package ru.nsu.brykin;

public class Student {
    private String name;
    private Transcript transcript;

    public Student(String name, int totalGradesCount) {
        this.name = name;
        this.transcript = new Transcript(totalGradesCount);
    }

    public String getName() {
        return name;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void addGrade(Grade grade) {
        transcript.addGrade(grade);
    }

    public void setExcellent(boolean excellent) {
        transcript.excellentGrade(excellent);
    }

    public boolean redDiplom() {
        return transcript.redDiplom();
    }

    public boolean canGetIncreasedScholarship() {
        return transcript.canGetIncreasedScholarship();
    }
}