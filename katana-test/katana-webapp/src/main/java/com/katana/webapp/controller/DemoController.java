package com.katana.webapp.controller;

import com.katana.demo.entity.uc.tables.pojos.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 测试模块
 *
 * @author liyifei Created on 2022/1/24
 */
@RestController
@RequestMapping("demo")
public class DemoController {


    /**
     * 模拟用户查询
     *
     * @param limit 用户个数
     * @return
     * @ignoreParam userInfo 用户信息
     * @apiNote 模拟用户查询, 这里使用@ignoreParam 隐藏了userInfo参数
     */
    @GetMapping("/test/{limit}")
    public List<UserInfo> dummy(@PathVariable int limit, UserInfo userInfo) {
        return IntStream.range(0, limit).mapToObj(n -> new UserInfo()).toList();
    }


}
