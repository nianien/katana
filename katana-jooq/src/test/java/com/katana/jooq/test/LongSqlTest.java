package com.katana.jooq.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : skyfalling
 * @created : 2023/11/3, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class LongSqlTest {

    @Test
    public void test() {
        int limit = 5;
        String sql = "select * from user where id  in (1001,1001, 1002, 1003, 1004, 1005, 1006, 1007) and name in(select name from whitelist where name in('a','b','c','d','e','f','g','h','i','j','k','l','m'))";
        String abbrSql = sql.replaceAll("(([^,]+,){"+limit+"})([^)]*)", "$1...");
        System.out.println(abbrSql);
        Assertions.assertEquals("select * from user where id  in (1001,1001, 1002, 1003, 1004,...) and name in(select name from whitelist where name in('a','b','c','d','e',...))", abbrSql);
    }
}
