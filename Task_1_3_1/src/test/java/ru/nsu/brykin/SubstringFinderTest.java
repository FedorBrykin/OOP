package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;


public class SubstringFinderTest {

    @Test
    public void testFindSubstringPresent() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test1.txt");
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "бра");
        assertEquals(List.of(1, 8), indices);
    }

    @Test
    public void testFindSubstringAbsent() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test1.txt");
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "кот");
        assertEquals(List.of(), indices);
    }

    @Test
    public void testFindEmptyFile() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test2.txt");
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "бра");
        assertEquals(List.of(), indices);
    }

    @Test
    public void testFindMultipleOccurrences() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test3.txt");
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "бра");
        assertEquals(List.of(0, 5, 10), indices);
    }

    @Test
    public void testLargeFile() throws IOException {
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            largeContent.append("абракадабра ");
        }
        InputStream inputStream = new ByteArrayInputStream(largeContent.toString().getBytes());
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "бра");
    }

    @Test
    public void testOverlappingOccurrences() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/test4.txt");
        SubstringFinder finder = new SubstringFinder();
        List<Integer> indices = finder.find(inputStream, "aaaaa");
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), indices);
    }
}
