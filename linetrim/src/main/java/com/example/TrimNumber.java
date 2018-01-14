package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liwentian on 2017/11/14.
 */

public class TrimNumber {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(new File("linetrim/input.txt")));
            writer = new BufferedWriter(new FileWriter(new File("linetrim/output.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = trimNumber(line);
                writer.write(line);
                writer.newLine();
            }
        } finally {
            reader.close();
            writer.close();
        }
    }

    private static String trimNumber(String s) {
        int i;
        for (i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                break;
            }
        }
        return s.substring(i);
    }
}
