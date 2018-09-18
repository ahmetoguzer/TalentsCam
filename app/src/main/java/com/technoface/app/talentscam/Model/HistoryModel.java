package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.HistoryVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 7.11.2017.
 */

public class HistoryModel implements Serializable {

    public ArrayList<HistoryVo> getHistory(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                HistoryVo vo = new HistoryVo();
                vo.setShootingScore(json.optString("ShootingScore"));
                vo.setOpponentImageUrl(json.optString("OpponentImageUrl"));
                vo.setOpponentName(json.optString("OpponentName"));
                vo.setOpponentScore(json.optString("OpponentScore"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
