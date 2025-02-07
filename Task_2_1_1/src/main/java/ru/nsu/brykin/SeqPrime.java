package ru.nsu.brykin;

public class SeqPrime implements PrimeChecker {
    @Override
    public boolean containsNonPrime(int[] numbers, int numTreads) {
        for (int number : numbers) {
            if (!PrimeUtils.isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}