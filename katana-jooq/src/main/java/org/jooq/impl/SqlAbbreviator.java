package org.jooq.impl;

import org.jooq.*;
import org.jooq.conf.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 长SQL缩略改写
 *
 * @author : liyifei
 * @created : 2023/11/13, 星期一
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class SqlAbbreviator implements VisitListener {


    /**
     * 用于缩略SQL的上下文
     */
    private final DSLContext abbrContext;

    /**
     * SQL缩略参数长度限制
     */
    private final ThreadLocal<Integer> abbrLimit;

    /**
     * SQL WHERE-IN语句访问堆栈
     */
    private final ThreadLocal<Stack<Integer>> whereInStack = ThreadLocal.withInitial(Stack::new);
    /**
     * SQL INSERT语句访问堆栈
     */
    private final ThreadLocal<Stack<Boolean>> insertStack = ThreadLocal.withInitial(Stack::new);

    /**
     * @param defaultLimit SQL长度限制，超过限制的SQL将被省略
     * @param sqlDialect   SQL方言
     * @param settings
     */
    public SqlAbbreviator(int defaultLimit, SQLDialect sqlDialect, Settings settings) {
        this.abbrLimit = ThreadLocal.withInitial(() -> defaultLimit);
        this.abbrContext = DSL.using(new DefaultConfiguration(new NoConnectionProvider(), sqlDialect, settings).set(this));
    }

    public SqlAbbreviator(int abbrLimit, SQLDialect sqlDialect) {
        this(abbrLimit, sqlDialect, null);
    }

    public SqlAbbreviator(int abbrLimit) {
        this(abbrLimit, SQLDialect.DEFAULT, null);
    }

    @Override
    public void visitStart(VisitContext context) {
        int limit = abbrLimit.get();
        if (context.queryPart().getClass() == InList.class) {
            Stack<Integer> stack = whereInStack.get();
            InList inList = (InList) context.queryPart();
            boolean abbr = inList.values.size() > limit;
            if (abbr) {
                while (inList.values.size() > limit) {
                    inList.values.remove(0);
                }
                stack.push(inList.values.size());
            }
        } else if (context.queryPart().getClass() == FieldMapsForInsert.class) {
            Stack<Boolean> stack = insertStack.get();
            FieldMapsForInsert forInsert = (FieldMapsForInsert) context.queryPart();
            boolean abbr = forInsert.rows > limit;
            if (abbr) {
                for (List<Field<?>> list : forInsert.values.values()) {
                    list.retainAll(new ArrayList<>(list.subList(0, limit)));
                }
                forInsert.rows = limit;
            }
            stack.push(abbr);
        }
    }

    @Override
    public void visitEnd(VisitContext context) {
        if (context.queryPart().getClass() == Val.class) {
            Stack<Integer> stack = whereInStack.get();
            if (!stack.isEmpty()) {
                Integer count = stack.pop();
                if (count == 1) {
                    context.context().sql(",...");
                } else {
                    stack.push(count - 1);
                }
            }
        } else if (context.queryPart().getClass() == FieldMapsForInsert.class) {
            Stack<Boolean> stack = insertStack.get();
            if (!stack.isEmpty()) {
                Boolean abbr = stack.pop();
                if (abbr) {
                    context.context().sql(",...");
                }
            }
        }
    }

    /**
     * 缩略SQL
     *
     * @param sql
     * @return
     */
    public String abbr(String sql) {
        return abbrContext.render(abbrContext.parser().parseQuery(sql));
    }

    /**
     * 缩略SQL
     *
     * @param sql
     * @param limit 参数个数限制
     * @return
     */
    public String abbr(String sql, int limit) {
        try {
            abbrLimit.set(limit);
            return abbr(sql);
        } finally {
            abbrLimit.remove();
        }
    }


}
