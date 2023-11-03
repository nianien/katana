package com.katana.webapp.controller;

import com.katana.demo.entity.uc.tables.pojos.UserInfo;
import com.katana.repository.dao.UserDao;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.LongStream;

/**
 * 用户管理模块
 * Created on 2022/1/24
 *
 * @author liyifei
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Resource
    private UserDao userDao;

    /**
     * 查询用户数
     *
     * @param limit 用户名
     * @return
     */
    @GetMapping("/abbr/{limit}")
    public List<UserInfo> abbr(@PathVariable int limit) {
        return userDao.abbr(LongStream.range(1, limit + 1).mapToObj(e -> e).toArray(n -> new Long[n]));
    }

    /**
     * 查询用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/find/{name}")
    public List<UserInfo> find(@PathVariable String name) {
        return userDao.find(name);
    }

    /**
     * 查询用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/{name}")
    public List<UserInfo> get(@PathVariable String name) {
        return userDao.get(name);
    }

    /**
     * 注册用户
     *
     * @param name  用户名
     * @param phone 电话
     * @param email 邮箱
     * @return 注册结果
     */
    @GetMapping("/insert/{name}/{phone}/{email}")
    public List<UserInfo> register(@PathVariable String name, @PathVariable String phone, @PathVariable String email) {
        userDao.insert(name, phone, email, 2);
        return get(name);
    }


    /**
     * 修改用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/update/{name}/{phone}/{email}")
    public List<UserInfo> put(@PathVariable String name, @PathVariable String phone, @PathVariable String email) {
        userDao.update(name, phone, email);
        return get(name);
    }

    /**
     * 用户查询
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/delete/{name}")
    public List<UserInfo> delete(@PathVariable String name) {
        userDao.delete(name);
        return get(name);
    }
}
