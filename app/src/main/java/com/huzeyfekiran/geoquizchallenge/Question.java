package com.huzeyfekiran.geoquizchallenge;

public class Question {
    private int textResId;
    private int questionYesResponse;
    private int quesitonNoResponse;
    private boolean theCoolAnswer;

    public Question(int textResId, int questionYesResponse, int quesitonNoResponse, boolean theCoolAnswer) {
        this.textResId = textResId;
        this.questionYesResponse = questionYesResponse;
        this.quesitonNoResponse = quesitonNoResponse;
        this.theCoolAnswer = theCoolAnswer;
    }

    public int getTextResId() {
        return textResId;
    }

    public int getQuestionYesResponse() {
        return questionYesResponse;
    }

    public int getQuesitonNoResponse() {
        return quesitonNoResponse;
    }

    public boolean gettheCoolAnswer(){
        return theCoolAnswer;
    }
}
