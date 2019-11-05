package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserVO {

    private String username;
    private String userUrl;
    private String avatarUrl;
    private String bio;

    public static UserVO newInstance(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
