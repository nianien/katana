package com.katana.repository.test;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.katana.demo.entity.uc.Tables.USER_INFO;

/**
 * @author : liyifei
 * @created : 2023/10/30, 星期一
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class UserDaoTest {
    private static DSLContext dslContext;

    @BeforeAll
    public static void setUp() throws SQLException {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:mem:test", "sa", "sa");
        conn.prepareStatement(
                "CREATE TABLE `user_info`\n" +
                        "(\n" +
                        "    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '账户ID',\n" +
                        "    `name`        varchar(16) NOT NULL DEFAULT '' COMMENT '账户名称',\n" +
                        "    `phone`       varchar(20) NOT NULL DEFAULT '' COMMENT '电话',\n" +
                        "    `email`       varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',\n" +
                        "    `contact`     varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',\n" +
                        "    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                        "    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                        "    `tenant_code` varchar(16) NOT NULL DEFAULT '' COMMENT '租户编码',\n" +
                        "    `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境',\n" +
                        "    PRIMARY KEY (`id`)\n" +
                        ")").execute();
        DefaultConfiguration config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.MYSQL);
        config.setConnection(conn);
        config.settings()
                .withRenderSchema(false)
                .withRenderNameCase(RenderNameCase.AS_IS)
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS);
        dslContext = DSL.using(config);

    }

    @Test
    public void test() {

        System.out.println(dslContext.render(USER_INFO));
        System.out.println(dslContext.render(dslContext.select().from(USER_INFO)
                .where(USER_INFO.NAME.like("test"))));;
    }

}
