package ru.nsu.brykin;

class Grade {
    private String subject;
    private String type; // "exam" or "credit"
    private int score; // Score from 2 to 5

    public Grade(String subject, String type, int score) {
        this.subject = subject;
        this.type = type;
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

    public boolean isFinal() {
        return type.equals("exam");
    }
}