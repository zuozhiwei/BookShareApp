package com.hgo.book.tool;


import com.google.gson.Gson;

import java.util.HashMap;

public class JsonTool {
    static Gson gson = new Gson();

    public static String returnPackage(String status,String reason,Object result) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("status",status);
        if (null != reason && !"".equals(reason)) {
            map.put("reason",reason);
        }
        if (null != result && !"".equals(result)) {
            map.put("result",result);
        }
        return gson.toJson(map);
    }
}
