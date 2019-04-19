package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.User;

public class UserVO {

    private Integer id;
    private String username;
    private String userUrl;
    private String userAvatar;
    private String userGithubId;

    public UserVO() {
    }

    public UserVO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.userUrl = user.getUserUrl();
        this.userAvatar = user.getUserAvatar();
        this.userGithubId = user.getUserGithubId();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserGithubId() {
        return userGithubId;
    }

    public void setUserGithubId(String userGithubId) {
        this.userGithubId = userGithubId;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userGithubId='" + userGithubId + '\'' +
                '}';
    }
}
