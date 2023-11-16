/*
 * This file is generated by jOOQ.
 */
package org.jooq.codegen.maven.example.tables;


import java.time.LocalDateTime;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function9;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row9;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.codegen.maven.example.Keys;
import org.jooq.codegen.maven.example.Public;
import org.jooq.codegen.maven.example.tables.records.UserInfoRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * 用户信息表
 * @param id 账户ID
 * @param name 账户名称
 * @param phone 电话
 * @param email 邮箱
 * @param contact 联系人
 * @param createTime 创建时间
 * @param updateTime 更新时间
 * @param tenantCode 租户编码
 * @param env 环境
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserInfo extends TableImpl<UserInfoRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC.USER_INFO</code>
     */
    public static final UserInfo USER_INFO = new UserInfo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserInfoRecord> getRecordType() {
        return UserInfoRecord.class;
    }

    /**
     * The column <code>PUBLIC.USER_INFO.ID</code>. 账户ID
     */
    public final TableField<UserInfoRecord, Long> ID = createField(DSL.name("ID"), SQLDataType.BIGINT.nullable(false).identity(true), this, "账户ID");

    /**
     * The column <code>PUBLIC.USER_INFO.NAME</code>. 账户名称
     */
    public final TableField<UserInfoRecord, String> NAME = createField(DSL.name("NAME"), SQLDataType.VARCHAR(16).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "账户名称");

    /**
     * The column <code>PUBLIC.USER_INFO.PHONE</code>. 电话
     */
    public final TableField<UserInfoRecord, String> PHONE = createField(DSL.name("PHONE"), SQLDataType.VARCHAR(20).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "电话");

    /**
     * The column <code>PUBLIC.USER_INFO.EMAIL</code>. 邮箱
     */
    public final TableField<UserInfoRecord, String> EMAIL = createField(DSL.name("EMAIL"), SQLDataType.VARCHAR(20).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "邮箱");

    /**
     * The column <code>PUBLIC.USER_INFO.CONTACT</code>. 联系人
     */
    public final TableField<UserInfoRecord, String> CONTACT = createField(DSL.name("CONTACT"), SQLDataType.VARCHAR(20).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "联系人");

    /**
     * The column <code>PUBLIC.USER_INFO.CREATE_TIME</code>. 创建时间
     */
    public final TableField<UserInfoRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("CREATE_TIME"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "创建时间");

    /**
     * The column <code>PUBLIC.USER_INFO.UPDATE_TIME</code>. 更新时间
     */
    public final TableField<UserInfoRecord, LocalDateTime> UPDATE_TIME = createField(DSL.name("UPDATE_TIME"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "更新时间");

    /**
     * The column <code>PUBLIC.USER_INFO.TENANT_CODE</code>. 租户编码
     */
    public final TableField<UserInfoRecord, String> TENANT_CODE = createField(DSL.name("TENANT_CODE"), SQLDataType.VARCHAR(16).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "租户编码");

    /**
     * The column <code>PUBLIC.USER_INFO.ENV</code>. 环境
     */
    public final TableField<UserInfoRecord, String> ENV = createField(DSL.name("ENV"), SQLDataType.VARCHAR(8).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "环境");

    private UserInfo(Name alias, Table<UserInfoRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserInfo(Name alias, Table<UserInfoRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户信息表"), TableOptions.table());
    }

    /**
     * Create an aliased <code>PUBLIC.USER_INFO</code> table reference
     */
    public UserInfo(String alias) {
        this(DSL.name(alias), USER_INFO);
    }

    /**
     * Create an aliased <code>PUBLIC.USER_INFO</code> table reference
     */
    public UserInfo(Name alias) {
        this(alias, USER_INFO);
    }

    /**
     * Create a <code>PUBLIC.USER_INFO</code> table reference
     */
    public UserInfo() {
        this(DSL.name("USER_INFO"), null);
    }

    public <O extends Record> UserInfo(Table<O> child, ForeignKey<O, UserInfoRecord> key) {
        super(child, key, USER_INFO);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<UserInfoRecord, Long> getIdentity() {
        return (Identity<UserInfoRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<UserInfoRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_B;
    }

    @Override
    public UserInfo as(String alias) {
        return new UserInfo(DSL.name(alias), this);
    }

    @Override
    public UserInfo as(Name alias) {
        return new UserInfo(alias, this);
    }

    @Override
    public UserInfo as(Table<?> alias) {
        return new UserInfo(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserInfo rename(String name) {
        return new UserInfo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserInfo rename(Name name) {
        return new UserInfo(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserInfo rename(Table<?> name) {
        return new UserInfo(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, String, LocalDateTime, LocalDateTime, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function9<? super Long, ? super String, ? super String, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function9<? super Long, ? super String, ? super String, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}