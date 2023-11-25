/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables.records;


import com.katana.demo.entity.uc.tables.UserTag;

import java.util.Date;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户标签表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserTagRecord extends UpdatableRecordImpl<UserTagRecord> implements Record6<Long, Long, String, Date, Date, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>uc.user_tag.id</code>. 主键
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>uc.user_tag.id</code>. 主键
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>uc.user_tag.userid</code>. 用户id
     */
    public void setUserid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>uc.user_tag.userid</code>. 用户id
     */
    public Long getUserid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>uc.user_tag.tag</code>. 账户标签
     */
    public void setTag(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>uc.user_tag.tag</code>. 账户标签
     */
    public String getTag() {
        return (String) get(2);
    }

    /**
     * Setter for <code>uc.user_tag.create_time</code>. 创建时间
     */
    public void setCreateTime(Date value) {
        set(3, value);
    }

    /**
     * Getter for <code>uc.user_tag.create_time</code>. 创建时间
     */
    public Date getCreateTime() {
        return (Date) get(3);
    }

    /**
     * Setter for <code>uc.user_tag.update_time</code>. 修改时间
     */
    public void setUpdateTime(Date value) {
        set(4, value);
    }

    /**
     * Getter for <code>uc.user_tag.update_time</code>. 修改时间
     */
    public Date getUpdateTime() {
        return (Date) get(4);
    }

    /**
     * Setter for <code>uc.user_tag.env</code>. 环境标
     */
    public void setEnv(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>uc.user_tag.env</code>. 环境标
     */
    public String getEnv() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, String, Date, Date, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, Long, String, Date, Date, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return UserTag.USER_TAG.ID;
    }

    @Override
    public Field<Long> field2() {
        return UserTag.USER_TAG.USERID;
    }

    @Override
    public Field<String> field3() {
        return UserTag.USER_TAG.TAG;
    }

    @Override
    public Field<Date> field4() {
        return UserTag.USER_TAG.CREATE_TIME;
    }

    @Override
    public Field<Date> field5() {
        return UserTag.USER_TAG.UPDATE_TIME;
    }

    @Override
    public Field<String> field6() {
        return UserTag.USER_TAG.ENV;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getUserid();
    }

    @Override
    public String component3() {
        return getTag();
    }

    @Override
    public Date component4() {
        return getCreateTime();
    }

    @Override
    public Date component5() {
        return getUpdateTime();
    }

    @Override
    public String component6() {
        return getEnv();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getUserid();
    }

    @Override
    public String value3() {
        return getTag();
    }

    @Override
    public Date value4() {
        return getCreateTime();
    }

    @Override
    public Date value5() {
        return getUpdateTime();
    }

    @Override
    public String value6() {
        return getEnv();
    }

    @Override
    public UserTagRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserTagRecord value2(Long value) {
        setUserid(value);
        return this;
    }

    @Override
    public UserTagRecord value3(String value) {
        setTag(value);
        return this;
    }

    @Override
    public UserTagRecord value4(Date value) {
        setCreateTime(value);
        return this;
    }

    @Override
    public UserTagRecord value5(Date value) {
        setUpdateTime(value);
        return this;
    }

    @Override
    public UserTagRecord value6(String value) {
        setEnv(value);
        return this;
    }

    @Override
    public UserTagRecord values(Long value1, Long value2, String value3, Date value4, Date value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserTagRecord
     */
    public UserTagRecord() {
        super(UserTag.USER_TAG);
    }

    /**
     * Create a detached, initialised UserTagRecord
     */
    public UserTagRecord(Long id, Long userid, String tag, Date createTime, Date updateTime, String env) {
        super(UserTag.USER_TAG);

        setId(id);
        setUserid(userid);
        setTag(tag);
        setCreateTime(createTime);
        setUpdateTime(updateTime);
        setEnv(env);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UserTagRecord
     */
    public UserTagRecord(com.katana.demo.entity.uc.tables.pojos.UserTag value) {
        super(UserTag.USER_TAG);

        if (value != null) {
            setId(value.getId());
            setUserid(value.getUserid());
            setTag(value.getTag());
            setCreateTime(value.getCreateTime());
            setUpdateTime(value.getUpdateTime());
            setEnv(value.getEnv());
            resetChangedOnNotNull();
        }
    }
}
