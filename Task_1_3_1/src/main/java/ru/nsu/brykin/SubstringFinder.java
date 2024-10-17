package ru.nsu.brykin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * поиск подстроки.
 */
public class SubstringFinder {
    /**
     * поиск.
     */
    public List<Integer> find(InputStream inputStream, String substring) throws IOException {
        List<Integer> indices = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            int index = line.indexOf(substring);
            while (index >= 0) {
                indices.add(lineNumber * 1000 + index);
                index = line.indexOf(substring, index + 1);
            }
            lineNumber++;
        }
        return indices;
    }
}

