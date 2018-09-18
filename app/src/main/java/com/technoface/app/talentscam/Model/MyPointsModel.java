package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.MyPointsVo;
import com.technoface.app.talentscam.Vo.PrizesVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 18.01.2018.
 */

public class MyPointsModel implements Serializable {

    public ArrayList<MyPointsVo> getMyPoint(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                MyPointsVo vo = new MyPointsVo();
                vo.setIsTotal(json.optString("IsTotal"));
                vo.setPointDate(json.optString("PointDate"));
                vo.setPointName(json.optString("PointName"));
                vo.setPointDescription(json.optString("PointDescription"));
                vo.setPoint(json.optString("Point"));

                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
