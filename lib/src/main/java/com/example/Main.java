package com.example;

import java.util.Scanner;

/**
 * Created by Larry-sea on 9/28/2016.
 */

public class Main {

    public static void main(String [] args)
    {

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
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        System.out.println(max);

    }
}
