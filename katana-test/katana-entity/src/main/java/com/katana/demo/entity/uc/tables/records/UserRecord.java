/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables.records;


import com.katana.demo.entity.uc.tables.User;

import java.util.Date;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record8<Long, String, String, String, Date, Date, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>uc.User.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>uc.User.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>uc.User.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>uc.User.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>uc.User.phone</code>.
     */
    public void setPhone(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>uc.User.phone</code>.
     */
    public String getPhone() {
        return (String) get(2);
    }

    /**
     * Setter for <code>uc.User.email</code>.
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>uc.User.email</code>.
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>uc.User.create_time</code>.
     */
    public void setCreateTime(Date value) {
        set(4, value);
    }

    /**
     * Getter for <code>uc.User.create_time</code>.
     */
    public Date getCreateTime() {
        return (Date) get(4);
    }

    /**
     * Setter for <code>uc.User.update_time</code>.
     */
    public void setUpdateTime(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>uc.User.update_time</code>.
     */
    public Date getUpdateTime() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>uc.User.tenant_code</code>.
     */
    public void setTenantCode(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>uc.User.tenant_code</code>.
     */
    public String getTenantCode() {
        return (String) get(6);
    }

    /**
     * Setter for <code>uc.User.env</code>.
     */
    public void setEnv(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>uc.User.env</code>.
     */
    public String getEnv() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, String, Date, Date, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Long, String, String, String, Date, Date, String, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return User.USER.ID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.NAME;
    }

    @Override
    public Field<String> field3() {
        return User.USER.PHONE;
    }

    @Override
    public Field<String> field4() {
        return User.USER.EMAIL;
    }

    @Override
    public Field<Date> field5() {
        return User.USER.CREATE_TIME;
    }

    @Override
    public Field<Date> field6() {
        return User.USER.UPDATE_TIME;
    }

    @Override
    public Field<String> field7() {
        return User.USER.TENANT_CODE;
    }

    @Override
    public Field<String> field8() {
        return User.USER.ENV;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getPhone();
    }

    @Override
    public String component4() {
        return getEmail();
    }

    @Override
    public Date component5() {
        return getCreateTime();
    }

    @Override
    public Date component6() {
        return getUpdateTime();
    }

    @Override
    public String component7() {
        return getTenantCode();
    }

    @Override
    public String component8() {
        return getEnv();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getPhone();
    }

    @Override
    public String value4() {
        return getEmail();
    }

    @Override
    public Date value5() {
        return getCreateTime();
    }

    @Override
    public Date value6() {
        return getUpdateTime();
    }

    @Override
    public String value7() {
        return getTenantCode();
    }

    @Override
    public String value8() {
        return getEnv();
    }

    @Override
    public UserRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserRecord value5(Date value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public UserRecord value6(Date value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public UserRecord value7(String value) {
        setTenantCode(value);
        return this;
    }

    @Override
    public UserRecord value8(String value) {
        setEnv(value);
        return this;
    }

    @Override
    public UserRecord values(Long value1, String value2, String value3, String value4, Date value5, Date value6, String value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Long id, String name, String phone, String email, Date createTime, Date updateTime, String tenantCode, String env) {
        super(User.USER);

        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        setTenantCode(tenantCode);
        setEnv(env);
    }
}
