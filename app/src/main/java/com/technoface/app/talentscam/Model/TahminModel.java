package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.HistoryVo;
import com.technoface.app.talentscam.Vo.TahminVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class TahminModel implements Serializable {
    public ArrayList<TahminVo> getTahmin(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                TahminVo vo = new TahminVo();
                vo.setGuessQuestionId(json.optString("GuessQuestionId"));
                vo.setUserId(json.optString("UserId"));
                vo.setGuessTitle(json.optString("GuessTitle"));
                vo.setGuessVideo(json.optString("GuessVideo"));
                vo.setStartSecond(json.optString("StartSecond"));
                vo.setStopSecond(json.optString("StopSecond"));
                vo.setAnswer(json.optString("Answer"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
