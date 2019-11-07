package com.ningmeng.vueblog.exceptionHandler;

import com.ningmeng.vueblog.exception.GithubTimeOutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午6:34
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Map<String, Object> notFoundHandler() {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("errorMessage", "page not found");
        return map;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = GithubTimeOutException.class)
    public Map<String, Object> githubConnectTimeOutHandler() {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("errorMessage", "服务端链接github超时,请重试");
        return map;
    }
}
