package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void testPageQuery() {
        Page<User> page = userService.page(new Page<>(1, 2));
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }
}
