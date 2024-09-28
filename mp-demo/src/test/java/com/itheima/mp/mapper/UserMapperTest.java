package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;

    @Test
    void testInsert() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
//        userMapper.saveUser(user);
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
//        User user = userMapper.queryUserById(5L);
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
//        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
//        userMapper.updateUser(user);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteUser(5L);
    }

    @Test
    void testPageQuery() {
        Page<User> page = userService.page(new Page<>(1, 2));
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }
}