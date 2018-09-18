package com.technoface.app.talentscam.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.technoface.app.talentscam.Adapters.BeginTutorialsAdapter;
import com.technoface.app.talentscam.R;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ahmet Oguzer on 21.11.2017.
 */

public class BeginTutorialsActivity extends BaseActivity {

    private ViewPager pager;
    private PageIndicatorView indicator;
    public static Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_tutorials);
        pager = (ViewPager) findViewById(R.id.pager);
        btnGo = (Button) findViewById(R.id.btn_go);
        BeginTutorialsAdapter adapter = new BeginTutorialsAdapter(BeginTutorialsActivity.this);
        pager.setAdapter(adapter);
        indicator = (PageIndicatorView)findViewById(R.id.pageIndicatorView);
        indicator.setViewPager(pager);
        indicator.setAnimationType(AnimationType.FILL);



        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeginTutorialsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}

