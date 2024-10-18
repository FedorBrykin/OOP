package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Ivan Ivanov", 5);
    }

    @Test
    void testGetName() {
        assertEquals("Ivan Ivanov", student.getName());
    }

    @Test
    void testRedDiplomWithOneThree() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 3));
        student.addGrade(new Grade("OS", Grade.GradeType.EXAM, 5));
        assertFalse(student.redDiplom());
    }

    @Test
    void testRedDiplomWithAverageLessThan4_75() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 4));
        student.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 4));
        student.addGrade(new Grade("OS", Grade.GradeType.EXAM, 4));
        assertFalse(student.redDiplom());
    }

    @Test
    void testRedDiplomWithFinalProjectGradeFour() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("OS", Grade.GradeType.EXAM, 4));
        assertFalse(student.redDiplom());
    }

    @Test
    void testRedDiplomWithAllExcellentExceptOne() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("OOP", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("OS", Grade.GradeType.EXAM, 5));
        student.addGrade(new Grade("Models", Grade.GradeType.EXAM, 4));
        assertTrue(student.redDiplom());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 4));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 5));
        assertTrue(student.canGetIncreasedScholarship());
    }

    @Test
    void testCannotGetIncreasedScholarship() {
        student.addGrade(new Grade("Math", Grade.GradeType.EXAM, 3));
        student.addGrade(new Grade("Physics", Grade.GradeType.EXAM, 4));
        assertFalse(student.canGetIncreasedScholarship());
    }
}