package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.User;

public interface UserService {

    void login(User user);

    User getUser();
}
