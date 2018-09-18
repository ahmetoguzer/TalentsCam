package com.technoface.app.talentscam.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.technoface.app.talentscam.R;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ahmet on 3.7.2017.
 */
public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

//
//        initToolBar();
    }

//
//    public void initToolBar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }

//    protected void addFragment(@IdRes int containerViewId,
//                               @NonNull Fragment fragment,
//                               @NonNull String fragmentTag) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(containerViewId, fragment, fragmentTag)
//                .disallowAddToBackStack()
//                .commit();
//    }
}