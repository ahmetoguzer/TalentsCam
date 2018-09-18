package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.PrizesVo;
import com.technoface.app.talentscam.Vo.RewardVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 11.01.2018.
 */

public class RewardModel implements Serializable {

    public ArrayList<RewardVo> getReward(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                RewardVo vo = new RewardVo();
                vo.setQuestionId(json.optString("QuestionId"));
                vo.setQuestionText(json.optString("QuestionText"));
                vo.setAnswer(json.optString("Answer"));
                vo.setPoint(json.optString("Point"));
                vo.setChoices(json.optJSONArray("Choices"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
