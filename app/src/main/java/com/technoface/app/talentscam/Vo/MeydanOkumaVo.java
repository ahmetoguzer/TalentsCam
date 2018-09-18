package com.technoface.app.talentscam.Vo;

import java.io.Serializable;

/**
 * Created by Ahmet on 5.6.2017.
 */

public class MeydanOkumaVo implements Serializable {

    private String CompetitionId;
    private String CompetitionName;
    private String CompetitionImageUrl;
    private String CompetitionVideoUrl;
    private String CompetitionLevel;
    private String CompetitionScore;
    private String CompetitionStatus;
    private String ShootingProgress;
    private String DuelloStatus;
    private String PreviewUrl;
    private String OpponentPoint;

    public String getOpponentPoint() {
        return OpponentPoint;
    }

    public void setOpponentPoint(String opponentPoint) {
        OpponentPoint = opponentPoint;
    }

    public String getPreviewUrl() {
        return PreviewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        PreviewUrl = previewUrl;
    }

    public String getShootingProgress() {
        return ShootingProgress;
    }

    public void setShootingProgress(String duelloProgress) {
        ShootingProgress = duelloProgress;
    }

    public String getDuelloStatus() {
        return DuelloStatus;
    }

    public void setDuelloStatus(String duelloStatus) {
        DuelloStatus = duelloStatus;
    }


    public String getCompetitionId() {
        return CompetitionId;
    }

    public void setCompetitionId(String competitionId) {
        CompetitionId = competitionId;
    }

    public String getCompetitionName() {
        return CompetitionName;
    }

    public void setCompetitionName(String competitionName) {
        CompetitionName = competitionName;
    }

    public String getCompetitionImageUrl() {
        return CompetitionImageUrl;
    }

    public void setCompetitionImageUrl(String competitionImageUrl) {
        CompetitionImageUrl = competitionImageUrl;
    }

    public String getCompetitionVideoUrl() {
        return CompetitionVideoUrl;
    }

    public void setCompetitionVideoUrl(String competitionVideoUrl) {
        CompetitionVideoUrl = competitionVideoUrl;
    }

    public String getCompetitionLevel() {
        return CompetitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        CompetitionLevel = competitionLevel;
    }

    public String getCompetitionScore() {
        return CompetitionScore;
    }

    public void setCompetitionScore(String competitionScore) {
        CompetitionScore = competitionScore;
    }

    public String getCompetitionStatus() {
        return CompetitionStatus;
    }

    public void setCompetitionStatus(String competitionStatus) {
        CompetitionStatus = competitionStatus;
    }



}
