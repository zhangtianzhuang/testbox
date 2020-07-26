package com.bjtu.testbox.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestboxTool {

    /**
     * generate a random task number that decided by current system time and a random strings.
     *
     * @return
     */
    public static String currentTimeString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    public static String currentTimeString(){
        return currentTimeString("yyyyMMdd");
    }

    public static String randomString(int n) {
        // A ~ Z  65 ~ 90
        char[] strings = new char[n];
        for (int i = 0; i < n; i++) {
            strings[i] = (char) (65 + new Random().nextInt(26));
        }
        return String.valueOf(strings);
    }

    public static String randomNumber(int n){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    public static String randomTaskNum(){
        return currentTimeString()+randomNumber(4)+randomString(4);
    }

    public static void main(String[] args) {
    }
}
