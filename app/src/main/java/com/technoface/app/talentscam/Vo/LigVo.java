package com.technoface.app.talentscam.Vo;

import java.io.Serializable;

/**
 * Created by Ahmet on 13.6.2017.
 */

public class LigVo implements Serializable {

    private String Sira;
    private String UserId;
    private String UserName;
    private String UserAvatarUrl;
    private String ToplamPuan;

    public String getSira() {
        return Sira;
    }

    public void setSira(String sira) {
        Sira = sira;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

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

    public String getToplamPuan() {
        return ToplamPuan;
    }

    public void setToplamPuan(String toplamPuan) {
        ToplamPuan = toplamPuan;
    }

    public String getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(String userLevel) {
        UserLevel = userLevel;
    }

    private String UserLevel;




}
