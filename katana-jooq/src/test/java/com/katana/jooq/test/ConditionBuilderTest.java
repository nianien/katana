package com.katana.jooq.test;

import com.katana.jooq.condition.ConditionBuilder;
import com.katana.jooq.condition.FluentCondition;
import com.katana.jooq.condition.Operator;
import org.jooq.*;
import org.jooq.conf.RenderNameStyle;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.FieldsRow;
import org.jooq.impl.TableImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.cudrania.core.functions.Params.gt;
import static com.cudrania.core.functions.Params.notEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * scm.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
public class ConditionBuilderTest {

    static class ListTable extends TableImpl implements Table {

        private Field[] fields;

        public ListTable(String name, Field... fields) {
            super(DSL.name(name));
            this.fields = fields;
        }

        @Override
        public Row fieldsRow() {
            return new FieldsRow(fields);
        }
    }

    /**
     * 模拟动态表
     */
    static ListTable GoodsQueryTable = new ListTable("goods", DSL.field("out_submit_time", java.sql.Date.class),
            DSL.field("src_store_name", String.class),
            DSL.field("industry_name", Integer.class),
            DSL.field("industry_id", Integer.class),
            DSL.field("id", Integer.class),
            DSL.field("price", BigDecimal.class),
            DSL.field("biz_type", Integer.class));


    /**
     * 模拟静态表
     */
    static class UserTable {
        private static final Field<Long> ID = DSL.field("id", Long.class);
        private static final Field<String> NAME = DSL.field("name", String.class);
        private static final Field<Integer> TYPE = DSL.field("type", Integer.class);
    }

    private static DSLContext dslContext;

    @BeforeAll
    public static void setup() {
        DefaultConfiguration config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.MYSQL);
        config.settings()
                //shard-sphere不支持schema
                .withRenderSchema(false)
                .withRenderNameStyle(RenderNameStyle.AS_IS);
        dslContext = DSL.using(config);
    }


    @Test
    public void testFluent() {
        long id = 100;
        String name = "jack";
        List<Integer> types = Arrays.asList(1001, 1002, 1003, 1004, 0, -1);
        Condition condition = FluentCondition.and()
                .when(gt(id, 10), UserTable.ID)
                .when(notEmpty(name), UserTable.NAME::ne)
                .when(notEmpty(types)
                        .then(e -> e.stream().filter(i -> i > 0).toList())
                        .when(List::isEmpty).negate(), UserTable.TYPE::notIn)
                .get();


        System.out.println(dslContext.renderInlined(condition));
        System.out.println(dslContext.renderNamedOrInlinedParams(condition));
        System.out.println("==========================");
        String render = dslContext.render(condition);
        System.out.println(render);
        List<Object> bindValues = dslContext.extractBindValues(condition);
        System.out.println(dslContext.renderInlined(dslContext.query(render, bindValues.toArray())));
        System.out.println(dslContext.extractBindValues(condition));
        System.out.println("==========================");
        System.out.println(dslContext.renderNamedParams(condition));
        System.out.println(dslContext.extractParams(condition));

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("jooq.enable.sql.abbr"));
        String sql = "select * from user where id  in (1001,1001, 1002, 1003, 1004, 1005, 1006, 1007) and type in('a','b','c','d','e','f','g','h','i','j','k','l','m')) and name in(select name from user where name in('a','b','c','d','e','f','g','h','i','j','k','l','m'))";
        System.out.println(sql.replaceAll("(([^,]+,){3})([^)]*)", "$1..."));
//        System.out.println(sql.replaceAll("(.*\\([^,]+)((,[^,]+){3})((,[^,]+)+\\).*)", "$3"));
//        System.out.println(sql.replaceAll("(.*\\([^,]+)((,[^,]+){3})((,[^,]+)+\\).*)", "$1$4"));
//        System.out.println("('1001', 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004, 1002, 1003, 1004)".matches(".*\\([^,]+((,[^,]+){10})(,[^,]+)+\\).*"));
        System.out.println("abc123def".replaceAll("([a-z]+)","..."));
    }

    @Test
    public void testBuilderByName() {

        GoodsQuery query = new GoodsQuery();
        query.setSubmitTimeBegin(new Date());
        query.setSubmitTimeEnd(new Date());
        query.setSrcStoreName("abc");
        query.setIndustryName("ddd");
        query.setIndustryId(-100L);
        query.setId(100L);
        query.setPrice(new BigDecimal(1111));
        Condition condition = ConditionBuilder.byName()
                .match(GoodsQuery::getPrice, Operator.LT)
                .match("(?i).*name.*", Operator.LIKE)
                .filter(f -> {
                    if (f.getField().getName().equals("src_store_name")) {
                        f.setOperator(Operator.NE);
                    }
                })
                .build(query);
        System.out.println(dslContext.renderInlined(condition));
    }


    @Test
    public void testBuilderByTable() {
        GoodsQuery goodsQuery = new GoodsQuery();
        goodsQuery.setExpireDate(new Date[]{new Date(), new Date()});
        goodsQuery.setSrcStoreName("测试仓");
        goodsQuery.setCreateDate(new Date());
        goodsQuery.setBizType(10);
        Condition condition = ConditionBuilder.byTable(GoodsQueryTable)
                .match(GoodsQuery::getSrcStoreName, Operator.LIKE)
                .build(goodsQuery);
        String sql = dslContext.renderInlined(condition);
        System.out.println(sql);
        assertTrue(sql.contains("src_store_name like"));
    }

}
