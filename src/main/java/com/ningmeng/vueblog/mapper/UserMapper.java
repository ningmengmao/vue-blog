package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ningmeng.vueblog.entity.User;

public interface UserMapper extends BaseMapper<User> {

    public User getUser(User user);

    public User selectById(int id);
}
