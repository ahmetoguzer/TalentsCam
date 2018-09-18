package com.technoface.app.talentscam.Constants;

import com.dropbox.client2.session.Session;

/**
 * Created by Ahmet on 29.5.2017.
 */

public class Constants {
    public static boolean mLoggedIn = false;

    final static public String DROPBOX_APP_KEY = "6bhchl0giql9mde";
    final static public String DROPBOX_APP_SECRET = "2ntpbgoaa0f6bxf";

    final static public Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

    final static public String ACCOUNT_PREFS_NAME = "prefs";
    final static public String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static public String ACCESS_SECRET_NAME = "ACCESS_SECRET";
}
