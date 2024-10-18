package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class GradeTest {
    @Test
    public void testIsSatisfactory() {
        Grade grade = new Grade("Math", Grade.GradeType.EXAM, 3);
        assertTrue(grade.isSatisfactory());
        assertFalse(grade.isGood());
        assertFalse(grade.isExcellent());
    }

    @Test
    public void testIsGood() {
        Grade grade = new Grade("Math", Grade.GradeType.EXAM, 4);
        assertTrue(grade.isGood());
        assertFalse(grade.isSatisfactory());
        assertFalse(grade.isExcellent());
    }

    @Test
    public void testIsExcellent() {
        Grade grade = new Grade("Math", Grade.GradeType.EXAM, 5);
        assertTrue(grade.isExcellent());
        assertFalse(grade.isSatisfactory());
        assertFalse(grade.isGood());
    }

    @Test
    public void testIsFinal() {
        Grade examGrade = new Grade("Math", Grade.GradeType.EXAM, 5);
        Grade creditGrade = new Grade("Physics", Grade.GradeType.CREDIT, 4);
        assertTrue(examGrade.isFinal());
        assertFalse(creditGrade.isFinal());
    }

    @Test
    public void testCreateGradeWithInvalidScore() {
        try {
            new Grade("Biology", Grade.GradeType.EXAM, 6);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
        }
    }
}
