/*
 * This file is generated by jOOQ.
 */
package com.katana.demo.entity.uc.tables.pojos;


import java.io.Serializable;
import java.util.Date;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String contact;
    private Date createTime;
    private Date updateTime;
    private String tenantCode;
    private String env;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.name = value.name;
        this.phone = value.phone;
        this.email = value.email;
        this.contact = value.contact;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.tenantCode = value.tenantCode;
        this.env = value.env;
    }

    public User(
        Long id,
        String name,
        String phone,
        String email,
        String contact,
        Date createTime,
        Date updateTime,
        String tenantCode,
        String env
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.contact = contact;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.tenantCode = tenantCode;
        this.env = env;
    }

    /**
     * Getter for <code>uc.user.id</code>. 账户ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>uc.user.id</code>. 账户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>uc.user.name</code>. 账户名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>uc.user.name</code>. 账户名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>uc.user.phone</code>. 电话
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setter for <code>uc.user.phone</code>. 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for <code>uc.user.email</code>. 邮箱
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for <code>uc.user.email</code>. 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for <code>uc.user.contact</code>. 联系人
     */
    public String getContact() {
        return this.contact;
    }

    /**
     * Setter for <code>uc.user.contact</code>. 联系人
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Getter for <code>uc.user.create_time</code>. 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * Setter for <code>uc.user.create_time</code>. 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Getter for <code>uc.user.update_time</code>. 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * Setter for <code>uc.user.update_time</code>. 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Getter for <code>uc.user.tenant_code</code>. 租户编码
     */
    public String getTenantCode() {
        return this.tenantCode;
    }

    /**
     * Setter for <code>uc.user.tenant_code</code>. 租户编码
     */
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    /**
     * Getter for <code>uc.user.env</code>. 环境
     */
    public String getEnv() {
        return this.env;
    }

    /**
     * Setter for <code>uc.user.env</code>. 环境
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
        final User other = (User) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.phone == null) {
            if (other.phone != null)
                return false;
        }
        else if (!this.phone.equals(other.phone))
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        }
        else if (!this.email.equals(other.email))
            return false;
        if (this.contact == null) {
            if (other.contact != null)
                return false;
        }
        else if (!this.contact.equals(other.contact))
            return false;
        if (this.createTime == null) {
            if (other.createTime != null)
                return false;
        }
        else if (!this.createTime.equals(other.createTime))
            return false;
        if (this.updateTime == null) {
            if (other.updateTime != null)
                return false;
        }
        else if (!this.updateTime.equals(other.updateTime))
            return false;
        if (this.tenantCode == null) {
            if (other.tenantCode != null)
                return false;
        }
        else if (!this.tenantCode.equals(other.tenantCode))
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
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
        result = prime * result + ((this.createTime == null) ? 0 : this.createTime.hashCode());
        result = prime * result + ((this.updateTime == null) ? 0 : this.updateTime.hashCode());
        result = prime * result + ((this.tenantCode == null) ? 0 : this.tenantCode.hashCode());
        result = prime * result + ((this.env == null) ? 0 : this.env.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(phone);
        sb.append(", ").append(email);
        sb.append(", ").append(contact);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(tenantCode);
        sb.append(", ").append(env);

        sb.append(")");
        return sb.toString();
    }
}
