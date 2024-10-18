package ru.nsu.brykin;

class Student {
    private String name;
    private Transcript transcript;

    public Student(String name) {
        this.name = name;
        this.transcript = new Transcript();
    }

    public void addGrade(Grade grade) {
        transcript.addGrade(grade);
    }

    public void excellentGrade(boolean excellent) {
        transcript.excellentGrade(excellent);
    }

    public double getAverage() {
        return transcript.calcAverage();
    }

    public boolean toBudget() {
        return transcript.toBudget();
    }

    public boolean redDiplom() {
        return transcript.redDiplom();
    }

    public boolean canGetIncreasedScholarship() {
        return transcript.canGetIncreasedScholarship();
    }
}

