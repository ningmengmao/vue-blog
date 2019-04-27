package com.ningmeng.vueblog.util;


import java.util.HashMap;
import java.util.Map;

public class MyJson {

    public final static int SUCCESS = 200;
    public final static int BAD_REQUEST = 400;

    public static Map<String, Object> toJson(int status, String message, Object obj){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("data", obj);
        return map;
    }
}
