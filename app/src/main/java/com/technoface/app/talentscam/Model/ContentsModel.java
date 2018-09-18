package com.technoface.app.talentscam.Model;

import com.technoface.app.talentscam.Vo.ContentsVo;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 6.12.2017.
 */

public class ContentsModel {

    public ArrayList<ContentsVo> getContents(JSONArray e){
        ArrayList  myList = new ArrayList();
        try {
            for (int i = 0; i < e.length(); i++) {
                JSONObject json = e.getJSONObject(i);
                ContentsVo vo = new ContentsVo();
                vo.setCategoryId(json.optString("CategoryId "));
                vo.setCategoryTitle(json.optString("CategoryTitle"));
                vo.setContentId(json.optString("ContentId"));
                vo.setContentTitle(json.optString("ContentTitle"));
                vo.setContentImage(json.optString("ContentImage"));
                vo.setContentVideo(json.optString("ContentVideo"));
                vo.setContentDetail(json.optString("ContentDetail"));
                vo.setItems(json.optJSONArray("Items"));
                vo.setNestedContent(json.optString("NestedContent"));
                myList.add(vo);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return myList;
    }
}
