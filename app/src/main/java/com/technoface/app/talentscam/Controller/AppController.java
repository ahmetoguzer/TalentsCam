package com.technoface.app.talentscam.Controller;

/**
 * Created by Ahmet on 25.5.2017.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.support.multidex.MultiDexApplication;
import android.support.multidex.MultiDex;

/**
 * Created by Ahmet on 10.5.2017.
 */

public class AppController extends MultiDexApplication  {

    private static AppController mInstance;
    public Uri profilImageUrl;
    public String profilName;
    public Uri profilLink;
    public String profilGender;
    public String profilTimezone;
    public String profilLocale;
    public String profilEmail;
    public String profilFriendsCount;
    public String profilFriends;
    public String profileID;
    public String profilAge;

    public String userPuan;
    public String userLevel;
    public String userId;


    public String CompetitonId="0";
    public String OpponentId;
    public String OpponentScore="0";
    public String outputfilename;
    public String recordedUrl;
    public String SmartCamId;
    public String SmartCamRecordingId;
    public String rtspUrl;
    public String revisionID;
    public Context context;
    public String grupid="0";
    public String SmartCamTitle;
    public String deviceToken;
    public Fragment goOnFragment=null;
    public boolean isAdmin;
    public String groupId;


    public String selectedChallengeName;
    public String selectedChallengeVideoLink;
    public String selectedChallengeImage;

    public String photoFilePath;

    public String ContentId;
    public String youtubeLink;
    public String ContentTitle;
    public String ContentDetail;
    public Fragment currentFragment;
    public String OpponentPoint;

    public boolean isOpenGeneralPlayersFragment = false;


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        printHashKey();
    }

    public void printHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.technoface.app.duello",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:", e.toString());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}