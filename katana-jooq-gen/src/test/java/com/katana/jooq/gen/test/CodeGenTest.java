package com.katana.jooq.gen.test;

import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.JavaGenerator;
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
        genFromSql();
    }
    public static void genFromH2() throws Exception {
        Connection conn = DriverManager.
                getConnection("jdbc:h2:mem:test;MODE=MYSQL", "sa", "sa");
        conn.prepareStatement(
                "CREATE TABLE `ad_crm_account_industry_restriction`\n" +
                        "(\n" +
                        "    `id`               bigint(10)              NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                        "    `crm_agent_type`   int(10)       default 0 not null comment '开户代理商类型',\n" +
                        "    `crm_agent_son_type`   int(10)       default 0 not null comment '开户代理商子类型',\n" +
                        "    `restriction_type` int(10)       default 0 not null comment '限制类型，0可开发，1不可开发',\n" +
                        "    `industry_id`      bigint(10)    default 0 not null comment '行业id',\n" +
                        "    `industry_level`   int(10)       default 0 not null comment '行业级别',\n" +
                        "    `area_conf`        varchar(1024) default null comment '地域限制',\n" +
                        "    `not_dev_area_conf`        varchar(1024) default null comment '不可开发地域限制',\n" +
                        "    PRIMARY KEY (`id`)\n" +
                        ") COMMENT '客开行业限制表';").execute();
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
                        .withName(JavaGenerator.class.getName())
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


    public static void genFromSql() throws Exception {
        Configuration configuration = new Configuration()
                .withGenerator(
                        new Generator()
                                .withName(JavaGenerator.class.getName())
                                .withDatabase(new Database()
                                        .withName(org.jooq.meta.extensions.ddl.DDLDatabase.class.getName())
                                        .withProperties(
                                                new Property().withKey("scripts").withValue("/Users/skyfalling/Workspace/kuaishou/ks-jooq-gen/src/main/sql/h2/uc.sql"), new Property().withKey("sort").withValue("semantic")
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
                                        .withDirectory("generated-sources")));

        GenerationTool.generate(configuration);
    }

}
