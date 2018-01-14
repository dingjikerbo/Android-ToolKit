package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrimVLC {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(new File("linetrim/input.txt")));
            writer = new BufferedWriter(new FileWriter(new File("linetrim/output.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("VLC")) {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        } finally {
            reader.close();
            writer.close();
        }
    }
}
