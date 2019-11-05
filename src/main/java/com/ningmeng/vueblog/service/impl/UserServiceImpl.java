package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ningmeng.vueblog.entity.User;
import com.ningmeng.vueblog.mapper.UserMapper;
import com.ningmeng.vueblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;


    @Override
    public User getUser(User user) {
        return userMapper.getUser(user);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User findUserByAccountId(Long id) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("account_id", id));
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
