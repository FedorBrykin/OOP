package ru.nsu.brykin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

/**
 * тесты для послед.
 */
class SeqPrimeTest {
    private final PrimeChecker checker = new SeqPrime();

    private static Stream<Arguments> test() {
        return Stream.of(
                Arguments.of(new int[]{4, 6, 8, 9, 10}, true),

                Arguments.of(new int[]{2, 3, 5, 7, 11}, false),

                Arguments.of(new int[]{}, false),

                Arguments.of(new int[]{13}, false),

                Arguments.of(new int[]{15}, true)
        );
    }

    @ParameterizedTest
    @MethodSource("test")
    void testContainsNonPrime(int[] numbers, boolean expectedResult) throws InterruptedException {
        assertEquals(expectedResult, checker.containsNonPrime(numbers));
    }
}