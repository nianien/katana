package com.katana.webapp.controller;

import com.katana.demo.entity.uc.tables.pojos.User;
import com.katana.repository.dao.UserDao;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private UserDao accountDao;


    /**
     * 查询用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/find/{name}")
    public List<User> find(@PathVariable String name) {
        return accountDao.find(name);
    }

    /**
     * 查询用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/{name}")
    public List<User> get(@PathVariable String name) {
        return accountDao.get(name);
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
    public List<User> register(@PathVariable String name, @PathVariable String phone, @PathVariable String email) {
        accountDao.insert(name, phone, email, 2);
        return get(name);
    }


    /**
     * 修改用户
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/update/{name}/{phone}/{email}")
    public List<User> put(@PathVariable String name, @PathVariable String phone, @PathVariable String email) {
        accountDao.update(name, phone, email);
        return get(name);
    }

    /**
     * 用户查询
     *
     * @param name 用户名
     * @return
     */
    @GetMapping("/delete/{name}")
    public List<User> delete(@PathVariable String name) {
        accountDao.delete(name);
        return get(name);
    }
}
