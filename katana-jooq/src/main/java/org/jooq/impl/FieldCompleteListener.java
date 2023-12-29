package org.jooq.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.val;

/**
 * 动态改写SQL，自动插入指定字段或追加字段匹配条件，支持多个字段<p/>
 * 可用于租户隔离或环境隔离等场景，能够自动根据上下文对SQL进行改写，保证数据安全性和隔离性
 * <p/>
 * 针对insert/update语句，自动追加或覆盖指定字段值，支持嵌套查询：如insert...select，update...where-select<p/>
 * 针对select/delete语句，自动追加或覆盖匹配条件，支持union/join/subquery等复杂场景<p/>
 * <p/>
 * 注意: 该类需要作为ExecuteListener/VisitListener同时配置才能使用
 *
 * @author liyifei
 */
@Slf4j
public class FieldCompleteListener implements VisitListener {
    /**
     * 需要自动补齐的字段
     */
    private final Map<String, Field<String>> AUTO_FIELDS = new HashMap<>();
    /**
     * 需要补齐字段的默认值
     */
    private static final Map<String, ThreadLocal<Param<String>>> AUTO_VALUES = new HashMap<>();

    /**
     * 需要追加的字段和默认值
     *
     * @param fieldAndDefaultValues
     */
    public FieldCompleteListener(String[]... fieldAndDefaultValues) {
        for (String[] fieldAndDefaultValue : fieldAndDefaultValues) {
            String fieldName = fieldAndDefaultValue[0];
            String defaultValue = fieldAndDefaultValue[1];
            AUTO_FIELDS.put(fieldName, DSL.field(fieldName, String.class));
            AUTO_VALUES.put(fieldName, ThreadLocal.withInitial(() -> val(defaultValue)));
        }
    }

    @Override
    public void visitStart(VisitContext ctx) {
        QueryPart query = ctx.queryPart();
        if (query instanceof InsertQueryImpl
                || query instanceof UpdateQueryImpl
                || query instanceof DeleteQueryImpl
                || query instanceof SelectQueryImpl) {
            rewriteQuery(query, AUTO_FIELDS);
        }
    }


    /**
     * 重写SQL，自动补齐匹配字段
     *
     * @param query
     * @param autoFields
     */
    private void rewriteQuery(QueryPart query, Map<String, Field<String>> autoFields) {
        //插入时,自动插入field
        if (query instanceof InsertQueryImpl) {
            InsertQueryImpl insertQuery = (InsertQueryImpl) query;
            Table table = insertQuery.table();
            autoFields.forEach((k, v) -> {
                Field<String> field = table.field(v);
                if (field != null) {
                    FieldMapsForInsert insertMaps = insertQuery.getInsertMaps();
                    Param<String> targetValue = targetValue(field);
                    log.info("add condition to table[{}]:{} ", table, field.eq(targetValue));
                    insertMaps.values.put(field, createFieldValues(targetValue, insertMaps.rows));
                }
            });
        }
        //更新时, 禁止field更新,增加field匹配
        if (query instanceof UpdateQueryImpl) {
            UpdateQueryImpl<?> updateQuery = (UpdateQueryImpl<?>) query;
            FieldMapForUpdate values = updateQuery.getValues();
            Table<?> table = updateQuery.table();
            autoFields.forEach((k, v) -> {
                Field<String> field = table.field(v);
                if (field != null) {
                    //禁止手动更新
                    values.remove(field);
                }
                if (field != null) {
                    Param<String> targetValue = targetValue(field);
                    log.info("add condition to table[{}]:{} ", table, field.eq(targetValue));
                    updateQuery.addConditions(field.eq(targetValue));
                }
            });
        }

        if (query instanceof DeleteQueryImpl) {
            DeleteQueryImpl<?> deleteQuery = (DeleteQueryImpl<?>) query;
            Table<?> table = deleteQuery.table();
            autoFields.forEach((k, v) -> {
                Field<String> field = table.field(v);
                if (field != null) {
                    Param<String> targetValue = targetValue(field);
                    log.info("add condition to table[{}]:{} ", table, field.eq(targetValue));
                    deleteQuery.addConditions(field.eq(targetValue));
                }
            });
        }
        //查询时, 增加field匹配
        if (query instanceof SelectQueryImpl) {
            SelectQueryImpl<?> selectQuery = (SelectQueryImpl<?>) query;
            TableList tables = selectQuery.getFrom();
            for (Table<?> table : tables.wrapped()) {
                List<Table<?>> targetTables = new ArrayList<>();
                //如果是join表,则需要分别匹配
                if (table instanceof JoinTable) {
                    JoinTable jt = (JoinTable) table;
                    targetTables.add(jt.lhs);
                    targetTables.add(jt.rhs);
                } else {
                    targetTables.add(table);
                }
                for (Table<?> t : targetTables) {
                    autoFields.forEach((k, v) -> {
                        Field<String> field = t.field(v);
                        if (field != null) {
                            Param<String> targetValue = targetValue(field);
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
    }

    /**
     * 获取当前字段的当前值
     *
     * @return
     */
    private Param<String> targetValue(Field<?> field) {
        return AUTO_VALUES.get(field.getName()).get();
    }


    /**
     * 生成指定字段的默认值
     *
     * @param value 需要生成默认值的字段
     * @param rows  行数
     * @return
     */
    private List<Field<?>> createFieldValues(Param<String> value, int rows) {
        List<Field<?>> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            list.add(value);
        }
        return list;
    }


    /**
     * 设置指定字段的当前值
     */
    public static String setFieldValue(String name, String value) {
        ThreadLocal<Param<String>> tl = AUTO_VALUES.get(name);
        Param<String> pre = tl.get();
        tl.set(val(value));
        if (pre != null) {
            return pre.getValue();
        }
        return null;
    }


}