package com.katana.jooq.test;

import org.jooq.SQLDialect;
import org.jooq.impl.SqlAbbreviator;
import org.junit.jupiter.api.Test;

/**
 * @author : liyifei
 * @created : 2023/11/13, 星期一
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
public class SqlAbbrTest {
    private static SqlAbbreviator sqlAbbreviator = new SqlAbbreviator(5, SQLDialect.MYSQL);


    @Test
    public void testSelectIn() {
        String sql = "select * from user where id  in " +
                "(" +
                "select id from user_extra where id in(?,?, ?, ?, ?, ?, ?, ?) and type in(?) and name in" +
                "(select name from white_list where name in('a','b','c','d','e','f','g','h','i','j','k','l','m'))" +
                ")";
        System.out.println(sqlAbbreviator.abbr(sql));
    }

    @Test
    public void testInsertValues() {
        String sql = "insert into user_info(\n" +
                "  id,\n" +
                "  name,\n" +
                "  phone,\n" +
                "  email,\n" +
                "  contact,\n" +
                "  create_time,\n" +
                "  update_time,\n" +
                "  tenant_code,\n" +
                "  env\n" +
                ")\n" +
                "values (1, 2, 3, 4, 5, 6, 7, 8, 9),(10, 11, 12, 13, 14, 15, 16, 17, 18),(19, 20, 21, 22, 23, 24, 25, 26, 27),\n" +
                "(28, 29, 30, 31, 32, 33, 34, 35, 36),\n" +
                "(37, 38, 39, 40, 41, 42, 43, 44, 45),\n" +
                "(46, 47, 48, 49, 50, 51, 52, 53, 54),\n" +
                "(55, 56, 57, 58, 59, 60, 61, 62, 63),\n" +
                "(64, 65, 66, 67, 68, 69, 70, 71, 72),\n" +
                "(73, 74, 75, 76, 77, 78, 79, 80, 81),\n" +
                "(82, 83, 84, 85, 86, 87, 88, 89, 90),\n" +
                "(91, 92, 93, 94, 95, 96, 97, 98, 99),\n" +
                "(100, 101, 102, 103, 104, 105, 106, 107, 108),\n" +
                "(109, 110, 111, 112, 113, 114, 115, 116, 117),\n" +
                "(118, 119, 120, 121, 122, 123, 124, 125, 126),\n" +
                "(127, 128, 129, 130, 131, 132, 133, 134, 135),\n" +
                "(136, 137, 138, 139, 140, 141, 142, 143, 144)\n";
        System.out.println(sqlAbbreviator.abbr(sql));

    }


}
