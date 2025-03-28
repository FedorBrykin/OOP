package ru.nsu.brykin;

import java.util.AbstractMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Hash-Table.
 */
public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int CAPACITY = 4;
    private static final float RESIZE_F = 0.8f;

    private Entry<K, V>[] table;
    int size;
    private int threshold;
    private int modCount;

    /**
     * Hash-Table.
     */
    public HashTable() {
        this.table = new Entry[CAPACITY];
        this.threshold = (int) (CAPACITY * RESIZE_F);
        this.size = 0;
        this.modCount = 0;
    }

    /**
     * entry.
     */
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * добавление.
     */
    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        addEntry(key, value, index);
    }

    /**
     * удаление.
     */
    public void remove(K key) {
        int index = getIndex(key);
        Entry<K, V> previous = null;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                modCount++;
                return;
            }
            previous = current;
            current = current.next;
        }
    }

    /**
     * поиск значения по ключу.
     */
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * обновление значения по ключу.
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * проверка наличия значения по ключу.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * iterator.
     */
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int currentIndex;
            private Entry<K, V> currentEntry;
            private final int expectedModCount = modCount;

            /**
             * наличие следующего.
             */
            @Override
            public boolean hasNext() {
                while (currentEntry == null && currentIndex < table.length) {
                    currentEntry = table[currentIndex++];
                }
                return currentEntry != null;
            }

            /**
             * next.
             */
            @Override
            public Map.Entry<K, V> next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Entry<K, V> entry = currentEntry;
                currentEntry = currentEntry.next;
                return new AbstractMap.SimpleEntry<>(entry.key, entry.value);
            }
        };
    }

    /**
     * +запись.
     */
    private void addEntry(K key, V value, int index) {
        if (size >= threshold) {
            resize();
            index = getIndex(key);
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
        modCount++;
    }

    /**
     * индекс.
     */
    private int getIndex(K key) {
        return key.hashCode() % table.length;
    }

    /**
     * resize.
     */
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        threshold = (int) (table.length * RESIZE_F);
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * toString.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        Iterator<Map.Entry<K, V>> iter = iterator();
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            str.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        if (str.length() > 1) {
            str.setLength(str.length() - 2);
        }
        str.append("}");
        return str.toString();
    }

    /**
     * пример теста.
     */
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one"));
    }
}
