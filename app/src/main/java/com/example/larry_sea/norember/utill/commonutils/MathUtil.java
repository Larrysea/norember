package com.example.larry_sea.norember.utill.commonutils;

/**
 * Created by Larry-sea on 10/12/2016.
 * <p>
 * 数学常用工具类
 */

public class MathUtil {

    /*
    * 获取随机数
    * @param length
    *
    * */
    static public String random(int length) {
        String strRand = "";
        for (int i = 0; i < length; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }
}
