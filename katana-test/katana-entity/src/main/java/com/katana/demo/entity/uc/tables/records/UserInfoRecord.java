/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables.records;


import com.katana.demo.entity.uc.tables.UserInfo;

import java.util.Date;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户信息表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserInfoRecord extends UpdatableRecordImpl<UserInfoRecord> implements Record9<Long, String, String, String, String, Date, Date, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>uc.user_info.id</code>. 账户ID
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>uc.user_info.id</code>. 账户ID
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>uc.user_info.name</code>. 账户名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>uc.user_info.name</code>. 账户名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>uc.user_info.phone</code>. 电话
     */
    public void setPhone(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>uc.user_info.phone</code>. 电话
     */
    public String getPhone() {
        return (String) get(2);
    }

    /**
     * Setter for <code>uc.user_info.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>uc.user_info.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>uc.user_info.contact</code>. 联系人
     */
    public void setContact(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>uc.user_info.contact</code>. 联系人
     */
    public String getContact() {
        return (String) get(4);
    }

    /**
     * Setter for <code>uc.user_info.create_time</code>. 创建时间
     */
    public void setCreateTime(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>uc.user_info.create_time</code>. 创建时间
     */
    public Date getCreateTime() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>uc.user_info.update_time</code>. 更新时间
     */
    public void setUpdateTime(Date value) {
        set(6, value);
    }

    /**
     * Getter for <code>uc.user_info.update_time</code>. 更新时间
     */
    public Date getUpdateTime() {
        return (Date) get(6);
    }

    /**
     * Setter for <code>uc.user_info.tenant_code</code>. 租户编码
     */
    public void setTenantCode(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>uc.user_info.tenant_code</code>. 租户编码
     */
    public String getTenantCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>uc.user_info.env</code>. 环境
     */
    public void setEnv(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>uc.user_info.env</code>. 环境
     */
    public String getEnv() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, String, Date, Date, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, String, String, String, String, Date, Date, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserInfo.USER_INFO.ID;
    }

    @Override
    public Field<String> field2() {
        return UserInfo.USER_INFO.NAME;
    }

    @Override
    public Field<String> field3() {
        return UserInfo.USER_INFO.PHONE;
    }

    @Override
    public Field<String> field4() {
        return UserInfo.USER_INFO.EMAIL;
    }

    @Override
    public Field<String> field5() {
        return UserInfo.USER_INFO.CONTACT;
    }

    @Override
    public Field<Date> field6() {
        return UserInfo.USER_INFO.CREATE_TIME;
    }

    @Override
    public Field<Date> field7() {
        return UserInfo.USER_INFO.UPDATE_TIME;
    }

    @Override
    public Field<String> field8() {
        return UserInfo.USER_INFO.TENANT_CODE;
    }

    @Override
    public Field<String> field9() {
        return UserInfo.USER_INFO.ENV;
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
    public String component5() {
        return getContact();
    }

    @Override
    public Date component6() {
        return getCreateTime();
    }

    @Override
    public Date component7() {
        return getUpdateTime();
    }

    @Override
    public String component8() {
        return getTenantCode();
    }

    @Override
    public String component9() {
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
    public String value5() {
        return getContact();
    }

    @Override
    public Date value6() {
        return getCreateTime();
    }

    @Override
    public Date value7() {
        return getUpdateTime();
    }

    @Override
    public String value8() {
        return getTenantCode();
    }

    @Override
    public String value9() {
        return getEnv();
    }

    @Override
    public UserInfoRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserInfoRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public UserInfoRecord value3(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserInfoRecord value4(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserInfoRecord value5(String value) {
        setContact(value);
        return this;
    }

    @Override
    public UserInfoRecord value6(Date value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public UserInfoRecord value7(Date value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public UserInfoRecord value8(String value) {
        setTenantCode(value);
        return this;
    }

    @Override
    public UserInfoRecord value9(String value) {
        setEnv(value);
        return this;
    }

    @Override
    public UserInfoRecord values(Long value1, String value2, String value3, String value4, String value5, Date value6, Date value7, String value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserInfoRecord
     */
    public UserInfoRecord() {
        super(UserInfo.USER_INFO);
    }

    /**
     * Create a detached, initialised UserInfoRecord
     */
    public UserInfoRecord(Long id, String name, String phone, String email, String contact, Date createTime, Date updateTime, String tenantCode, String env) {
        super(UserInfo.USER_INFO);

        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setContact(contact);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        setTenantCode(tenantCode);
        setEnv(env);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UserInfoRecord
     */
    public UserInfoRecord(com.katana.demo.entity.uc.tables.pojos.UserInfo value) {
        super(UserInfo.USER_INFO);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
            setPhone(value.getPhone());
            setEmail(value.getEmail());
            setContact(value.getContact());
            setCreateTime(value.getCreateTime());
            setUpdateTime(value.getUpdateTime());
            setTenantCode(value.getTenantCode());
            setEnv(value.getEnv());
            resetChangedOnNotNull();
        }
    }
}
