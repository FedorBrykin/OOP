package ru.nsu.brykin;

/**
 * последовательный.
 */
public class SeqPrime implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers) {
        for (int number : numbers) {
            if (!PrimeUtils.isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}