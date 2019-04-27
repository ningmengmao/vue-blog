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
    public void login(User user) {
        User u = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .eq("user_github_id", user.getUserGithubId())
        );
        if (u != null && u.getUsername()!= null && u.getUserGithubId() != null)
            if (!u.getUserGithubId().equals(user.getUserGithubId()) || !u.getUsername().equals(user.getUsername()))
                throw new RuntimeException("登录失败");
    }

    @Override
    public User getUser() {
        return null;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
