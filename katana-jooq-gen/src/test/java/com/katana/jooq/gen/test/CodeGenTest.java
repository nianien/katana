package com.katana.jooq.gen.test;

import lombok.SneakyThrows;
import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.JavaGenerator;
import org.jooq.meta.jaxb.*;
import org.jooq.util.jaxb.tools.MiniJAXB;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;


/**
 * @author : liyifei
 * @created : 2023/11/15, 星期三
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class CodeGenTest {

    public static void main(String[] args) throws Exception {
        String sql = Files.readString(Path.of("katana-test/katana-entity/src/main/sql/uc.sql"));
        String xml = "./jooqConfig.xml";
        Configuration configuration = config(false ? sql : xml);
        GenerationTool.generate(configuration);
    }

    private static Configuration config(String src) {
        Configuration configuration = new File(src).exists() ? configFromXml(src) : configFromSql(src);
        configuration.getGenerator().withTarget(
                new Target().withPackageName("com.katana.demo.entity")
                        .withDirectory("katana-test/katana-entity/src/main/java")
        );
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
            return MiniJAXB.unmarshal(file, Configuration.class);
        }
        return new Configuration()
                .withGenerator(
                        new Generator()
                                .withName(JavaGenerator.class.getName())
                                .withDatabase(new Database()
                                        .withName(org.jooq.meta.extensions.ddl.DDLDatabase.class.getName())
                                        .withProperties(
                                                new Property().withKey("scripts").withValue("**/sql/*.sql"), new Property().withKey("sort").withValue("semantic")
                                        )
                                ).withGenerate(
                                        new Generate()
                                                .withPojos(true)
                                                .withGlobalCatalogReferences(true)
                                                .withGlobalSchemaReferences(false)
                                                .withEmptySchemas(false)
                                )
                );
    }

}
