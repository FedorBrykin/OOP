package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SubstringFinderTest {
    private void createTestFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        }
    }

    @Test
    public void testFindSubstringPresent() throws IOException {
        String filename = "test1.txt";
        createTestFile(filename, "абракадабра");
        List<Integer> indices = SubstringFinder.find(filename, "бра");
        assertEquals(List.of(1, 8), indices);
        new File(filename).delete(); // Удаляем тестовый файл
    }

    @Test
    public void testFindSubstringAbsent() throws IOException {
        String filename = "test2.txt";
        createTestFile(filename, "абракадабра");
        List<Integer> indices = SubstringFinder.find(filename, "кот");
        assertEquals(List.of(), indices);
        new File(filename).delete();
    }

    @Test
    public void testFindEmptyFile() throws IOException {
        String filename = "test3.txt";
        createTestFile(filename, "");
        List<Integer> indices = SubstringFinder.find(filename, "бра");
        assertEquals(List.of(), indices);
        new File(filename).delete();
    }

    @Test
    public void testFindMultipleOccurrences() throws IOException {
        String filename = "test4.txt";
        createTestFile(filename, "бракабракабра");
        List<Integer> indices = SubstringFinder.find(filename, "бра");
        assertEquals(List.of(0, 5, 10), indices);
        new File(filename).delete();
    }

    @Test
    public void testFindNonExistentFile() {
        assertThrows(IOException.class, () -> {
            SubstringFinder.find("non_existent_file.txt", "бра");
        });
    }
}
