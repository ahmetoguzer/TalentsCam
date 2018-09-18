package com.technoface.app.talentscam.Vo;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class TahminVo {

    private String GuessQuestionId;
    private String UserId;
    private String GuessTitle;
    private String GuessVideo;
    private String StartSecond;
    private String StopSecond;
    private String Answer;

    public String getGuessQuestionId() {
        return GuessQuestionId;
    }

    public void setGuessQuestionId(String guessQuestionId) {
        GuessQuestionId = guessQuestionId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getGuessTitle() {
        return GuessTitle;
    }

    public void setGuessTitle(String guessTitle) {
        GuessTitle = guessTitle;
    }

    public String getGuessVideo() {
        return GuessVideo;
    }

    public void setGuessVideo(String guessVideo) {
        GuessVideo = guessVideo;
    }

    public String getStartSecond() {
        return StartSecond;
    }

    public void setStartSecond(String startSecond) {
        StartSecond = startSecond;
    }

    public String getStopSecond() {
        return StopSecond;
    }

    public void setStopSecond(String stopSecond) {
        StopSecond = stopSecond;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
