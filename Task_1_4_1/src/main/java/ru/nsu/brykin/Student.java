package ru.nsu.brykin;

/**
 * штудент.
 */
public class Student {
    private String name;
    private Transcript transcript;

    /**
     * студент.
     */
    public Student(String name, int totalGradesCount) {
        this.name = name;
        this.transcript = new Transcript(totalGradesCount);
    }

    /**
     * имя.
     */
    public String getName() {
        return name;
    }

    /**
     * оценка+.
     */
    public void addGrade(Grade grade) {
        transcript.addGrade(grade);
    }

    /**
     * отлично.
     */
    public void setExcellent(boolean excellent) {
        transcript.excellentGrade(excellent);
    }

    /**
     * красный.
     */
    public boolean redDiplom() {
        return transcript.redDiplom();
    }

    /**
     * стипендия.
     */
    public boolean canGetIncreasedScholarship() {
        return transcript.canGetIncreasedScholarship();
    }
}