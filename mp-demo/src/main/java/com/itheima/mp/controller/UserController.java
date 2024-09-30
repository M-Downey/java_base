package com.itheima.mp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author tony
 * @since 2024-09-28
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 根据 id 查询用户
     */
    @ApiOperation("根据 id 查询用户")
    @GetMapping("/{id}")
    public UserVO queryUserById(@PathVariable("id") Long id) {
//        User user = userService.getById(id);
//        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
//        return userVO;
        return userService.queryUserAndAddressById(id);
    }

    /**
     * 根据 ids 查询用户集合
     */
    @ApiOperation("根据 ids 查询用户集合")
    @GetMapping()
    public List<UserVO> queryUsersByIds(@RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        List<UserVO> userVOList = BeanUtil.copyToList(users, UserVO.class);
        return userVOList;
    }

    /**
     * 根据条件分页查询用户集合
     */
    @ApiOperation("根据 ids 查询用户集合")
    @GetMapping("/page")
    public PageDTO<UserVO> pageQueryUsersWithCondition(UserQuery userQuery) {
        PageDTO<UserVO> userVOPageDTO = userService.pageQueryUsers(userQuery);
        return userVOPageDTO;
    }

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.removeById(id);
    }
}
