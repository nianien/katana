package com.katana.jooq.test;

import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.JavaGeneratorX;
import org.jooq.meta.jaxb.*;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * @author : liyifei
 * @created : 2023/11/15, 星期三
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class CodeGenTest {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:mem:test;", "sa", "sa");
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
                        ");COMMENT ON TABLE `user_info` IS '用户信息表';").execute();
        Generate generate = new Generate()
                .withPojos(true)
                .withGlobalCatalogReferences(true)
                .withGlobalSchemaReferences(false)
                .withEmptySchemas(false);

        generate.setPojosAsJavaRecordClasses(true);
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl("jdbc:h2:mem:test;")
                        .withUser("sa")
                        .withPassword("sa"))
                .withGenerator(new Generator()
                        .withName(JavaGeneratorX.class.getName())
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.h2.H2Database")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("PUBLIC")
                        ).withGenerate(generate
                        )
                        .withTarget(new Target()
                                .withPackageName("org.jooq.codegen.maven.example")
                                .withDirectory("generated-sources")));

        GenerationTool.generate(configuration);
    }
}
