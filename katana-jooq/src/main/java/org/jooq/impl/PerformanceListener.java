package org.jooq.impl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;
import org.jooq.conf.SettingsTools;
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
public class PerformanceListener implements ExecuteListener {

    /**
     * 存储正在执行的SQL列表
     */
    @Setter(AccessLevel.NONE)
    private final ThreadLocal<List<Query>> SQL_QUERIES = ThreadLocal.withInitial(ArrayList::new);
    /**
     * SQL执行计时器
     */
    @Setter(AccessLevel.NONE)
    private final ThreadLocal<StopWatch> STOP_WATCH = ThreadLocal.withInitial(StopWatch::new);

    private Properties properties;

    private DSLContext renderContext;
    private SqlAbbreviator sqlAbbreviator;

    public PerformanceListener() {
        this(new Properties());
    }

    public PerformanceListener(Properties properties) {
        this.properties = properties;
    }


    @Override
    public void start(ExecuteContext ctx) {
        //init for every outer call on this thread, not every invocation of this method
        SQL_QUERIES.get();
        STOP_WATCH.get();
        if (renderContext == null) {//实例共享
            synchronized (this) {
                if (renderContext == null) {
                    renderContext = DSL.using(ctx.dialect(),
                            SettingsTools.clone(ctx.settings()).withRenderFormatted(false));
                }
            }
        }
        if (sqlAbbreviator == null) {//实例共享
            synchronized (this) {
                if (sqlAbbreviator == null) {
                    sqlAbbreviator = new SqlAbbreviator(properties.paramAbbrLimit, ctx.dialect(), ctx.settings());
                }
            }
        }
    }

    @Override
    public void renderEnd(ExecuteContext ctx) {
        try {
            if (ctx.query() != null) {
                SQL_QUERIES.get().add(ctx.query());
            }
        } catch (Exception e) {
            //ignore
        }
    }


    @Override
    public void executeEnd(ExecuteContext ctx) {
        printSql(false);
        end();
    }


    @Override
    public void exception(ExecuteContext ctx) {
        printSql(true);
        end();
    }


    private void end() {
        SQL_QUERIES.remove();
        STOP_WATCH.remove();
    }

    /**
     * 打印SQL
     *
     * @param hasError 是否有异常
     */
    private void printSql(boolean hasError) {
        //enable show-sql or exists slow-query
        try {
            long costNanos = STOP_WATCH.get().split();
            List<Query> queryList = SQL_QUERIES.get();
            boolean isSlow = costNanos > TimeUnit.MILLISECONDS.toNanos(properties.slowSqlThreshold);
            if (properties.showSql || isSlow) {
                String timeCost = StopWatch.format(costNanos);
                int limit = hasError ? queryList.size() - 1 : queryList.size();
                String succeed = queryList.subList(0, limit)
                        .stream()
                        .map(this::buildSql)
                        .collect(Collectors.joining(";\n--------------------\n"));
                String fail = queryList.subList(limit, queryList.size())
                        .stream()
                        .map(this::buildSql)
                        .collect(Collectors.joining(";\n--------------------\n"));
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
            e.printStackTrace();
        }
    }


    /**
     * 构建SQL
     *
     * @param query
     * @return
     */
    private String buildSql(Query query) {
        String prepared = sqlAbbreviator.abbr(renderContext.render(query), 1);
        String rendered = renderContext.renderInlined(query);
        //SQL超长时缩略打印
        if (rendered.length() > properties.sqlLengthLimit) {
            if (properties.paramAbbrLimit > 0) {
                // 参数缩略
                rendered = sqlAbbreviator.abbr(rendered);
            }
            //参数缩略后SQL仍超长，则进行截断
            if (rendered.length() > properties.sqlLengthLimit) {
                rendered = rendered.substring(0, properties.sqlLengthLimit) + "...";
            }
        }
        return "[prepared]" + prepared + "\n[rendered]" + rendered;
    }


    @Data
    public static class Properties {
        /**
         * 是否显示执行的SQL
         */
        private boolean showSql = true;

        /**
         * 慢查询阈值，默认1000ms
         */
        private long slowSqlThreshold = 1000;

        /**
         * SQL长度限制，超过限制的SQL将被截断打印，默认10000
         */
        private int sqlLengthLimit = 1000;

        /**
         * 参数列表长度限制，超过限制将被缩略打印，默认1000
         */
        private int paramAbbrLimit = 10;
    }

}
