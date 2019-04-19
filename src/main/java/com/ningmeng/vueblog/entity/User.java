package com.ningmeng.vueblog.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ningmeng.vueblog.vo.UserVO;

import java.io.Serializable;

@JsonIdentityInfo(property = "userId",generator = ObjectIdGenerators.PropertyGenerator.class )
public class User implements Serializable {

    private Integer id;
    private String username;
    private String userUrl;
    private String userAvatar;
    private String userGithubId;

    public User() {
    }

    public User(String username, String userUrl, String userAvatar, String userGithubId) {
        this.username = username;
        this.userUrl = userUrl;
        this.userAvatar = userAvatar;
        this.userGithubId = userGithubId;
    }

    public User(UserVO userVO){
        this.id = userVO.getId();
        this.username = userVO.getUsername();
        this.userUrl = userVO.getUserUrl();
        this.userAvatar = userVO.getUserAvatar();
        this.userGithubId = userVO.getUserGithubId();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userGithubId='" + userGithubId + '\'' +
                '}';
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
}
