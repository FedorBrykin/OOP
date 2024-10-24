package ru.nsu.brykin;


/**
 * оценки.
 */
class Grade {
    public enum GradeType {
        EXAM,
        ORDINARY;
    }

    private String subject;
    private GradeType type;
    private int score;

    /**
     * оценки.
     */
    public Grade(String subject, GradeType type, int score) {
        this.subject = subject;
        this.type = type;
        setScore(score);
    }

    /**
     * предмет.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * оценка.
     */
    public void setScore(int score) {
        if (score < 2 || score > 5) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 2 до 5.");
        }
        this.score = score;
    }

    /**
     * score.
     */
    public int getScore() {
        return score;
    }

    /**
     * 3.
     */
    public boolean isSatisfactory() {
        return score == 3;
    }

    /**
     * 4.
     */
    public boolean isGood() {
        return score == 4;
    }

    /**
     * 5.
     */
    public boolean isExcellent() {
        return score == 5;
    }
}