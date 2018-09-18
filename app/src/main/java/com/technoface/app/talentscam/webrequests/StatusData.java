package com.technoface.app.talentscam.webrequests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 6/16/2015.
 */
public class StatusData  {

    private boolean status;
    @SerializedName("status_message")
    private String  message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
