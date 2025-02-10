package ru.nsu.brykin;

import java.util.Random;

/**
 * список
 */
public class PrimeGen {
    /**
     * создание списка
     */
    public static int[] generateLargePrimeArray(int size) {
        int[] primes = new int[size];
        Random random = new Random();
        int count = 0;

        while (count < size) {
            int number = 1000000 + random.nextInt(9000000);
            if (isPrime(number)) {
                primes[count] = number;
                count++;
            }
        }

        return primes;
    }

    /**
     * делимость
     */
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