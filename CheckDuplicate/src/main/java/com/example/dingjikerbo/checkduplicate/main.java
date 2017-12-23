package com.example.dingjikerbo.checkduplicate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class main {

    private static final int SEGMENT = 10;
    private static final String[] SPLITER = new String[] {"？", "！", "。"};

    public static void main(String[] args) {
        try {
            List<String> text1 = FileUtils.readFile("my.txt");
            List<String> result1 = split(text1, SPLITER);

            List<String> text2 = FileUtils.readFile("test2.txt");
            List<String> result2 = split(text2, SPLITER);

            HashMap<String, String[]> map = check(result1, result2);
            for (String key : map.keySet()) {
                System.out.println("我的句子: " + key);
                String[] pair = map.get(key);
                System.out.println("重复部分: " + pair[1]);
                System.out.println("对比句子: " + pair[0]);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> split(List<String> list, String[] spliter) {
        List<String> result = new LinkedList<>();
        for (String s : list) {
            result.addAll(split(s, spliter));
        }
        return result;
    }

    private static List<String> split(String s, String[] spliter) {
        List<String> result = new LinkedList<>();
        while (s.length() > 0) {
            int min = Integer.MAX_VALUE;

            for (String t : spliter) {
                int index = s.indexOf(t);
                if (index == -1) {
                    continue;
                }
                if (index < min) {
                    min = index;
                }
            }

            if (min < s.length()) {
                result.add(s.substring(0, min + 1));
                s = s.substring(min + 1);
            } else {
                result.add(s);
                s = "";
            }
        }
        return result;
    }

    private static HashMap<String, String[]> check(List<String> list1, List<String> list2) {
        HashMap<String, String[]> map = new HashMap<>();
        for (String s : list1) {
            String max = "", target = "";
            for (String t : list2) {
                String p = like(s, t);
                if (p.length() > max.length()) {
                    max = p;
                    target = t;
                }
            }
            if (max.length() > 0) {
                map.put(s, new String[] {target, max});
            }
        }
        return map;
    }

    private static String like(String s, String t) {
        String max = "";
        for (int i = 0; i <= s.length(); i++) {
            for (int j = SEGMENT; i + j <= s.length(); j++) {
                String ss = s.substring(i, i + j);
                if (t.contains(ss)) {
                    if (ss.length() > max.length()) {
                        max = ss;
                    }
                }
            }
        }
        return max;
    }
}
