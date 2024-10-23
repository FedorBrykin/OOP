package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;

class Transcript {
    private List<Grade> grades;
    private boolean excellent;
    private int totalGradesCount; // Общее количество оценок

    public Transcript(int totalGradesCount) {
        this.grades = new ArrayList<>();
        this.excellent = false;
        this.totalGradesCount = totalGradesCount;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void excellentGrade(boolean excellent) {
        this.excellent = excellent;
    }

    public double calcAverage() {
        if (grades.isEmpty()) {
            return 0;
        }
        return grades.stream()
                .mapToDouble(Grade::getScore)
                .average()
                .orElse(0);
    }

    public boolean toBudget() {
        long satisfactoryCount = grades.stream()
                .filter(Grade::isSatisfactory)
                .count();
        return satisfactoryCount == 0;
    }

    public boolean redDiplom() {
        long excellentCount = grades.stream()
                .filter(Grade::isExcellent)
                .count();
        long satisfactoryCount = grades.stream()
                .filter(Grade::isSatisfactory)
                .count();
        return (excellentCount * 100.0 / totalGradesCount >= 75) && (satisfactoryCount == 0);
    }

    public boolean canGetIncreasedScholarship() {
        return calcAverage() >= 4.5;
    }

    public boolean isFinalGrade(Grade grade) {
        // Предположим, что grades — это список оценок
        return grades.get(grades.size() - 1).equals(grade);
    }
}
