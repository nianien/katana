package com.katana.jooq.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : liyifei
 * @created : 2023/11/3, 星期五
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class LongSqlTest {

    @Test
    public void test() {
        String sql = "select * from user where id  in (1001,1001, 1002, 1003, 1004, 1005, 1006, 1007) and type in('a','b','c','d','e','f','g','h','i','j','k','l','m')) and name in(select name from user where name in(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?))";
        String abbrSql = sql.replaceAll("(([^,]+,){5})([^)]*)", "$1...");
        System.out.println(abbrSql);
        Assertions.assertEquals("select * from user where id  in (1001,1001, 1002, 1003, 1004,...) and type in('a','b','c','d','e',...)) and name in(select name from user where name in(?,?,?,?,?,...))", abbrSql);
    }
}
