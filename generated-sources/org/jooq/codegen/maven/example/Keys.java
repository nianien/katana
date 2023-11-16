/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.maven.example;


import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.codegen.maven.example.tables.UserInfo;
import org.jooq.codegen.maven.example.tables.records.UserInfoRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * PUBLIC.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<UserInfoRecord> CONSTRAINT_B = Internal.createUniqueKey(UserInfo.USER_INFO, DSL.name("CONSTRAINT_B"), new TableField[] { UserInfo.USER_INFO.ID }, true);
}
