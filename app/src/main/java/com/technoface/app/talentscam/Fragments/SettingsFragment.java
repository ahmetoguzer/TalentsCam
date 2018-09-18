package com.technoface.app.talentscam.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.LoginActivity;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ahmet on 13.6.2017.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener{

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ImageView imgProfil;
    private Button cikis;
    private TextView name,puan;
    private CircularProgressBar circularProgressBar;
    private RelativeLayout lytGecmisim,lytPuanDurumu,lytProfil,lytSmartCams,lytCikis,lytYardim;
    private MySharedpreferences mySharedPreferences;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        attemptLogin();
    }

    private void init(View rootView){
        imgProfil = (ImageView)rootView.findViewById(R.id.person_photo);
        name = (TextView) rootView.findViewById(R.id.txt_profilname);
        puan = (TextView) rootView.findViewById(R.id.tv_puan);
        cikis = (Button) rootView.findViewById(R.id.profil_cikis);
        circularProgressBar = (CircularProgressBar)rootView.findViewById(R.id.meydan_okuma_CircularProgressbar);

        lytGecmisim = (RelativeLayout) rootView.findViewById(R.id.layout_gecmisim);
        lytPuanDurumu = (RelativeLayout) rootView.findViewById(R.id.layout_puan_durumu);
        lytProfil = (RelativeLayout) rootView.findViewById(R.id.layout_profilim);
        lytSmartCams = (RelativeLayout) rootView.findViewById(R.id.layout_smart_cam);
        lytCikis = (RelativeLayout) rootView.findViewById(R.id.layout_cikis);
        lytYardim =  (RelativeLayout) rootView.findViewById(R.id.layout_yardim);
        lytSmartCams.setOnClickListener(this);
        lytYardim.setOnClickListener(this);
        lytProfil.setOnClickListener(this);
        lytGecmisim.setOnClickListener(this);
        lytPuanDurumu.setOnClickListener(this);
        lytCikis.setOnClickListener(this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(50*1024*2014)
                .discCacheSize(50*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(String.valueOf(AppController.getInstance().profilImageUrl), imgProfil, options);

        name.setText(AppController.getInstance().profilName);
        puan.setText(AppController.getInstance().userPuan);
        circularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
        circularProgressBar.setProgressBarWidth(4f);
        circularProgressBar.setBackgroundProgressBarWidth(4f);
        int animationDuration = 2000; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);

        onTouchEffect(lytGecmisim);
        onTouchEffect(lytProfil);
        onTouchEffect(lytPuanDurumu);
        onTouchEffect(lytSmartCams);
        onTouchEffect(lytCikis);
        mySharedPreferences= MySharedpreferences.getInstance(getActivity());

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        super.onActivityCreated(savedInstanceState);
    }

    private void onTouchEffect(RelativeLayout layout){
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    v.setAlpha(0.1f);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    v.setAlpha(1);
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_gecmisim:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.frame_layout, HistoryFragment.newInstance(),HistoryFragment.FRAGMENT_TAG);
                transaction.disallowAddToBackStack();
                transaction.commit();
                MainMenuActivity.mStacks.get("Fragments").push(HistoryFragment.newInstance());
                MainMenuActivity.selectedFragment = HistoryFragment.newInstance();
                MainMenuActivity.setTitle();

                break;
            case R.id.layout_puan_durumu:
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction1.replace(R.id.frame_layout, RakingFragment.newInstance(),RakingFragment.FRAGMENT_TAG);
                transaction1.disallowAddToBackStack();
                transaction1.commit();
                MainMenuActivity.mStacks.get("Fragments").push(RakingFragment.newInstance());
                MainMenuActivity.selectedFragment = RakingFragment.newInstance();
                MainMenuActivity.setTitle();

                break;
            case R.id.layout_profilim:
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction2.replace(R.id.frame_layout, ProfileFragment.newInstance(),ProfileFragment.FRAGMENT_TAG);
                transaction2.disallowAddToBackStack();
                transaction2.commit();
                MainMenuActivity.mStacks.get("Fragments").push(ProfileFragment.newInstance());
                MainMenuActivity.selectedFragment = ProfileFragment.newInstance();
                MainMenuActivity.setTitle();

                break;
            case R.id.layout_yardim:
                FragmentTransaction transac = getActivity().getSupportFragmentManager().beginTransaction();
                transac.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transac.replace(R.id.frame_layout, MyPointsFragment.newInstance(),MyPointsFragment.FRAGMENT_TAG);
                transac.disallowAddToBackStack();
                transac.commit();
                MainMenuActivity.mStacks.get("Fragments").push(MyPointsFragment.newInstance());
                MainMenuActivity.selectedFragment = MyPointsFragment.newInstance();
                MainMenuActivity.setTitle();

                break;
            case R.id.layout_smart_cam:
                Bundle bund = new Bundle();
                bund.putString("state", "0");
                Fragment frg = ChangeCountryFragment.newInstance();
                frg.setArguments(bund);
                FragmentTransaction transaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.frame_layout,frg,ChangeCountryFragment.FRAGMENT_TAG);
                transaction3.disallowAddToBackStack();
                transaction3.commit();
                MainMenuActivity.mStacks.get("Fragments").push(ChangeCountryFragment.newInstance());
                MainMenuActivity.selectedFragment = ChangeCountryFragment.newInstance();
                MainMenuActivity.setTitle();


                break;
            case R.id.layout_cikis:
//                LoginManager.getInstance().logOut();
                AppController.getInstance().grupid="0";
                startActivity(new Intent(getActivity(), LoginActivity.class));

                mySharedPreferences.saveData("userid","");
                mySharedPreferences.saveData("user_Email", "");
                mySharedPreferences.saveData("user_name", "");
                mySharedPreferences.saveData("user_Puan", "");
                mySharedPreferences.saveData("user_avatarUrl", "");
                mySharedPreferences.saveData("user_age", "");
                mySharedPreferences.saveData("user_Gender", "");
                break;
        }
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetUserDetails(AppController.getInstance().userId));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = new JSONArray(obj.optString("ResultObjects"));
                        JSONObject json = jsonArray.getJSONObject(0);
                        mySharedPreferences.saveData("userid",json.optString("UserId"));
                        mySharedPreferences.saveData("user_Email", json.optString("UserEmail"));
                        mySharedPreferences.saveData("user_name", json.optString("UserName"));
                        mySharedPreferences.saveData("user_Puan", json.optString("Puan"));
                        mySharedPreferences.saveData("user_avatarUrl", json.optString("UserAvatarUrl"));
                        mySharedPreferences.saveData("user_age", json.optString("UserAge"));
                        mySharedPreferences.saveData("user_Gender", json.optString("UserGender"));

                        AppController.getInstance().profilEmail=json.optString("UserEmail");
                        AppController.getInstance().userId=json.optString("UserId");
                        AppController.getInstance().userPuan=json.optString("Puan");
                        AppController.getInstance().userLevel=json.optString("PuanLevel");
                        AppController.getInstance().profilAge = json.optString("UserAge");
                        AppController.getInstance().profilGender = json.optString("UserGender");
                        AppController.getInstance().profilImageUrl = Uri.parse(json.optString("UserAvatarUrl"));

                    } else {
                        Log.e("Settings Fragment", obj.optString("errorMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };


}
