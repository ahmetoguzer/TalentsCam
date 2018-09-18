package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.LigVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmet on 13.6.2017.
 */

public class LigModel implements Serializable {

    public ArrayList<LigVo> getRaking(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                LigVo vo = new LigVo();
                vo.setUserId(json.optString("UserId"));
                vo.setUserName(json.optString("UserName"));
                vo.setUserAvatarUrl(json.optString("UserAvatarUrl"));
                vo.setToplamPuan(json.optString("ToplamPuan"));
                vo.setSira(json.optString("Sira"));
                vo.setUserLevel(json.optString("UserLevel"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
