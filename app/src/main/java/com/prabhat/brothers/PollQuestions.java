package com.prabhat.brothers;

/**
 * Created by HP on 3/30/2018.
 */

public class PollQuestions {

    private String questionTitle, opt1, opt2, opt1Votes, opt2Votes;

    public PollQuestions() {
    }

    public PollQuestions(String questionTitle, String opt1, String opt2, String opt1Votes, String opt2Votes) {
        this.questionTitle = questionTitle;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt1Votes = opt1Votes;
        this.opt2Votes = opt2Votes;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt1Votes() {
        return opt1Votes;
    }

    public void setOpt1Votes(String opt1Votes) {
        this.opt1Votes = opt1Votes;
    }

    public String getOpt2Votes() {
        return opt2Votes;
    }

    public void setOpt2Votes(String opt2Votes) {
        this.opt2Votes = opt2Votes;
    }

}
