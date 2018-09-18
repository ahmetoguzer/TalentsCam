package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.MeydanOkumaVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet on 5.6.2017.
 */

public class MeydanOkumaModel {

    public ArrayList<MeydanOkumaVo> getCompetition(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                MeydanOkumaVo vo = new MeydanOkumaVo();
                vo.setCompetitionId(json.optString("CompetitionId"));
                vo.setCompetitionName(json.optString("CompetitionName"));
                vo.setCompetitionImageUrl(json.optString("CompetitionImageUrl"));
                vo.setCompetitionVideoUrl(json.optString("CompetitionVideoUrl"));
                vo.setCompetitionLevel(json.optString("CompetitionLevel"));
                vo.setCompetitionScore(json.optString("CompetitionScore"));
                vo.setCompetitionStatus(json.optString("CompetitionStatus"));
                vo.setShootingProgress(json.optString("ShootingProgress"));
                vo.setDuelloStatus(json.optString("DuelloStatus"));
                vo.setPreviewUrl(json.optString("PreviewUrl"));
                vo.setOpponentPoint(json.optString("OpponentPoint"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
