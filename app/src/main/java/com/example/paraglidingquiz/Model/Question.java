package com.example.paraglidingquiz.Model;

import java.io.Serializable;

public class Question implements Serializable {
    private final long id;
    private final String question;
    private final String[] answers;
    private final int correctIndex;
    private final int chapter;
    private final int answeredCorrectCounter;
    private final int answeredWrongCounter;
    private boolean answeredWrong;

    public Question(long id, String question, String[] answers, int correctIndex, int chapter, int answeredCorrectCounter, int answeredWrongCounter) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
        this.answeredCorrectCounter = answeredCorrectCounter;
        this.answeredWrongCounter = answeredWrongCounter;
        this.chapter = chapter;
    }

    public boolean validate(short index) {
        return index == correctIndex;
    }

    public long getId() { return id; }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public int getAnsweredCorrectCounter() {
        return answeredCorrectCounter;
    }

    public int getAnsweredWrongCounter() {
        return answeredWrongCounter;
    }

    public boolean getUnanswered() { return answeredCorrectCounter + answeredWrongCounter == 0; }

    public boolean isAnsweredWrong() {
        return answeredCorrectCounter < answeredWrongCounter;
    }

    public void setAnsweredWrong(boolean answeredWrong) {
        this.answeredWrong = answeredWrong;
    }

    public int getChapter() {
        return chapter;
    }
}
