package ru.nsu.brykin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstringFinderTest {

    @Test
    public void testFindSubstringPresent() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream("абракадабра".getBytes())) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "бра");
            assertEquals(List.of(1, 8), indices);
        }
    }

    @Test
    public void testFindSubstringAbsent() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream("абракадабра".getBytes())) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "кот");
            assertEquals(List.of(), indices);
        }
    }

    @Test
    public void testFindEmptyFile() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream("".getBytes())) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "бра");
            assertEquals(List.of(), indices);
        }
    }

    @Test public void testFindMultipleOccurrences() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream("бракабракабра".getBytes())) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "бра");
            assertEquals(List.of(0, 5, 10), indices);
        }
    }

    @Test
    public void testLargeFile() throws IOException {
        String filename = "test_large.txt";
        StringBuilder largeContent = new StringBuilder();
        String content = "абракадабра\n";
        long targetSizeInBytes = 9L * 1024 * 1024 * 1024; // 9 ГБ
        long currentSizeInBytes = 0;
        try (FileWriter writer = new FileWriter(filename)) {
            while (currentSizeInBytes < targetSizeInBytes) {
                writer.write(content);
                currentSizeInBytes += content.length();
            }
        }
        try (InputStream inputStream = new FileInputStream(new File(filename))) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "ыыы");
            assertEquals(List.of(), indices);
        }
        new File(filename).delete();
    }

    @Test
    public void testOverlappingOccurrences() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream("aaaaaaaaaaaaaaaaa".getBytes())) {
            SubstringFinder finder = new SubstringFinder();
            List<Integer> indices = finder.find(inputStream, "aaaaa");
            assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), indices);
        }
    }
}
