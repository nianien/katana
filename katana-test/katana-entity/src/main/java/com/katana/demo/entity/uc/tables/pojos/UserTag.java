/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables.pojos;


import java.io.Serializable;
import java.util.Date;


/**
 * 用户标签表
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userid;
    private String tag;
    private Date createTime;
    private Date modifyTime;
    private String env;

    public UserTag() {}

    public UserTag(UserTag value) {
        this.id = value.id;
        this.userid = value.userid;
        this.tag = value.tag;
        this.createTime = value.createTime;
        this.modifyTime = value.modifyTime;
        this.env = value.env;
    }

    public UserTag(
        Long id,
        Long userid,
        String tag,
        Date createTime,
        Date modifyTime,
        String env
    ) {
        this.id = id;
        this.userid = userid;
        this.tag = tag;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.env = env;
    }

    /**
     * Getter for <code>uc.user_tag.id</code>. 主键
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>uc.user_tag.id</code>. 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>uc.user_tag.userid</code>. 用户id
     */
    public Long getUserid() {
        return this.userid;
    }

    /**
     * Setter for <code>uc.user_tag.userid</code>. 用户id
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * Getter for <code>uc.user_tag.tag</code>. 账户标签
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Setter for <code>uc.user_tag.tag</code>. 账户标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Getter for <code>uc.user_tag.create_time</code>. 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * Setter for <code>uc.user_tag.create_time</code>. 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter for <code>uc.user_tag.modify_time</code>. 修改时间
     */
    public Date getModifyTime() {
        return this.modifyTime;
    }

    /**
     * Setter for <code>uc.user_tag.modify_time</code>. 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * Getter for <code>uc.user_tag.env</code>. 环境标
     */
    public String getEnv() {
        return this.env;
    }

    /**
     * Setter for <code>uc.user_tag.env</code>. 环境标
     */
    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserTag other = (UserTag) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.userid == null) {
            if (other.userid != null)
                return false;
        }
        else if (!this.userid.equals(other.userid))
            return false;
        if (this.tag == null) {
            if (other.tag != null)
                return false;
        }
        else if (!this.tag.equals(other.tag))
            return false;
        if (this.createTime == null) {
            if (other.createTime != null)
                return false;
        }
        else if (!this.createTime.equals(other.createTime))
            return false;
        if (this.modifyTime == null) {
            if (other.modifyTime != null)
                return false;
        }
        else if (!this.modifyTime.equals(other.modifyTime))
            return false;
        if (this.env == null) {
            if (other.env != null)
                return false;
        }
        else if (!this.env.equals(other.env))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.userid == null) ? 0 : this.userid.hashCode());
        result = prime * result + ((this.tag == null) ? 0 : this.tag.hashCode());
        result = prime * result + ((this.createTime == null) ? 0 : this.createTime.hashCode());
        result = prime * result + ((this.modifyTime == null) ? 0 : this.modifyTime.hashCode());
        result = prime * result + ((this.env == null) ? 0 : this.env.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserTag (");

        sb.append(id);
        sb.append(", ").append(userid);
        sb.append(", ").append(tag);
        sb.append(", ").append(createTime);
        sb.append(", ").append(modifyTime);
        sb.append(", ").append(env);

        sb.append(")");
        return sb.toString();
    }
}
