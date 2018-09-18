package com.technoface.app.talentscam.Vo;

import org.json.JSONArray;

/**
 * Created by Ahmet Oguzer on 11.01.2018.
 */

public class RewardVo {

    private String QuestionId;
    private String QuestionText;
    private JSONArray Choices;

    public JSONArray getChoices() {
        return Choices;
    }

    public void setChoices(JSONArray choices) {
        Choices = choices;
    }

    private String Answer;
    private String Point;

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }


    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }
}
