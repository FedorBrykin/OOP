package ru.nsu.brykin;

/**
 * heap sort, подсчёт времени выполнения.
 */
public class Heapsort {

    /**
     * замер скорости работы сортировки.
     */

    public static void main(String[] args) {
        int arrayLen = 10000;
        int arrayElementValue = 10000;
        int[] array = new int[arrayLen];
        for (int i = 0; i < arrayLen; i++) {
            array[i] = arrayElementValue;
            arrayElementValue--;
        }
        // Замер времени сортировки
        long startTime = System.nanoTime();
        sort(array);
        long endTime = System.nanoTime();
        long workTime = endTime - startTime;
        System.out.println(workTime);

    }

    /**
     * heap sort.
     */

    public static void sort(int[] arr) {
        int n = arr.length;

        //максимальная куча
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // убираем элементы
        for (int i = n - 1; i > 0; i--) {
            int k = arr[i];
            arr[i] = arr[0];
            arr[0] = k;

            heapify(arr, i, 0);
        }
    }

    /**
     * поиск наибольшего локально
     */

    private static void heapify(int[] arr, int n, int i) {
        int largestIndex = i; // max элемент
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // если левый больше
        if (left < n && arr[left] > arr[largestIndex]) {
            largestIndex = left;
        }

        // если правый больше
        if (right < n && arr[right] > arr[largestIndex]) {
            largestIndex = right;
        }

        // если самый большой != корень
        if (largestIndex != i) {
            int swap = arr[i];
            arr[i] = arr[largestIndex];
            arr[largestIndex] = swap;

            heapify(arr, n, largestIndex);
        }
    }
}