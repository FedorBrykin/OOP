package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {
    public static List<Integer> find(String filename, String substring) throws IOException {
        List<Integer> ind = new ArrayList<>();
        StringBuilder contentBuffer = new StringBuilder();
        int substringLength = substring.length();
        Reader fileReader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        int curInd = 0; // Текущий индекс в файле
        while ((line = reader.readLine()) != null) {
            contentBuffer.append(line);
            int lineLength = line.length();
            int index = contentBuffer.indexOf(substring);
            while (index != -1) {
                ind.add(curInd + index);
                index = contentBuffer.indexOf(substring, index + 1);
            }
            if (contentBuffer.length() > substringLength) {
                contentBuffer.delete(0, lineLength - substringLength);
            }
        }
        return ind;
    }

    public static void main(String[] args) throws IOException {
        String filename = "./src/main/resources/input.txt";
        String substring = "бра";
        List<Integer> ind = find(filename, substring);
        System.out.println(ind);
    }
}
