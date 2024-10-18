package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TranscriptTest {
    @Test
    public void testCalcAverage() {
        Transcript transcript = new Transcript();
        transcript.addGrade(new Grade("Math", "exam", 5));
        transcript.addGrade(new Grade("Physics", "exam", 4));
        transcript.addGrade(new Grade("OOP", "exam", 3));
        assertEquals(4.0, transcript.calcAverage(), 0.01);
    }

    @Test
    public void testTransferToBudget() {
        Transcript transcript = new Transcript();
        transcript.addGrade(new Grade("Math", "exam", 5));
        transcript.addGrade(new Grade("Physics", "exam", 4));
        assertTrue(transcript.toBudget());
        transcript.addGrade(new Grade("OOP", "exam", 3));
        assertFalse(transcript.toBudget());
    }

    @Test
    public void testCanGetRedDiplom() {
        Transcript transcript = new Transcript();
        transcript.excellentGrade(true);
        transcript.addGrade(new Grade("Math", "exam", 5));
        transcript.addGrade(new Grade("Physics", "exam", 5));
        transcript.addGrade(new Grade("OOP", "exam", 5));
        assertTrue(transcript.redDiplom());
        transcript.addGrade(new Grade("OS", "exam", 3));
        assertFalse(transcript.redDiplom());
    }

    @Test
    public void testCanGetIncreasedScholarship() {
        Transcript transcript = new Transcript();
        transcript.addGrade(new Grade("Math", "exam", 5));
        transcript.addGrade(new Grade("Physics", "exam", 4));
        assertTrue(transcript.canGetIncreasedScholarship());
        transcript.addGrade(new Grade("OS", "exam", 3));
        assertFalse(transcript.canGetIncreasedScholarship());
    }
}
