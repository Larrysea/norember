package com.example.larry_sea.norember.utill;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Larry-sea on 2016/9/6.
 * <p/>
 * 密码强度检测工具
 */
public class CheckPswMeter {
    private String psw;
    private int length;//密码长度
    private int upperAlp = 0;//大写字母长度
    private int lowerAlp = 0;//小写字母长度
    private int num = 0;//数字长度
    private int charlen = 0;//特殊字符长度
    private int addCode;    //加分项
    private int deduction;  //减分项


    /**
     * =================分割线===加分项目=====================
     **/


    //密码长度积分
    public int CheckPswLength() {
        return this.length * 4;
    }

    //大写字母积分
    public int CheckPswUpper() {
        String reg = "[A-Z]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(psw);
        int j = 0;
        while (matcher.find()) {
            j++;
        }
        this.upperAlp = j;
        if (j <= 0) {
            return 0;
        }
        return (this.length - j) * 2;
    }

    //测试小写字母字元
    public int CheckPwsLower() {
        String reg = "[a-z]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(this.psw);
        int j = 0;
        while (matcher.find()) {
            j++;
        }
        this.lowerAlp = j;
        if (j <= 0) {
            return 0;
        }
        return (this.length - j) * 2;
    }

    //测试数字字元
    public int checkNum() {
        String reg = "[0-9]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(this.psw);
        int j = 0;
        while (matcher.find()) {
            j++;
        }
        this.num = j;
        if (this.num == this.length) {
            return 0;
        }
        return j * 4;
    }

    //测试符号字元
    public int checkChar() {
        charlen = this.length - this.upperAlp
                - this.lowerAlp - this.num;
        return this.charlen * 6;
    }

    //密碼中間穿插數字或符號字元
    public int checkNumOrCharInStr() {
        int j = this.num + this.charlen - 1;
        if (j < 0) {
            j = 0;
        }
        if (this.num + this.charlen == this.length) {
            j = this.length - 2;
        }
        return j * 2;
    }

    /**
     * 最低要求标准
     * 该方法需要在以上加分方法使用后才可以使用
     *
     * @return
     */
    public int LowerQuest() {
        int j = 0;
        if (this.length >= 8) {
            j++;
        }
        if (this.upperAlp > 0) {
            j++;
        }
        if (this.lowerAlp > 0) {
            j++;
        }
        if (this.num > 0) {
            j++;
        }
        if (this.charlen > 0) {
            j++;
        }
        if (j >= 4) {

        } else {
            j = 0;
        }
        return j * 2;
    }

    /**
     * =================分割线===扣分项目=====================
     **/
    //只包含英文字母
    public int OnlyHasAlp() {
        if (this.length == (this.upperAlp + this.lowerAlp)) {
            return -this.length;
        }
        return 0;
    }

    //只包含数字
    public int OnlyHasNum() {
        if (this.length == this.num) {
            return -this.length;
        }
        return 0;
    }

    //重复字元扣分
    public int repeatDex() {
        char[] c = this.psw.toLowerCase().toCharArray();
        HashMap<Character, Integer> hashMap =
                new HashMap<Character, Integer>();
        for (int i = 0; i < c.length; i++) {
            if (hashMap.containsKey(c[i])) {
                hashMap.put(c[i], hashMap.get(c[i]) + 1);
            } else {
                hashMap.put(c[i], 1);
            }
        }
        int sum = 0;
        Iterator<Map.Entry<Character, Integer>> iterator =
                hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            int j = iterator.next().getValue();
            if (j > 0) {
                sum = sum + j * (j - 1);
            }
        }
        return -sum;
    }

    //连续英文大写字元
    public int seriseUpperAlp() {
        int j = 0;
        char[] c = this.psw.toCharArray();
        for (int i = 0; i < c.length - 1; i++) {
            if (Pattern.compile("[A-Z]").matcher(c[i] + "").find()) {
                if (Pattern.compile("[A-Z]").matcher(c[i + 1] + "").find()) {
                    j++;
                }
            }
        }
        return -2 * j;
    }

    //连续英文小写字元
    public int seriseLowerAlp() {
        String reg = "[a-z]";
        int j = 0;
        char[] c = this.psw.toCharArray();
        for (int i = 0; i < c.length - 1; i++) {
            if (Pattern.compile(reg).matcher(c[i] + "").find()
                    && c[i] + 1 == c[i + 1]) {
                j++;
            }
        }
        return -2 * j;
    }

    //连续数字字元
    public int seriseNum() {
        String reg = "[0-9]";
        Pattern pattern = Pattern.compile(reg);
        char[] c = this.psw.toCharArray();
        int j = 0;
        for (int i = 0; i < c.length - 1; i++) {
            if (pattern.matcher(c[i] + "").matches()
                    && pattern.matcher(c[i + 1] + "").matches()) {
                j++;
            }
        }
        return -2 * j;
    }

    //连续字母abc def之类超过3个扣分  不区分大小写字母
    public int seriesAlp2Three() {
        int j = 0;
        char[] c = this.psw.toLowerCase(Locale.CHINA).toCharArray();
        for (int i = 0; i < c.length - 2; i++) {
            if (Pattern.compile("[a-z]").matcher(c[i] + "").find()) {
                if ((c[i + 1] == c[i] + 1) && (c[i + 2] == c[i] + 2)) {
                    j++;
                }
            }
        }
        return -3 * j;
    }

    //连续数字123 234之类超过3个扣分
    public int seriesNum2Three() {
        int j = 0;
        char[] c = this.psw.toLowerCase(Locale.CHINA).toCharArray();
        for (int i = 0; i < c.length - 2; i++) {
            if (Pattern.compile("[0-9]").matcher(c[i] + "").find()) {
                if ((c[i + 1] == c[i] + 1) && (c[i + 2] == c[i] + 2)) {
                    j++;
                }
            }
        }
        return -3 * j;
    }

    public int addScore() {
        return (CheckPswLength() + CheckPswUpper() + CheckPwsLower() +
                checkNum() + checkChar() + checkNumOrCharInStr() + LowerQuest());

    }

    public int deductionScore() {
        return (OnlyHasAlp() + OnlyHasNum() + repeatDex() - +seriseUpperAlp() +
                seriseLowerAlp() + seriseNum() + seriesAlp2Three() + seriesNum2Three());
    }


    /*
    *
    * 获取密码强度
    *
    * */
    public int getPasswordStrength(String password) {
        this.psw = password.replaceAll("\\s", "");
        this.length = psw.length();
        if ((addScore() - deductionScore() - 2) <= 0) {
            return 0;
        } else if (((addScore() - deductionScore() - 2) / 3) > 90) {
            return 100;

        } else {
            return ((addScore() - deductionScore() - 2) / 3);
        }

    }

}