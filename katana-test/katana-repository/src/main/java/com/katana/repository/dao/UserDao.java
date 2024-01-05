package com.katana.repository.dao;

import com.katana.demo.entity.uc.tables.pojos.UserInfo;
import com.katana.demo.entity.uc.tables.records.UserInfoRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.katana.demo.entity.uc.Tables.*;

/**
 * @author : liyifei
 * @created : 2023/10/20, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
@Repository
public class UserDao {

    private DSLContext dslContext;

    @Autowired
    public UserDao(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<UserInfo> abbr(Long[] idList) {
        return dslContext.select().from(USER_INFO)
                .where(USER_INFO.ID.in(idList))
                .fetchInto(UserInfo.class);
    }

    public List<UserInfo> find(String name) {

        com.katana.demo.entity.uc.tables.UserInfo ta = USER_INFO.as("a");
        com.katana.demo.entity.uc.tables.UserAudit tb = USER_AUDIT.as("b");
        com.katana.demo.entity.uc.tables.UserTag td = USER_TAG.as("d");
        // join subquery and where subquery
        return dslContext.select(ta.asterisk()).from(ta)
                .join(
                        dslContext.select(tb.asterisk()).from(tb)
                                .where(tb.NAME.eq(name)
                                ).asTable("c")
                )
                .on(tb.as("c").USERID.eq(ta.ID))
                .where(ta.NAME.eq(name))
                .and(ta.ID.in(dslContext.select(td.USERID).from(td).where(td.USERID.ge(0L))))
                .union(dslContext.select().from(USER_INFO)
                        .where(USER_INFO.NAME.eq(name)))
                .fetchInto(UserInfo.class);
    }


    public List<UserInfo> get(String name) {
        return dslContext.select().from(USER_INFO)
                .where(DSL.val(name).like(USER_INFO.NAME))
                .and(USER_INFO.TENANT_CODE.eq("fail"))
                .fetchInto(UserInfo.class);
    }

    public int insert(String name, String phone, String email, int count) {
        UserInfoRecord[] records = new UserInfoRecord[count];
        for (int i = 0; i < count; i++) {
            UserInfoRecord record = new UserInfoRecord();
            record.setName(name);
            record.setPhone(phone);
            if (i % 2 == 0) {
                record.setEmail(email);
            } else {
                record.setPhone(email);
            }
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            records[i] = record;
        }
        dslContext.batchInsert(records).execute();

        UserInfoRecord record = new UserInfoRecord();
        record.setName(name);
        record.setPhone(phone);
        record.setEmail(email);
        record.setTenantCode("test");
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        UserInfoRecord record2 = new UserInfoRecord();
        record2.setName(name);
        record2.setPhone(phone);
        record2.setEmail(email);
        record2.setTenantCode("test2");
        record2.setCreateTime(new Date());
        record2.setUpdateTime(new Date());
        dslContext.insertInto(USER_INFO).set(record).newRecord().set(record2).execute();
        //id, name, phone, email, create_time, update_time, tenant_code, env
        Field[] fields = new Field[]{USER_INFO.NAME, USER_INFO.PHONE, USER_INFO.EMAIL, USER_INFO.CREATE_TIME, USER_INFO.UPDATE_TIME, USER_INFO.TENANT_CODE, USER_INFO.ENV};
        return dslContext.insertInto(USER_INFO, fields).select(
                dslContext.select(fields)
                        .from(USER_INFO).where(USER_INFO.TENANT_CODE.eq("test")).limit(1)).execute();
    }

    public int update(String name, String phone, String email) {
        return dslContext.update(USER_INFO)
                .set(USER_INFO.EMAIL, email)
                .set(USER_INFO.PHONE, phone)
                .set(USER_INFO.TENANT_CODE, "tenant")
                .where(USER_INFO.NAME.eq(name))
                .and(USER_INFO.ID.in(dslContext.select(USER_INFO.ID).from(USER_INFO).where(USER_INFO.ID.ge(0L))))
                .execute();
    }

    public int delete(String name) {
        return dslContext.delete(USER_INFO)
                .where(USER_INFO.NAME.eq(name))
                .and(USER_INFO.TENANT_CODE.eq("tenant"))
                .execute();
    }
}
