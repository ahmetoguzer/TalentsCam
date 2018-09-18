package com.technoface.app.talentscam;

import android.content.Context;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Created by Ahmet Oguzer on 31.10.2017.
 */

public class DropBoxClient {

    public static DbxClientV2 getClient(Context ctx){

        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config,ctx.getResources().getString(R.string.dropbox_api_key));

        return client;
    }
}
