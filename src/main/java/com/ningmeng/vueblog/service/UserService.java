package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.User;

public interface UserService {

    public User getUser(User user);

    public int addUser(User user);

    public User findUserByAccountId(Long id);
}
