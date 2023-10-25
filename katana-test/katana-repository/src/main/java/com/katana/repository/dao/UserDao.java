package com.katana.repository.dao;

import com.katana.demo.entity.uc.tables.pojos.User;
import com.katana.demo.entity.uc.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.katana.demo.entity.uc.Tables.*;

/**
 * @author : liyifei
 * @created : 2023/10/20, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
@Repository
public class UserDao {

    @Resource
    private DSLContext dslContext;

    public List<User> find(String name) {

        com.katana.demo.entity.uc.tables.User ta = USER.as("a");
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
                .union(dslContext.select().from(USER)
                        .where(USER.NAME.eq(name)))
                .fetchInto(User.class);
    }


    public List<User> get(String name) {
        return dslContext.select().from(USER)
                .where(USER.NAME.like(name))
                .fetchInto(User.class);
    }

    public int insert(String name, String phone, String email, int count) {
        UserRecord[] records = new UserRecord[count];
        for (int i = 0; i < count; i++) {
            UserRecord record = new UserRecord();
            record.setName(name);
            record.setPhone(phone);
            if (i % 2 == 0) {
                record.setEmail(email);
            } else {
                record.setPhone(email);
            }

            records[i] = record;
        }
        dslContext.batchInsert(records).execute();

        UserRecord record = new UserRecord();
        record.setName(name);
        record.setPhone(phone);
        record.setEmail(email);
        record.setTenantCode("test");
        UserRecord record2 = new UserRecord();
        record2.setName(name);
        record2.setPhone(phone);
        record2.setEmail(email);
        record2.setTenantCode("test2");
        dslContext.insertInto(USER).set(record).newRecord().set(record2).execute();
        //id, name, phone, email, create_time, update_time, tenant_code, env
        Field[] fields = new Field[]{USER.NAME, USER.PHONE, USER.EMAIL, USER.CREATE_TIME, USER.UPDATE_TIME, USER.TENANT_CODE, USER.ENV};
        return dslContext.insertInto(USER, fields).select(
                dslContext.select(fields)
                        .from(USER).where(USER.TENANT_CODE.eq("test")).limit(1)).execute();
    }

    public int update(String name, String phone, String email) {
        return dslContext.update(USER)
                .set(USER.EMAIL, email)
                .set(USER.PHONE, phone)
                .set(USER.TENANT_CODE, "tenant")
                .where(USER.NAME.eq(name))
                .and(USER.ID.in(dslContext.select(USER.ID).from(USER).where(USER.ID.ge(0L))))
                .execute();
    }

    public int delete(String name) {
        return dslContext.delete(USER)
                .where(USER.NAME.eq(name))
                .execute();
    }
}
