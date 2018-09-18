package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.CountryVo;
import com.technoface.app.talentscam.Vo.GrupVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 15.01.2018.
 */

public class CountryModel {
    public ArrayList<CountryVo> getCountry(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                CountryVo vo = new CountryVo();
                vo.setCountryName(json.optString("CountryName"));
                vo.setCountryCode(json.optString("CountryCode"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
