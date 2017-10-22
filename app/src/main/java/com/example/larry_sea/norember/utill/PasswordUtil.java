package com.example.larry_sea.norember.utill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Larry-sea on 2016/9/4.
 */
public class PasswordUtil {

    public String generatePassword(int length, boolean upper, boolean lower, boolean digits, boolean special, int minDigits, boolean ambig, boolean pronuncable) {
        int minlower = 0;
        int minupper = 0;
        int minspecial = 0;
        List<Character> list = new <Character>ArrayList();
        if (upper) {
            minupper = 1;
        }
        if (lower) {
            minlower = 1;
        }
        if (special) {
            minspecial = 1;
        }
        if (lower && minlower > 0) {
            for (int i = 0; i < minlower; i++) {
                list.add('L');
            }
        }
        if (upper && minupper > 0) {
            for (int i = 0; i < minupper; i++) {
                list.add('U');
            }
        }
        if (digits && minDigits > 0) {
            for (int i = 0; i < minDigits; i++) {
                list.add('D');
            }
        }
        if (special && minspecial > 0) {
            for (int i = 0; i < minspecial; i++) {
                list.add('S');
            }
        }
        while (list.size() < length) {
            list.add('A');

        }
        charComparator charComparator = new charComparator();
        Collections.sort(list, charComparator);
        String chars = "";
        String lowchars = "abcdefghjkmnpqrstuvwxyz";
        if (!ambig) {
            lowchars += "ilo";
        }
        if (lower) {
            chars += lowchars;
        }
        String upperchars = "ABCDEFGHJKMNPQRSTUVWXYZ";
        if (!ambig) {

            upperchars += "ILO";
        }
        if (upper) {
            chars += upperchars;
        }
        String digitchars = "23456789";
        if (!ambig) {
            digitchars += "10";
        }
        if (digits) {
            chars += digitchars;
        }
        String specialchars = "!@#$%^&*";
        if (special) {
            chars += specialchars;
        }
        String pass = "";
        for (int i = 0; i < length; i++) {
            String usechars = null;
            switch (list.get(i)) {
                case 'L':
                    usechars = lowchars;
                    break;
                case 'U':
                    usechars = upperchars;
                    break;
                case 'D':
                    usechars = digitchars;
                    break;
                case 'S':
                    usechars = specialchars;
                    break;
                case 'A':
                    usechars = chars;
                    break;
            }
            int position = getRandom(0, usechars.length() - 1);
            pass += usechars.charAt(position);
        }
        return pass;
    }

    public int getRandom(int startNumber, int endNumber) {
        return (int) (Math.random() * endNumber) + startNumber;
    }


    class charComparator implements Comparator<Character> {

        @Override
        public int compare(Character character, Character t1) {
            int flag = character.compareTo(t1);
            return flag;
        }
    }


}
