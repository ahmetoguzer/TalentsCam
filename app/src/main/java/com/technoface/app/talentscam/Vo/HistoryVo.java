package com.technoface.app.talentscam.Vo;

/**
 * Created by Ahmet Oguzer on 7.11.2017.
 */

public class HistoryVo {
    private String ShootingScore;
    private String OpponentImageUrl;
    private String OpponentName;
    private String OpponentScore;

    public String getOpponentScore() {
        return OpponentScore;
    }

    public void setOpponentScore(String opponentScore) {
        OpponentScore = opponentScore;
    }

    public String getShootingScore() {
        return ShootingScore;
    }

    public void setShootingScore(String shootingScore) {
        ShootingScore = shootingScore;
    }

    public String getOpponentImageUrl() {
        return OpponentImageUrl;
    }

    public void setOpponentImageUrl(String opponentImageUrl) {
        OpponentImageUrl = opponentImageUrl;
    }

    public String getOpponentName() {
        return OpponentName;
    }

    public void setOpponentName(String opponentName) {
        OpponentName = opponentName;
    }
}
