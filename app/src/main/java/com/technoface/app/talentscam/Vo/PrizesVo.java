package com.technoface.app.talentscam.Vo;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class PrizesVo {

    private String PrizeId;
    private String PrizeTitle;
    private String PrizePoint;
    private String PrizeQuote;
    private String PrizeImage;
    private String ExtraDataMessage;

    public String getPrizeId() {
        return PrizeId;
    }

    public void setPrizeId(String prizeId) {
        PrizeId = prizeId;
    }

    public String getPrizeTitle() {
        return PrizeTitle;
    }

    public void setPrizeTitle(String prizeTitle) {
        PrizeTitle = prizeTitle;
    }

    public String getPrizePoint() {
        return PrizePoint;
    }

    public void setPrizePoint(String prizePoint) {
        PrizePoint = prizePoint;
    }

    public String getPrizeQuote() {
        return PrizeQuote;
    }

    public void setPrizeQuote(String prizeQuote) {
        PrizeQuote = prizeQuote;
    }

    public String getPrizeImage() {
        return PrizeImage;
    }

    public void setPrizeImage(String prizeImage) {
        PrizeImage = prizeImage;
    }

    public String getExtraDataMessage() {
        return ExtraDataMessage;
    }

    public void setExtraDataMessage(String extraDataMessage) {
        ExtraDataMessage = extraDataMessage;
    }
}
