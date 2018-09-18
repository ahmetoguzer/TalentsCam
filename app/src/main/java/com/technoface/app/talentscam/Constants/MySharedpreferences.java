package com.technoface.app.talentscam.Constants;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahmet on 5.9.2017.
 */

public class MySharedpreferences {

    private static MySharedpreferences mySharedPreferences;
    private SharedPreferences sharedPreferences;

    public static MySharedpreferences getInstance(Context context) {
        if (mySharedPreferences == null) {
            mySharedPreferences = new MySharedpreferences(context);
        }
        return mySharedPreferences;
    }

    private MySharedpreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("Duello",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
