package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    public void testPutAndGet() {
        hashTable.put("key1", 1);
        assertEquals(1, hashTable.get("key1"));

        hashTable.put("key2", 2);
        assertEquals(2, hashTable.get("key2"));
    }

    @Test
    public void testUpdate() {
        hashTable.put("key1", 1);
        hashTable.update("key1", 2);
        assertEquals(2, hashTable.get("key1"));
    }

    @Test
    public void testRemove() {
        hashTable.put("key1", 1);
        hashTable.remove("key1");
        assertNull(hashTable.get("key1"));
    }

    @Test
    public void testContainsKey() {
        hashTable.put("key1", 1);
        assertTrue(hashTable.containsKey("key1"));
        assertFalse(hashTable.containsKey("key2"));
    }

    @Test
    public void testIterator() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        Iterator<Map.Entry<String, Integer>> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());
        Map.Entry<String, Integer> entry1 = iterator.next();
        assertEquals("key1", entry1.getKey());
        assertEquals(1, entry1.getValue());
        assertTrue(iterator.hasNext());
        Map.Entry<String, Integer> entry2 = iterator.next();
        assertEquals("key2", entry2.getKey());
        assertEquals(2, entry2.getValue());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorConcurrentModification() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        Iterator<Map.Entry<String, Integer>> iterator = hashTable.iterator();
        hashTable.put("key3", 3);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }

        assertEquals(20, hashTable.size);
    }

    @Test
    public void testRemoveNonExistentKey() {
        hashTable.put("key1", 1);
        hashTable.remove("key2");
        assertEquals(1, hashTable.size);
    }

    @Test
    public void testToStringEmpty() {
        assertEquals("{}", hashTable.toString());
    }

    @Test
    public void testToStringSingleEntry() {
        hashTable.put("key1", 1);
        assertEquals("{key1=1}", hashTable.toString());
    }

    @Test
    public void testToStringMultipleEntries() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);
        assertEquals("{key1=1, key2=2}", hashTable.toString());
    }

    @Test
    public void testToStringWithDuplicateKeys() {
        hashTable.put("key1", 1);
        hashTable.update("key1", 2);
        hashTable.put("key2", 3);
        assertEquals("{key1=2, key2=3}", hashTable.toString());
    }

    @Test
    public void testToStringLargeNumberOfEntries() {
        for (int i = 0; i < 10; i++) {
            hashTable.put("key" + i, i);
        }
        StringBuilder expected = new StringBuilder("{");
        for (int i = 0; i < 10; i++) {
            expected.append("key").append(i).append("=").append(i).append(", ");
        }
        expected.setLength(expected.length() - 2);
        expected.append("}");

        assertEquals(expected.toString(), hashTable.toString());
    }

}
