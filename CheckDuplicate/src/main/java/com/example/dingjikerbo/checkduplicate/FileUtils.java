package com.example.dingjikerbo.checkduplicate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dingjikerbo on 2017/12/21.
 */

public class FileUtils {

    public static List<String> readFile(String name) throws IOException {
        FileReader fr = new FileReader(name);
        BufferedReader bf = new BufferedReader(fr);
        List<String> result = new LinkedList<>();
        String line = "";
        while ((line = bf.readLine()) != null) {
            line = line.trim();
            line = line.replaceAll("“", "");
            line = line.replaceAll("”", "");
            if (!line.isEmpty()) {
                result.add(line);
            }
        }
        return result;
    }
}
