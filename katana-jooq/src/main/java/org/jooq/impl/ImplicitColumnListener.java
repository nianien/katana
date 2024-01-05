package org.jooq.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.val;

/**
 * 动态改写SQL，自动插入隐式列、追加隐式列匹配，支持多个隐式列<p/>
 * 可用于租户隔离或环境隔离等场景，能够自动根据上下文对SQL进行改写，保证数据安全性和隔离性
 * <p/>
 * 针对insert/update语句，自动追加或覆盖指定字段值，支持嵌套查询：如insert...select，update...where-select<p/>
 * 针对select/delete语句，自动追加或覆盖匹配条件，支持union/join/subquery等复杂场景<p/>
 * <p/>
 *
 * @author liyifei
 */
@Slf4j
public class ImplicitColumnListener implements VisitListener {
    /**
     * 隐式字段的默认值
     */
    private ThreadLocal<Map<String, Param<String>>> IMPLICIT_COLUMNS;

    private final ThreadLocal<Stack<Set<Field<String>>>> implicitsInStack = ThreadLocal.withInitial(Stack::new);
    private final ThreadLocal<Stack<Boolean>> flagInStack = ThreadLocal.withInitial(Stack::new);

    /**
     * 定义隐式字段及默认值
     *
     * @param columnAndDefaultValues
     */
    public ImplicitColumnListener(String[]... columnAndDefaultValues) {
        Map<String, Param<String>> defaultValues = Arrays.stream(columnAndDefaultValues)
                .collect(Collectors.toMap(e -> e[0], e -> val(e[1])));
        IMPLICIT_COLUMNS = ThreadLocal.withInitial(() -> defaultValues);
    }


    /**
     * 定义隐式字段及默认值
     *
     * @param columnAndDefaultValues
     */
    public ImplicitColumnListener(Map<String, String> columnAndDefaultValues) {
        Map<String, Param<String>> defaultValues = columnAndDefaultValues.entrySet()
                .stream().collect(Collectors.toMap(e -> e.getKey(), e -> val(e.getValue())));
        IMPLICIT_COLUMNS = ThreadLocal.withInitial(() -> defaultValues);
    }


