/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables;


import com.katana.demo.entity.uc.Keys;
import com.katana.demo.entity.uc.Uc;
import com.katana.demo.entity.uc.tables.records.UserAuditRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * 用户审核表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAudit extends TableImpl<UserAuditRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>uc.user_audit</code>
     */
    public static final UserAudit USER_AUDIT = new UserAudit();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserAuditRecord> getRecordType() {
        return UserAuditRecord.class;
    }

    /**
     * The column <code>uc.user_audit.userid</code>. 用户ID
     */
    public final TableField<UserAuditRecord, Long> USERID = createField(DSL.name("userid"), SQLDataType.BIGINT.nullable(false), this, "用户ID");

    /**
     * The column <code>uc.user_audit.name</code>. 用户名
     */
    public final TableField<UserAuditRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(64).nullable(false), this, "用户名");

    /**
     * The column <code>uc.user_audit.audit_state</code>.
     * 审核状态，0表示审核通过，1表示审核中，2表示审核拒绝，5待提交至审核(搁置)
     */
    public final TableField<UserAuditRecord, Integer> AUDIT_STATE = createField(DSL.name("audit_state"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field(DSL.raw("'5'"), SQLDataType.INTEGER)), this, "审核状态，0表示审核通过，1表示审核中，2表示审核拒绝，5待提交至审核(搁置)");

    /**
     * The column <code>uc.user_audit.auditor_id</code>. 审核员id
     */
    public final TableField<UserAuditRecord, Long> AUDITOR_ID = createField(DSL.name("auditor_id"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.field(DSL.raw("'-1'"), SQLDataType.BIGINT)), this, "审核员id");

    /**
     * The column <code>uc.user_audit.reason_code</code>. 拒绝理由
     */
    public final TableField<UserAuditRecord, String> REASON_CODE = createField(DSL.name("reason_code"), SQLDataType.VARCHAR(256).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "拒绝理由");

    /**
     * The column <code>uc.user_audit.refuse_reason</code>. 拒绝原因
     */
    public final TableField<UserAuditRecord, String> REFUSE_REASON = createField(DSL.name("refuse_reason"), SQLDataType.VARCHAR(1024).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "拒绝原因");

    /**
     * The column <code>uc.user_audit.env</code>. 环境标
     */
    public final TableField<UserAuditRecord, String> ENV = createField(DSL.name("env"), SQLDataType.VARCHAR(8).nullable(false).defaultValue(DSL.field(DSL.raw("''"), SQLDataType.VARCHAR)), this, "环境标");

    private UserAudit(Name alias, Table<UserAuditRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserAudit(Name alias, Table<UserAuditRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("用户审核表"), TableOptions.table());
    }

    /**
     * Create an aliased <code>uc.user_audit</code> table reference
     */
    public UserAudit(String alias) {
        this(DSL.name(alias), USER_AUDIT);
    }

    /**
     * Create an aliased <code>uc.user_audit</code> table reference
     */
    public UserAudit(Name alias) {
        this(alias, USER_AUDIT);
    }

    /**
     * Create a <code>uc.user_audit</code> table reference
     */
    public UserAudit() {
        this(DSL.name("user_audit"), null);
    }

    public <O extends Record> UserAudit(Table<O> child, ForeignKey<O, UserAuditRecord> key) {
        super(child, key, USER_AUDIT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Uc.UC;
    }

    @Override
    public UniqueKey<UserAuditRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_7;
    }

    @Override
    public UserAudit as(String alias) {
        return new UserAudit(DSL.name(alias), this);
    }

    @Override
    public UserAudit as(Name alias) {
        return new UserAudit(alias, this);
    }

    @Override
    public UserAudit as(Table<?> alias) {
        return new UserAudit(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(String name) {
        return new UserAudit(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(Name name) {
        return new UserAudit(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(Table<?> name) {
        return new UserAudit(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, Integer, Long, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Long, ? super String, ? super Integer, ? super Long, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Long, ? super String, ? super Integer, ? super Long, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
