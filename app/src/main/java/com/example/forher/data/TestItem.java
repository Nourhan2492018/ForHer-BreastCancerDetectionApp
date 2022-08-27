package com.example.forher.data;

public class TestItem {
    String testQuestion;
    String answerA;
    String answerB;

    public TestItem(String testQuestion, String answerA, String answerB) {
        this.testQuestion = testQuestion;
        this.answerA = answerA;
        this.answerB = answerB;
    }

    public String getTestQuestion() {
        return testQuestion;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }
}
