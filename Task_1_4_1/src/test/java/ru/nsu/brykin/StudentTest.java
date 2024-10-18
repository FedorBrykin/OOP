package ru.nsu.brykin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void testAddGrade() {
        Student student = new Student("Fedor B");
        student.addGrade(new Grade("Math", "exam", 5));
        assertEquals(5.0, student.getAverage(), 0.01);
    }

    @Test
    public void testSetThesisGradeExcellent() {
        Student student = new Student("Fedor B");
        student.addGrade(new Grade("Math", "exam", 5));
        student.excellentGrade(true);
        assertTrue(student.redDiplom());
    }

    @Test
    public void testCanTransferToBudget() {
        Student student = new Student("Vasya P");
        student.addGrade(new Grade("Math", "exam", 5));
        student.addGrade(new Grade("OOP", "exam", 4));
        assertTrue(student.toBudget());
        student.addGrade(new Grade("Physics", "exam", 3));
        assertFalse(student.toBudget());
    }

    @Test
    public void testCanGetRedDiplom() {
        Student student = new Student("Vasya P");
        student.excellentGrade(true);
        student.addGrade(new Grade("Math", "exam", 5));
        student.addGrade(new Grade("Physics", "exam", 5));
        student.addGrade(new Grade("OOP", "exam", 5));
        assertTrue(student.redDiplom());
        student.addGrade(new Grade("OS", "exam", 3));
        assertFalse(student.redDiplom());
    }

    @Test
    public void testCanGetIncreasedScholarship() {
        Student student = new Student("Vasya P");
        student.addGrade(new Grade("Math", "exam", 5));
        student.addGrade(new Grade("Physics", "exam", 4));
        assertTrue(student.canGetIncreasedScholarship());
        student.addGrade(new Grade("OOP", "exam", 3)); // Средний балл ниже 4.5
        assertFalse(student.canGetIncreasedScholarship());
    }
}
