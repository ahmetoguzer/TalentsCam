package com.technoface.app.talentscam.Activities;

/**
 * Created by Ahmet on 14.2.2017.
 */
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technoface.app.talentscam.Adapters.BeginTutorialsAdapter;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;

import java.util.Locale;


public class ActivitySplashScreen extends BaseActivity
{

    private static long SPLASH_MILLIS = 1500;

    private MySharedpreferences mySharedPreferences;
    private ImageView splashImage;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.splash_screen, null, false);
        mySharedPreferences= MySharedpreferences.getInstance(ActivitySplashScreen.this);
        splashImage = (ImageView) findViewById(R.id.splash_image);

        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        Locale myLocale = new Locale(mySharedPreferences.getData("lang"));
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        ActivitySplashScreen.this.getResources().updateConfiguration(config, ActivitySplashScreen.this.getResources().getDisplayMetrics());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {

                int position=0;

                if(!mySharedPreferences.getData("user_name").equals("")){
                    AppController.getInstance().profilEmail = mySharedPreferences.getData("user_Email");
                    AppController.getInstance().profilName = mySharedPreferences.getData("user_name");
                    AppController.getInstance().userId = mySharedPreferences.getData("userid");
                    AppController.getInstance().userPuan = mySharedPreferences.getData("user_Puan");
                    AppController.getInstance().profilImageUrl = Uri.parse(mySharedPreferences.getData("user_avatarUrl"));
                    AppController.getInstance().profilAge = mySharedPreferences.getData("user_age");
                    AppController.getInstance().profilGender = mySharedPreferences.getData("user_Gender");
                    AppController.getInstance().profilFriends= mySharedPreferences.getData("user_FacebookFriens");

                    Intent intent = new Intent(ActivitySplashScreen.this,
                            MainMenuActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }else{

                    Intent intent = new Intent(ActivitySplashScreen.this,
                            BeginTutorialsActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }


            }

        }, SPLASH_MILLIS);
    }

}