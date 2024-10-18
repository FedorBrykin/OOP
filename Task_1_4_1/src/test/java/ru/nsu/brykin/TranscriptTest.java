package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranscriptTest {
    private Transcript transcript;

    @BeforeEach
    void setUp() {
        transcript = new Transcript(5);
    }

    @Test
    void testCalcAverage() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 4));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        assertEquals(4.5, transcript.calcAverage(), 0.01);
    }

    @Test
    void testCalcAverageWithNoGrades() {
        assertEquals(0, transcript.calcAverage(), 0.01);
    }

    @Test
    void testToBudget() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 3));
        assertFalse(transcript.toBudget());
    }

    @Test
    void testToBudgetWithAllExcellent() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        assertTrue(transcript.toBudget());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 4));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        assertTrue(transcript.canGetIncreasedScholarship());
    }

    @Test
    void testCannotGetIncreasedScholarship() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 3));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 4));
        assertFalse(transcript.canGetIncreasedScholarship());
    }

    @Test
    void testRedDiplomWithOneThree() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 3));
        transcript.addGrade(new Grade("OS", Grade.GradeType.EXAM, 5));
        assertFalse(transcript.redDiplom());
    }

    @Test
    void testRedDiplomWithAverageLessThan4_75() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 4));
        transcript.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 4));
        transcript.addGrade(new Grade("OS", Grade.GradeType.EXAM, 4));
        assertFalse(transcript.redDiplom());
    }

    @Test
    void testRedDiplomWithFinalProjectGradeFour() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OS", Grade.GradeType.EXAM, 4));
        assertFalse(transcript.redDiplom());
    }

    @Test
    void testRedDiplomWithAllExcellentExceptOne() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OS", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Models", Grade.GradeType.EXAM, 4));
        assertTrue(transcript.redDiplom());
    }

    @Test
    void testRedDiplomWithMultipleGoodGrades() {
        transcript.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 4));
        transcript.addGrade(new Grade("OPP", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("OS", Grade.GradeType.EXAM, 5));
        transcript.addGrade(new Grade("Models", Grade.GradeType.EXAM, 5));
        assertTrue(transcript.redDiplom());
    }
}
