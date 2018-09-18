package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.PrizesVo;
import com.technoface.app.talentscam.Vo.TahminVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class PrizesModel implements Serializable {

    public ArrayList<PrizesVo> getPrizes(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                PrizesVo vo = new PrizesVo();
                vo.setPrizeId(json.optString("PrizeId"));
                vo.setPrizeTitle(json.optString("PrizeTitle"));
                vo.setPrizePoint(json.optString("PrizePoint"));
                vo.setPrizeQuote(json.optString("PrizeQuote"));
                vo.setPrizeImage(json.optString("PrizeImage"));
                vo.setExtraDataMessage(json.optString("ExtraDataMessage"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
