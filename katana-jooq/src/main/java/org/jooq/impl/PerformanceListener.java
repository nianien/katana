package org.jooq.impl;

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
public class PerformanceListener implements DefaultListener {

    /**
     * 是否显示执行的SQL
     */
    private static final boolean SHOW_SQL = Boolean.valueOf(System.getProperty("jooq.show-sql", "true"));

    /**
     * 慢SQL阈值，默认
     */
    private static final long SLOW_QUERY_TIME = Long.valueOf(System.getProperty("jooq.slow-query-time", "1000"));

    /**
     * SQL参数个数超过100个时，只显示前100个参数
     */
    private static final int PARAM_ABBR_LENGTH = Integer.valueOf(System.getProperty("jooq.sql.abbr.param.limit", "1000"));

    /**
     * SQL长度超过10000时，只显示前10000个字符
     */
    private static final int SQL_ABBR_LENGTH = Integer.valueOf(System.getProperty("jooq.sq.abbr.length.limit", "10000"));

    /**
     * 是否启用SQL缩略打印
     */
    private static final boolean ENABLE_SQL_ABBR = Boolean.valueOf(System.getProperty("jooq.sql.abbr.enable", "true"));
    /**
     * SQL缩略打印，正则表达式替换
     */
    private static final String[] SQL_ABBR_PATTERNS = new String[]{"(([^,]+,){" + PARAM_ABBR_LENGTH + "})([^)]*)", "$1..."};

    /**
     * 存储当前执行的SQL列表
     */
    private static final ThreadLocal<List<String>> SQL_QUERIES = ThreadLocal.withInitial(ArrayList::new);
    /**
     * 整体SQL请求的计时器
     */
    private static final ThreadLocal<StopWatch> STOP_WATCH = ThreadLocal.withInitial(StopWatch::new);

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
            if (SHOW_SQL || costNanos > TimeUnit.MILLISECONDS.toNanos(SLOW_QUERY_TIME)) {
                String timeCost = StopWatch.format(costNanos);
                List<String> list = sqlList.stream().map(sql -> {
                    //SQL超长时缩略打印
                    if (ENABLE_SQL_ABBR && sql.length() > SQL_ABBR_LENGTH) {
                        // 优先缩略参数列表显示
                        sql = sql.replaceAll(SQL_ABBR_PATTERNS[0], SQL_ABBR_PATTERNS[1]);
                        //参数缩略后SQL仍超长，则进行截断
                        if (sql.length() > SQL_ABBR_LENGTH) {
                            sql = sql.substring(0, SQL_ABBR_LENGTH);
                        }
                    }
                    return sql;
                }).collect(Collectors.toList());
                int limit = hasError ? sqlList.size() - 1 : sqlList.size();
                String succeed = list.subList(0, limit).stream().collect(Collectors.joining(";\n--------------------\n"));
                String fail = list.subList(limit, list.size()).stream().collect(Collectors.joining(";\n--------------------\n"));
                if (!hasError) {//all success
                    log.info("sql query by jooq cost {}:\n{}", timeCost, succeed);
                } else {//has failed
                    log.info("sql query by jooq cost {}:\nsuccess:{}\nfailed:{}", timeCost, succeed, fail);
                }
            }
        } catch (Exception e) {
            //ignore
        } finally {
            SQL_QUERIES.remove();
            STOP_WATCH.remove();
        }
    }
}
