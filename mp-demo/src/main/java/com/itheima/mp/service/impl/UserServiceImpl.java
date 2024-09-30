package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tony
 * @since 2024-09-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public PageDTO<UserVO> pageQueryUsers(UserQuery userQuery) {
        // 1.构建条件
        // 1.1.分页条件
        Page<User> page = Page.of(userQuery.getPageNo(), userQuery.getPageSize());
        // 1.2.排序条件
        if (userQuery.getSortBy() != null) {
            if (userQuery.getIsAsc()) {
                page.addOrder(OrderItem.asc(userQuery.getSortBy()));
            } else {
                page.addOrder(OrderItem.desc(userQuery.getSortBy()));
            }
        }else{
            // 默认按照更新时间排序
            page.addOrder(OrderItem.desc("update_time"));
        }

        // 2.查询 TODO 条件呢？
        page(page);
        // 3.数据非空校验
        List<User> records = page.getRecords();
        if (records == null || records.size() <= 0) {
            // 无数据，返回空结果
            return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
        }
        // 4.有数据，转换
        List<UserVO> list = BeanUtil.copyToList(records, UserVO.class);
        // 5.封装返回
        return new PageDTO<UserVO>(page.getTotal(), page.getPages(), list);
    }
}
