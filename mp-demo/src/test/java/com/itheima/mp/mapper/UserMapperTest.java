package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
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
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
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

    /**
     * 测试 queryWrapper
     */
    @Test
    void testQueryWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .select("id", "username", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 测试 updateWrapper，设置id 1，2，4 用户余额-200
     */
    @Test
    void testUpdateWrapper1() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = 2000")
                        .eq("username", "Jack");
        userMapper.update(wrapper);
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUsername, "Jack"));
        users.forEach(System.out::println);
    }
    @Test
    void testUpdateWrapper2() {
        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", ids);
        userMapper.update(userUpdateWrapper);
    }

    /**
     * 测试 lambdaQueryWrapper
     */
    @Test
    void testLambdaQueryWrapper() {
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>()
                .lambda()
                .select(User::getId, User::getUsername, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 测试自定义 sql 语句，业务层不能出现 balance=balance-200 这种语句
     */
    @Test
    void testCustomSql() {
        List<Long> ids = List.of(1L, 2L, 4L);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getId, ids);
        int num = userMapper.deductBalanceByIds(200, wrapper);
        System.out.println(num);
    }

    /**
     * 测试自定义sql+多表联查
     */
    @Test
    void testJoinQuery() {
        // user 和 address 表联查 id 在1，2，4并且 address 是北京的用户
        // select * from user u join address a on a.user_id = u.id where id in (1, 2, 4) and a.city = 'beijing'
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("u.id", List.of(1L, 2L, 4L))
                .eq("a.city", "武汉")
                .orderByAsc("u.id");
        List<User> users = userMapper.queryUserByWrapper(wrapper);
        users.forEach(System.out::println);
    }
}