package com.fly.blog.controller;

import com.fly.blog.entity.User;
import com.fly.blog.entity.UserExample;
import com.fly.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XXX
 * @since 2018-04-09
 */

@RestController
@RequestMapping("/users")
@Api(value = "用户相关api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @ApiOperation(value = "hello")
    @GetMapping("hello")
    public String sayHello() {
        return "Hello world!";
    }

    @ApiOperation(value = "通过ID获取用户")
    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {

        User user = userService.selectByPrimaryKey(id);
        return user;
    }

    @ApiOperation(value = "通过Name获取用户")
    @GetMapping("name/{name}")
    public User getUserByName(@PathVariable String name) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(name);
        List<User> users = userService.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @ApiOperation(value = "获取所有用户")
    @GetMapping
    public List<User> getUsers() {

        List<User> users = userService.selectByExample(new UserExample());
        if (CollectionUtils.isEmpty(users)) {
            return new ArrayList<>();
        }
        return users;
    }

    @ApiOperation(value = "添加用户")
    @PostMapping
    public String addUsers(User user) {
        int count = 0;
        try {
            count = userService.insertSelective(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return count > 0 ? "success" : "error";
    }

    @ApiOperation(value = "查询用户")
    @PostMapping("search/")
    public List<User> searchUsers(@RequestBody User userExample) {
        System.out.println(userExample);
        return new ArrayList<>();
    }

}
