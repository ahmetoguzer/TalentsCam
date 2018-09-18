package com.technoface.app.talentscam.Vo;

import org.json.JSONArray;

/**
 * Created by Ahmet Oguzer on 6.12.2017.
 */

public class ContentsVo {
    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getNestedContent() {
        return NestedContent;
    }

    public void setNestedContent(String nestedContent) {
        NestedContent = nestedContent;
    }

    private String CategoryId;
    private String CategoryTitle;
    private String ContentId;
    private String ContentTitle;
    private String ContentImage;
    private String ContentVideo;
    private JSONArray Items;
    private String NestedContent;

    public JSONArray getItems() {
        return Items;
    }

    public void setItems(JSONArray items) {
        Items = items;
    }

    public String getContentId() {
        return ContentId;
    }

    public void setContentId(String contentId) {
        ContentId = contentId;
    }

    public String getContentTitle() {
        return ContentTitle;
    }

    public void setContentTitle(String contentTitle) {
        ContentTitle = contentTitle;
    }

    public String getContentImage() {
        return ContentImage;
    }

    public void setContentImage(String contentImage) {
        ContentImage = contentImage;
    }

    public String getContentVideo() {
        return ContentVideo;
    }

    public void setContentVideo(String contentVideo) {
        ContentVideo = contentVideo;
    }

    public String getContentDetail() {
        return ContentDetail;
    }

    public void setContentDetail(String contentDetail) {
        ContentDetail = contentDetail;
    }

    private String ContentDetail;

}
