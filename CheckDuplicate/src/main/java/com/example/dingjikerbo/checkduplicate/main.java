package com.example.dingjikerbo.checkduplicate;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class main {

    private static final int SEGMENT = 3;
    private static final String[] SPLITER = new String[] {"？", "！", "。"};

    public static void main(String[] args) {
        try {
            List<String> text1 = FileUtils.readFile("test1.txt");
            List<String> result1 = split(text1, SPLITER);

            List<String> text2 = FileUtils.readFile("test2.txt");
            List<String> result2 = split(text2, SPLITER);

            HashMap<String, List<String>> map = check(result1, result2);
            for (String key : map.keySet()) {
                System.out.println(">>>>>>>>>: " + key);
                for (String s : map.get(key)) {
                    System.out.println(s);
                }
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

    private static HashMap<String, List<String>> check(List<String> list1, List<String> list2) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : list1) {
            for (String t : list2) {
                if (like(s, t)) {
                    map.computeIfAbsent(s, k -> new LinkedList<String>()).add(t);
                }
            }
        }
        return map;
    }

    private static boolean like(String s, String t) {
        for (int i = 0; i <= s.length() - SEGMENT; i++) {
            String ss = s.substring(i, i + SEGMENT);
            if (t.contains(ss)) {
                return true;
            }
        }
        return false;
    }
}
