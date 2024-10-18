package ru.nsu.brykin;

class Grade {
    private String subject;
    private String type;
    private int score;

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