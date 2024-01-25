package org.example.object;

public class Question {

    String question;

    int result;

    String category;


    public Question(String question, int result, String category) {
        this.question = question;
        this.result = result;
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}