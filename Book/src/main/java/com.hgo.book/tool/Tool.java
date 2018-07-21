package com.hgo.book.tool;

import java.util.HashMap;

public class Tool {

    /**
     * 参数处理，把null转成空字符串
     * @param param
     * @param key
     * @param value
     */
    public static void  hashMapPutTool(HashMap<String,Object> param,String key,Object value) {
        if (null == value) {
            param.put(key,"");
        } else {
            param.put(key,value);
        }
    }
}