    @Override
    public void clauseStart(VisitContext ctx) {
        Clause clause = ctx.clause();
        switch (clause) {
            case SELECT:
            case INSERT:
            case UPDATE:
            case DELETE:
                flagInStack.get().push(false);
                break;
            case SELECT_WHERE:
            case DELETE_WHERE:
            case UPDATE_WHERE:
                flagInStack.get().push(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void clauseEnd(VisitContext context) {
        Clause clause = context.clause();
        if (clause == Clause.SELECT_WHERE || clause == Clause.SELECT) {
            flagInStack.get().pop();
        }
    }

    @Override
    public void visitStart(VisitContext ctx) {
        QueryPart query = ctx.queryPart();
        if (query instanceof InsertQueryImpl
                || query instanceof UpdateQueryImpl
                || query instanceof DeleteQueryImpl
                || query instanceof SelectQueryImpl) {
            implicitsInStack.get().push(new HashSet<>());
        }
        if (query instanceof TableFieldImpl && flagInStack.get().peek()) {
            TableFieldImpl tableField = (TableFieldImpl) query;
            if (IMPLICIT_COLUMNS.get().containsKey(tableField.getName())) {
                implicitsInStack.get().peek().add(tableField.coerce(String.class));
            }
        }
    }

    @Override
    public void visitEnd(VisitContext ctx) {
        QueryPart query = ctx.queryPart();
        if (query instanceof InsertQueryImpl
                || query instanceof UpdateQueryImpl
                || query instanceof DeleteQueryImpl
                || query instanceof SelectQueryImpl) {
            rewriteQuery(query, IMPLICIT_COLUMNS.get().keySet());
        }
    }

    /**
     * 重写SQL，自动补齐匹配字段</br>
     * 注意: 这里的前提未显示指定字段，否则不会自动补齐
     *
     * @param query
     * @param autoFields
     */
    private void rewriteQuery(QueryPart query, Set<String> autoFields) {
        Set<Field<String>> fields = implicitsInStack.get().pop();
        //插入时,自动插入field
        if (query instanceof InsertQueryImpl) {
            InsertQueryImpl insertQuery = (InsertQueryImpl) query;
            Table table = insertQuery.table();
            autoFields.forEach((v) -> {
                Field<String> field = table.field(v, String.class);
                if (field != null) {
                    FieldMapsForInsert insertMaps = insertQuery.getInsertMaps();
                    Param<String> targetValue = implicitValue(field);
                    log.info("add condition to table[{}]:{} ", table, field.eq(targetValue));
                    insertMaps.values.put(field, createImplicitValues(targetValue, insertMaps.rows));
                }
            });
        }
        //更新时, 禁止field更新, 并增加field匹配
        if (query instanceof UpdateQueryImpl) {
            UpdateQueryImpl<?> updateQuery = (UpdateQueryImpl<?>) query;
            FieldMapForUpdate values = updateQuery.getValues();
            Table<?> table = updateQuery.table();
            autoFields.forEach((v) -> {
                Field<String> field = table.field(v, String.class);
                if (field != null) {
                    //禁止手动更新
                    values.remove(field);
                    if (!fields.contains(field)) {
                        Param<String> implicitValue = implicitValue(field);
                        log.info("add condition to table[{}]:{} ", table, field.eq(implicitValue));
                        updateQuery.addConditions(field.eq(implicitValue));
                    }
                }
            });
        }
        if (query instanceof DeleteQueryImpl) {
            DeleteQueryImpl<?> deleteQuery = (DeleteQueryImpl<?>) query;
            Table<?> table = deleteQuery.table();
            autoFields.forEach((v) -> {
                Field<String> field = table.field(v, String.class);
                if (field != null && !fields.contains(field)) {
                    Param<String> targetValue = implicitValue(field);
                    log.info("add condition to table[{}]:{} ", table, field.eq(targetValue));
                    deleteQuery.addConditions(field.eq(targetValue));
                }
            });
        }
        //查询时, 增加field匹配
        if (query instanceof SelectQueryImpl) {
            SelectQueryImpl<?> selectQuery = (SelectQueryImpl<?>) query;
            TableList tables = selectQuery.getFrom();
            List<Table<?>> targetTables = new ArrayList<>();
            for (Table<?> table : tables.wrapped()) {
                //如果是join表,则需要分别匹配
                if (table instanceof JoinTable) {
                    JoinTable jt = (JoinTable) table;
                    targetTables.add(jt.lhs);
                    targetTables.add(jt.rhs);
                } else {
                    targetTables.add(table);
                }
            }
            for (Table<?> t : targetTables) {
                autoFields.forEach((v) -> {
                    Field<String> field = t.field(v, String.class);
                    if (field != null && !fields.contains(field)) {
                        Param<String> targetValue = implicitValue(field);
                        // 特定场景下，查询时禁止条件匹配
                        if (!"*".equals(targetValue.getValue())) {
                            log.info("add condition to table[{}]:{} ", t, field.eq(targetValue));
                            selectQuery.addConditions(field.eq(targetValue));
                        }
                    }
                });
            }
        }
    }

    /**
     * 获取隐式列的值
     *
     * @return
     */
    private Param<String> implicitValue(Field<?> field) {
        return IMPLICIT_COLUMNS.get().get(field.getName());
    }


    /**
     * 生成隐式列的默认值
     *
     * @param value 需要生成默认值的字段
     * @param rows  行数
     * @return
     */
    private List<Field<?>> createImplicitValues(Param<String> value, int rows) {
        List<Field<?>> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            list.add(value);
        }
        return list;
    }


    /**
     * 设置隐式列的当前值
     */
    public String setImplicitValue(String name, String value) {
        Param<String> pre = IMPLICIT_COLUMNS.get().put(name, val(value));
        return pre != null ? pre.getValue() : null;
    }


}