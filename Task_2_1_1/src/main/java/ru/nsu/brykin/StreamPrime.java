package ru.nsu.brykin;

import java.util.Arrays;

public class StreamPrime {
    public static boolean containsNonPrime(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(n -> !isPrime(n));
    }

    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}