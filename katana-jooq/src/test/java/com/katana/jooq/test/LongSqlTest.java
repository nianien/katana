package com.katana.jooq.test;

import org.jooq.impl.PerformanceListener;
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
        String sql = "select * from user where id  in (1001,1001, 1002, 1003, 1004, 1005, 1006, 1007) and name in(select name from whitelist where name in('a','b','c','d','e','f','g','h','i','j','k','l','m'))";
        String[] strings = PerformanceListener.sqlAbbrPatterns(5);
        String abbrSql = sql.replaceAll(strings[0], strings[1]);
        System.out.println(abbrSql);
        Assertions.assertEquals("select * from user where id  in (1001,1001, 1002, 1003, 1004,...) and name in(select name from whitelist where name in('a','b','c','d','e',...))", abbrSql);

    }

    @Test
    public void test2() {
        String sql = "select\n" +
                "  user_info.id,\n" +
                "  user_info.name,\n" +
                "  user_info.phone,\n" +
                "  user_info.email,\n" +
                "  user_info.contact,\n" +
                "  user_info.create_time,\n" +
                "  user_info.update_time,\n" +
                "  user_info.tenant_code,\n" +
                "  user_info.env\n" +
                "from user_info\n" +
                "where (\n" +
                "  user_info.id in (\n" +
                "    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, \n" +
                "    23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, \n" +
                "    42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, \n" +
                "    61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, \n" +
                "    80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, \n" +
                "    99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, \n" +
                "    115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, \n" +
                "    131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, \n" +
                "  )\n" +
                "  and user_info.env = 'local'\n" +
                "  and user_info.tenant_code = 'public'\n" +
                ")";
        String[] strings = PerformanceListener.sqlAbbrPatterns(5);
        String abbrSql = sql.replaceAll(strings[0], strings[1]);
        System.out.println(abbrSql);

    }

}
