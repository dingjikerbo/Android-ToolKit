import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        List<String> lines = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(new File("input.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = trimNumber(line);
                lines.add(line);
            }
        } finally {
            reader.close();
        }

        write(lines);
    }

    private static void write(List<String> lines) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(new File("input.txt")));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
