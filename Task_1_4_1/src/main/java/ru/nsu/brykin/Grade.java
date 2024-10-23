package ru.nsu.brykin;


class Grade {
    public enum GradeType {
        EXAM,
        CREDIT;
    }
    private String subject;
    private GradeType type;
    private int score;

    public Grade(String subject, GradeType type, int score) {
        this.subject = subject;
        this.type = type;
        setScore(score);
    }

    public void setScore(int score) {
        if (score < 2 || score > 5) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 2 до 5.");
        }
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean isSatisfactory() {
        return score == 3;
    }

    public boolean isGood() {
        return score == 4;
    }

    public boolean isExcellent() {
        return score == 5;
    }

    // Метод для определения, является ли оценка финальной
    public boolean isFinal(boolean isLastGrade) {
        return isLastGrade; // Финальная оценка — это последняя оценка
    }
}