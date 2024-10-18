package ru.nsu.brykin;

import java.util.ArrayList;
import java.util.List;

class Transcript {
    private List<Grade> grades;
    private boolean Excellent;

    public Transcript() {
        this.grades = new ArrayList<>();
        this.Excellent = false;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void excellentGrade(boolean excellent) {
        this.Excellent = excellent;
    }

    public double calcAverage() {
        if (grades.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (Grade grade : grades) {
            total += grade.getScore();
        }
        return total / grades.size();
    }

    public boolean toBudget() {
        int satisfactoryCount = 0;
        int lastTwoSessionsCount = Math.min(2, grades.size());
        for (int i = grades.size() - lastTwoSessionsCount; i < grades.size(); i++) {
            if (grades.get(i).isSatisfactory() && grades.get(i).isFinal()) {
                satisfactoryCount++;
            }
        }
        return satisfactoryCount == 0;
    }

    public boolean redDiplom() {
        if (!Excellent) {
            return false;
        }
        int excellentCount = 0;
        int satisfactoryCount = 0;
        for (Grade grade : grades) {
            if (grade.isExcellent()) excellentCount++;
            if (grade.isSatisfactory() && grade.isFinal()) satisfactoryCount++;
        }
        // 75% оценок должны быть "отлично"
        return (excellentCount * 100.0 / grades.size() >= 75) && (satisfactoryCount == 0);
    }

    public boolean canGetIncreasedScholarship() {
        return calcAverage() >= 4.5;
    }
}