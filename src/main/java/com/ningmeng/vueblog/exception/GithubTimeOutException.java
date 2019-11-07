package com.ningmeng.vueblog.exception;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/6 下午11:29
 * @Version 1.0
 */
public class GithubTimeOutException extends Exception {

    public GithubTimeOutException(String message) {
        super(message);
    }
}
