package com.asher.flume.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class MyJsonUtil {

    //判断一个字符串是否是Json格式
    public static boolean isValidJson(String s) {
        try {
            JSON.parse(s);
            return true;
        }catch (JSONException e){
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValidJson("fadsfadf"));
    }
}
