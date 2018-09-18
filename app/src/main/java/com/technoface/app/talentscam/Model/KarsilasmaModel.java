package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet on 12.6.2017.
 */

public class KarsilasmaModel implements Serializable {
    public ArrayList<KarsilasmaHistoryVo> getEndedCompetition(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                KarsilasmaHistoryVo vo = new KarsilasmaHistoryVo();
                vo.setOpponentId(json.optString("OpponentId"));
                vo.setOpponentName(json.optString("OpponentName"));
                vo.setOpponentImageUrl(json.optString("OpponentImageUrl"));
                vo.setOpponentScore(json.optString("OpponentScore"));
                vo.setOpponentPoint(json.optString("OpponentPoint"));
                vo.setOpponentVideoUrl(json.optString("OpponentVideoUrl"));
                vo.setShootingCreationDate(json.optString("ShootingCreationDate"));
                vo.setUserName(json.optString("UserName"));
                vo.setUserAvatarUrl(json.optString("UserAvatarUrl"));
                vo.setShootingScore(json.optString("ShootingScore"));
                vo.setShootingVideoUrl(json.optString("ShootingVideoUrl"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }

}
