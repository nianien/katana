package com.katana.jooq.gen.test;

import lombok.SneakyThrows;
import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.JavaGenerator;
import org.jooq.meta.jaxb.*;
import org.jooq.util.jaxb.tools.MiniJAXB;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * @author : liyifei
 * @created : 2023/11/15, 星期三
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class CodeGenTest {

    public static void main(String[] args) throws Exception {
        String sql = """
                 CREATE SCHEMA `uc`;
                 SET SCHEMA `uc`;
                 CREATE TABLE `user_info`
                 (
                     `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '账户ID',
                     `name`        varchar(16) NOT NULL DEFAULT '' COMMENT '账户名称',
                     `phone`       varchar(20) NOT NULL DEFAULT '' COMMENT '电话',
                     `email`       varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
                     `contact`     varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',
                     `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                     `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                     `tenant_code` varchar(16) NOT NULL DEFAULT '' COMMENT '租户编码',
                     `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境',
                     PRIMARY KEY (`id`)
                 )ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户信息表';
                 
                 CREATE TABLE `user_audit`
                 (
                     `userid`        bigint      NOT NULL COMMENT '用户ID',
                     `name`          varchar(64) NOT NULL COMMENT '用户名',
                     `audit_state`   int         NOT NULL DEFAULT '5' COMMENT '审核状态，0表示审核通过，1表示审核中，2表示审核拒绝，5待提交至审核(搁置)',
                     `auditor_id`    bigint      NOT NULL DEFAULT '-1' COMMENT '审核员id',
                     `reason_code`   varchar(256)         DEFAULT NULL COMMENT '拒绝理由',
                     `refuse_reason` varchar(1024)        DEFAULT NULL COMMENT '拒绝原因',
                     `env`           varchar(8)  NOT NULL DEFAULT '' COMMENT '环境标',
                     PRIMARY KEY (`userid`)
                 )ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户审核表';
                 
                 CREATE TABLE `user_tag`
                 (
                     `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
                     `userid`      bigint      NOT NULL COMMENT '用户id',
                     `tag`         varchar(64) NOT NULL DEFAULT '0' COMMENT '账户标签',
                     `create_time` timestamp   NOT NULL DEFAULT '2010-01-01 00:00:00' COMMENT '创建时间',
                     `modify_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                     `env`         varchar(8)  NOT NULL DEFAULT '' COMMENT '环境标',
                     PRIMARY KEY (`id`)
                 )ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户标签表'
                """;
        String xml = "./jooqConfig.xml";
        Configuration configuration = config(false ? sql : xml);
        GenerationTool.generate(configuration);
    }

    private static Configuration config(String src) {
        Configuration configuration = new File(src).exists() ? configFromXml(src) : configFromSql(src);
        configuration.getGenerator().getTarget()
                .withPackageName("com.katana.demo.entity")
                .withDirectory("katana-test/katana-entity/src/main/java");
        return configuration;
    }

    @SneakyThrows
    private static Configuration configFromSql(String sql) {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:mem:test;MODE=MYSQL", "sa", "sa");
        conn.prepareStatement(sql).execute();
        return new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl("jdbc:h2:mem:test;")
                        .withUser("sa")
                        .withPassword("sa"))
                .withGenerator(new Generator()
                        .withName(JavaGenerator.class.getName())
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.h2.H2Database")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("PUBLIC")
                        ).withGenerate(new Generate()
                                .withPojos(true)
                                .withGlobalCatalogReferences(true)
                                .withGlobalSchemaReferences(false)
                                .withEmptySchemas(false).withPojosAsJavaRecordClasses(false)
                        )
                        .withTarget(new Target()
                                .withPackageName("org.jooq.codegen.maven.example")
                                .withDirectory("generated-sources")));
    }

    private static Configuration configFromXml(String xmlPath) {
        File file = new File(xmlPath);
        if (file.exists()) {
            Configuration configuration = MiniJAXB.unmarshal(file, Configuration.class);
            return configuration;
        }
        return new Configuration()
                .withGenerator(
                        new Generator()
                                .withName(JavaGenerator.class.getName())
                                .withDatabase(new Database()
                                        .withName(org.jooq.meta.extensions.ddl.DDLDatabase.class.getName())
                                        .withProperties(
                                                new Property().withKey("scripts").withValue("**/h2/*.sql"), new Property().withKey("sort").withValue("semantic")
                                        )
                                ).withGenerate(
                                        new Generate()
                                                .withPojos(true)
                                                .withGlobalCatalogReferences(true)
                                                .withGlobalSchemaReferences(false)
                                                .withEmptySchemas(false)
                                )
                                .withTarget(new Target()
                                        .withPackageName("org.jooq.codegen.maven.example")
                                        .withDirectory("generated-sources")
                                ));
    }

}
