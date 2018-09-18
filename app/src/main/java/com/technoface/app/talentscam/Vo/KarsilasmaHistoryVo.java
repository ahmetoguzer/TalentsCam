package com.technoface.app.talentscam.Vo;

import java.io.Serializable;

/**
 * Created by Ahmet on 12.6.2017.
 */

public class KarsilasmaHistoryVo implements Serializable {
    private String OpponentId;
    private String OpponentName;
    private String OpponentImageUrl;
    private String OpponentScore;
    private String OpponentPoint;
    private String OpponentVideoUrl;






    private String UserName;
    private String UserAvatarUrl;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAvatarUrl() {
        return UserAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        UserAvatarUrl = userAvatarUrl;
    }

    public String getShootingScore() {
        return ShootingScore;
    }

    public void setShootingScore(String shootingScore) {
        ShootingScore = shootingScore;
    }

    public String getUserPointPerShooting() {
        return UserPointPerShooting;
    }

    public void setUserPointPerShooting(String userPointPerShooting) {
        UserPointPerShooting = userPointPerShooting;
    }

    public String getShootingVideoUrl() {
        return ShootingVideoUrl;
    }

    public void setShootingVideoUrl(String shootingVideoUrl) {
        ShootingVideoUrl = shootingVideoUrl;
    }

    public String getUserPoint() {
        return UserPoint;
    }

    public void setUserPoint(String userPoint) {
        UserPoint = userPoint;
    }

    private String ShootingScore;
    private String UserPointPerShooting;
    private String ShootingVideoUrl;
    private String UserPoint;


    public String getShootingCreationDate() {
        return ShootingCreationDate;
    }

    public void setShootingCreationDate(String shootingCreationDate) {
        ShootingCreationDate = shootingCreationDate;
    }

    private String ShootingCreationDate;


    public String getOpponentId() {
        return OpponentId;
    }

    public void setOpponentId(String opponentId) {
        OpponentId = opponentId;
    }

    public String getOpponentName() {
        return OpponentName;
    }

    public void setOpponentName(String opponentName) {
        OpponentName = opponentName;
    }

    public String getOpponentImageUrl() {
        return OpponentImageUrl;
    }

    public void setOpponentImageUrl(String opponentImageUrl) {
        OpponentImageUrl = opponentImageUrl;
    }

    public String getOpponentScore() {
        return OpponentScore;
    }

    public void setOpponentScore(String opponentScore) {
        OpponentScore = opponentScore;
    }

    public String getOpponentPoint() {
        return OpponentPoint;
    }

    public void setOpponentPoint(String opponentPoint) {
        OpponentPoint = opponentPoint;
    }

    public String getOpponentVideoUrl() {
        return OpponentVideoUrl;
    }

    public void setOpponentVideoUrl(String opponentVideoUrl) {
        OpponentVideoUrl = opponentVideoUrl;
    }
}
