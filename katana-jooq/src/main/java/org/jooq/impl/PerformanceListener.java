package org.jooq.impl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.ExecuteContext;
import org.jooq.tools.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于jooq的数据库执行性能监听器，支持批量SQL
 *
 * @author liyifei
 */
@Slf4j
@Data
public class PerformanceListener implements DefaultListener {

    /**
     * 是否显示执行的SQL
     */
    private boolean showSql = true;

    /**
     * 慢查询阈值，默认1000ms
     */
    private long slowQueryThreshold = 1000;

    /**
     * 是否启用SQL缩略打印
     */
    private boolean sqlAbbrEnabled = true;

    /**
     * SQL长度限制，超过限制的SQL将被截断打印，默认10000
     */
    private int sqlAbbrLimit = 10000;
    /**
     * IN参数列表长度限制，超过限制将被截断打印，默认1000
     */
    private int paramAbbrLimit = 1000;


    @Setter(AccessLevel.NONE)
    private String[] sqlAbbrPatterns = sqlAbbrPatterns(paramAbbrLimit);

    public void setSqlAbbrLimit(int sqlAbbrLimit) {
        this.sqlAbbrLimit = sqlAbbrLimit;
        this.sqlAbbrPatterns = sqlAbbrPatterns(paramAbbrLimit);
    }


    /**
     * 存储正在执行的SQL列表
     */
    @Setter(AccessLevel.NONE)
    private final ThreadLocal<List<String>> SQL_QUERIES = ThreadLocal.withInitial(ArrayList::new);
    /**
     * SQL执行计时器
     */
    @Setter(AccessLevel.NONE)
    private final ThreadLocal<StopWatch> STOP_WATCH = ThreadLocal.withInitial(StopWatch::new);

    @Override
    public void start(ExecuteContext ctx) {
        //init for every outer call on this thread, not every invocation of this method
        SQL_QUERIES.get();
        STOP_WATCH.get();
    }

    @Override
    public void renderEnd(ExecuteContext ctx) {
        try {
            if (ctx.query() != null) {
                SQL_QUERIES.get().add(ctx.query().toString());
            }
        } catch (Exception e) {
            //ignore
        }
    }


    @Override
    public void executeEnd(ExecuteContext ctx) {
        printSql(false);
    }


    @Override
    public void exception(ExecuteContext ctx) {
        printSql(true);
    }


    /**
     * @param hasError 是否存在异常
     */
    private void printSql(boolean hasError) {
        //enable show-sql or exists slow-query
        try {
            long costNanos = STOP_WATCH.get().split();
            List<String> sqlList = SQL_QUERIES.get();
            //
            boolean isSlow = costNanos > TimeUnit.MILLISECONDS.toNanos(slowQueryThreshold);
            if (showSql || isSlow) {
                String timeCost = StopWatch.format(costNanos);
                List<String> list = sqlList.stream().map(this::abbrSql).collect(Collectors.toList());
                int limit = hasError ? sqlList.size() - 1 : sqlList.size();
                String succeed = list.subList(0, limit).stream().collect(Collectors.joining(";\n--------------------\n"));
                String fail = list.subList(limit, list.size()).stream().collect(Collectors.joining(";\n--------------------\n"));
                if (!hasError) {//all success
                    if (isSlow) {
                        log.warn("slow sql query by jooq cost {}:\n{}", timeCost, succeed);
                    } else {
                        log.info("sql query by jooq cost {}:\n{}", timeCost, succeed);
                    }
                } else {//has failed
                    if (isSlow) {
                        log.warn("slow sql query by jooq cost {}:\nsuccess:{}\nfailed:{}", timeCost, succeed, fail);
                    } else {
                        log.info("sql query by jooq cost {}:\nsuccess:{}\nfailed:{}", timeCost, succeed, fail);
                    }
                }
            }
        } catch (Exception e) {

            //ignore
        } finally {
            SQL_QUERIES.remove();
            STOP_WATCH.remove();
        }
    }

    /**
     * SQL缩略打印正则表达式
     *
     * @param paramAbbrLimit
     * @return
     */
    public static String[] sqlAbbrPatterns(int paramAbbrLimit) {
        return new String[]{"(\\(([^,]+,){" + paramAbbrLimit + "})([^)]*)(\\))", "$1...$4"};
    }


    /**
     * SQL缩略打印
     *
     * @param sql
     * @return
     */
    private String abbrSql(String sql) {
        //SQL超长时缩略打印
        if (sqlAbbrEnabled && sql.length() > sqlAbbrLimit) {
            // 优先缩略参数列表显示
            sql = sql.replaceAll(sqlAbbrPatterns[0], sqlAbbrPatterns[1]);
            //参数缩略后SQL仍超长，则进行截断
            if (sql.length() > sqlAbbrLimit) {
                sql = sql.substring(0, sqlAbbrLimit);
            }
        }
        return sql;
    }

}
