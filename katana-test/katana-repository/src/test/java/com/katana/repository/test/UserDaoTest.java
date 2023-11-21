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
//        conn.prepareStatement(
//                "CREATE TABLE `user`\n" +
//                        "(\n" +
//                        "    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '账户ID',\n" +
//                        "    `name`        varchar(16) NOT NULL DEFAULT '' COMMENT '账户名称',\n" +
//                        "    `phone`       varchar(20) NOT NULL DEFAULT '' COMMENT '电话',\n" +
//                        "    `email`       varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',\n" +
//                        "    `contact`     varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',\n" +
//                        "    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
//                        "    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
//                        "    `tenant_code` varchar(16) NOT NULL DEFAULT '' COMMENT '租户编码',\n" +
//                        "    `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境',\n" +
//                        "    PRIMARY KEY (`id`)\n" +
//                        ")").execute();
        DefaultConfiguration config = new DefaultConfiguration();
        config.setSQLDialect(SQLDialect.H2);
        config.setConnection(conn);
        config.settings()
                .withRenderSchema(false)
                .withRenderNameCase(RenderNameCase.AS_IS)
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS);
        dslContext = DSL.using(config);

    }

    @Test
    public void test() {

        String sql="CREATE TABLE `account`\n" +
                "(\n" +
                "    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '<账户ID>',\n" +
                "    `name`          varchar(16) NOT NULL DEFAULT '' COMMENT '<账户名称>',\n" +
                "    `phone`         varchar(13) NOT NULL DEFAULT '' COMMENT '<电话>',\n" +
                "    `email`         varchar(16) NOT NULL DEFAULT '' COMMENT '<邮箱>',\n" +
                "    `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `update_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "    `tenant_code`   varchar(8)   NOT NULL DEFAULT '' COMMENT '<租户编码>',\n" +
                "    `env`       varchar(8) NOT NULL DEFAULT '' COMMENT '<环境>',\n" +
                "    PRIMARY KEY (`id`)\n" +
                ")ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='用户信息表';";
        DefaultConfiguration h2Config = new DefaultConfiguration();
        h2Config.setSQLDialect(SQLDialect.H2);
        h2Config.settings()
                .withRenderSchema(false)
                .withRenderNameCase(RenderNameCase.AS_IS)
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS);
        DSLContext h2Context = DSL.using(h2Config);

        DefaultConfiguration mySqlconfig = new DefaultConfiguration();
        mySqlconfig.setSQLDialect(SQLDialect.MYSQL);
        mySqlconfig.settings()
                .withRenderSchema(false)
                .withRenderNameCase(RenderNameCase.AS_IS)
                .withRenderQuotedNames(RenderQuotedNames.ALWAYS);
        DSLContext mySQLContext = DSL.using(mySqlconfig);

        System.out.println(h2Context.render(mySQLContext.parser().parse(sql)));
    }

}
