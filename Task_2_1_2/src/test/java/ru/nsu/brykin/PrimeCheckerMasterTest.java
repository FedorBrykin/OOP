package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrimeCheckerMasterTest {
    @Test
    void testHasCompositeNumbersInitiallyFalse() {
        PrimeCheckerMaster master = new PrimeCheckerMaster();
        assertFalse(master.hasCompositeNumbers());
    }

    @Test
    void testGetResultsReturnsUnmodifiableMap() {
        PrimeCheckerMaster master = new PrimeCheckerMaster();
        Map<List<Integer>, Boolean> results = master.getResults();
        assertThrows(UnsupportedOperationException.class,
                () -> results.put(Collections.singletonList(1), true));
    }
}