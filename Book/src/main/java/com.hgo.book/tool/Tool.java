package com.hgo.book.tool;

import java.util.HashMap;

public class Tool {

    public static void  hashMapPutTool(HashMap<String,Object> param,String key,Object value) {
        if (null == value) {
            param.put(key,"");
        } else {
            param.put(key,value);
        }
    }
}
