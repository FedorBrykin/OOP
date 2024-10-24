package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * подсчёты.
 */
class Transcript {
    private List<Grade> grades;
    private boolean excellent;
    private int totalGradesCount;

    /**
     * подсчёты.
     */
    public Transcript(int totalGradesCount) {
        this.grades = new ArrayList<>();
        this.excellent = false;
        this.totalGradesCount = totalGradesCount;
    }

    /**
     * +оценка.
     */
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    /**
     * 5.
     */
    public void excellentGrade(boolean excellent) {
        this.excellent = excellent;
    }

    /**
     * среднее.
     */
    public double calcAverage() {
        if (grades.isEmpty()) {
            return 0;
        }
        return grades.stream()
                .mapToDouble(Grade::getScore)
                .average()
                .orElse(0);
    }

    /**
     * бюджет.
     */
    public boolean toBudget() {
        long satisfactoryCount = grades.stream()
                .filter(Grade::isSatisfactory)
                .count();
        return satisfactoryCount == 0;
    }

    /**
     * красный.
     */
    public boolean redDiplom() {
        long excellentCount = grades.stream()
                .filter(Grade::isExcellent)
                .count();
        long satisfactoryCount = grades.stream()
                .filter(Grade::isSatisfactory)
                .count();

        Map<String, Grade> lastGrades = grades.stream()
                .collect(Collectors.toMap(Grade::getSubject, grade -> grade,
                        (old, newone) -> newone));
        boolean conditionIsMet = lastGrades.values().stream()
                .allMatch(grade -> grade.getScore() == 5);
        return (excellentCount * 100.0 / totalGradesCount >= 75) && (satisfactoryCount == 0)
                && conditionIsMet;
    }

    /**
     * стипендия.
     */
    public boolean canGetIncreasedScholarship() {
        return calcAverage() >= 4.5;
    }
}
