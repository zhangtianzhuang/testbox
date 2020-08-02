package com.bjtu.testbox.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public static String currentTimeString() {
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

    public static String randomNumber(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    public static String randomTaskNum() {
        return "T" + currentTimeString() + randomNumber(4) + randomString(4);
    }

    public static void main(String[] args) {
        System.out.println(randomTaskNum());
    }


    public static final Map<Integer,String> mapStatusCode;
    public static final Map<Integer,String> mapStatusShow;

    static {
        mapStatusCode = new HashMap<Integer, String>();
        mapStatusCode.put(1,"checkpending1");    // 待一级审核
        mapStatusCode.put(2,"checkpending2");    // 待二级审核
        mapStatusCode.put(3,"standingby");       // 待领用
        mapStatusCode.put(4,"notreturn");        // 待归还
        mapStatusCode.put(5,"completed");        // 已完成
        mapStatusCode.put(6,"rejected");         // 被拒绝

        mapStatusShow = new HashMap<>();
        mapStatusShow.put(1,"待一级审核");
        mapStatusShow.put(2,"待二级审核");
        mapStatusShow.put(3,"待领用");
        mapStatusShow.put(4,"待归还");
        mapStatusShow.put(5,"已完成");
        mapStatusShow.put(6,"被拒绝");
    }
}
