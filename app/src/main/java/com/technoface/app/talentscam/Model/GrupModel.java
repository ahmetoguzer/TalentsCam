package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.GrupVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet on 5.9.2017.
 */

public class GrupModel {

    public ArrayList<GrupVo> getGrup(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                GrupVo vo = new GrupVo();
                vo.setGroupId(json.optString("GroupId"));
                vo.setGroupName(json.optString("GroupName"));
                vo.setGroupLogo(json.optString("GroupLogo"));
                vo.setUserCount(json.optString("UserCount"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
