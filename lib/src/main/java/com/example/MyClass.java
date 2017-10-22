package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyClass {

    public static void main(String args[]) {
        String a = "fasf/fasfs/afsa/a.jpg";
        //  System.out.print(getFilePath(a));

        // System.out.print,ln(a);
        random(8);
        //System.out.print(random(8)+"    "+random(8)+"    "+random(10)+"   "+random(100));
        long b=213123;
        System.out.println(a+b);

    }


    static public <T> void getNumber(T ad) {
        System.out.println(ad.getClass());
    }

    static public String judgeNull(String a) {

        return a = "fasdfas";
    }


    static public void testMaxSon() {

        int arrayLenth = 0;
        Scanner scanner = new Scanner(System.in);
        arrayLenth = scanner.nextInt();
        int parmArray[] = new int[arrayLenth];
        int max = 0;
        int tempMax = 0;
        for (int position = 0; position < arrayLenth; position++) {
            parmArray[position] = scanner.nextInt();
            if (parmArray[position] > 0) {
                tempMax += parmArray[position];
            } else if (parmArray[position] < 0) {
                max = tempMax;
                tempMax = 0;
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        System.out.println(max);
    }


    static public String getFilePath(String filePath) {
        if (filePath != null) {
            return filePath.substring(0, filePath.lastIndexOf("/") + 1);
        } else return null;


    }


    static public String random(int length) {
        String strRand = "";
        for (int i = 0; i < length; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        return strRand;
    }



    public void initPassword()
    {


    }

}



