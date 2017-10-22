package com.example;

import java.util.Base64;

/**
 * Created by Larry-sea on 2016/11/4.
 * <p>
 * 测试base64编码解码
 */

public class testBase64 {

    public static void main(String args[]) {
        String testString="hello 中文world";
        String encodeString=new String(Base64.getEncoder().encode(testString.getBytes()));
        Base64.getDecoder().decode(encodeString.getBytes());
    }

}
