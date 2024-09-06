package ru.nsu.brykin;

/**
 * heap sort, подсчёт времени выполнения
 */
public class Main {
    public static void main(String[] args){
        int al = 10000;
        int key = 10000;
        int[] a = new int[al];
        for (int i = 0; i < al; i++) {
            a[i] = key;
            key--;
        }
        // Замер времени сортировки
        long startTime = System.nanoTime();
        sort(a);
        long endTime = System.nanoTime();

    }
    /**
     * heap sort
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
        int largest = i; // max элемент
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // если левый больше
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // если правый больше
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // если самый большой != корень
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
}