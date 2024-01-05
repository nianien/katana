package com.katana.repository.test;

import com.katana.repository.dao.UserDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : liyifei
 * @created : 2023/10/30, 星期一
 * Copyright (c) 2004-2029 All Rights Reserved.
 **/
@SpringBootTest(classes = ApplicationTest.class)
public class UserDaoTest {

    @Resource
    private UserDao userDao;


    @Test
    public void test() {
        userDao.find("liyifei");
        userDao.get("liyifei");
        userDao.delete("liyifei");
    }

}
