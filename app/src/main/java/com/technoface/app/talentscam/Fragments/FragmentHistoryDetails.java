package com.technoface.app.talentscam.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ahmet Oguzer on 9.11.2017.
 */

public class FragmentHistoryDetails extends BaseFragment implements VlcListener {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private TextView userName,opponentName,tvUserPuan,tvOpponent,userPoint,OppenentPoint;
    private CircleImageView   userPhoto,oppenetPhoto;
    private String pos;
    private ArrayList<KarsilasmaHistoryVo> myList;
    private RecyclerView rv;
    private VlcVideoLibrary vlcVideoLibraryOppenent;
    VideoView VideoView;
    ImageButton btnBack;

    LinearLayout layoutOppenent;

    public static FragmentHistoryDetails newInstance() {
        FragmentHistoryDetails fragment = new FragmentHistoryDetails();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history_details, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        attemptLogin();
    }

    private void init(View v){
        pos = getArguments().getString("pos");
        myList = new ArrayList<>();
        userName = (TextView) v.findViewById(R.id.txt_meydann_okuma_user);
        opponentName = (TextView) v.findViewById(R.id.txt_meydann_okuma_opponent);

        tvUserPuan = (TextView) v.findViewById(R.id.tv_puan_user);
        tvOpponent  = (TextView) v.findViewById(R.id.tv_puan_opponent);


        userPoint = (TextView) v.findViewById(R.id.person_score_user);
        OppenentPoint  = (TextView) v.findViewById(R.id.person_score_opponent);

        userPhoto = (CircleImageView) v.findViewById(R.id.person_photo_user);
        oppenetPhoto = (CircleImageView) v.findViewById(R.id.person_photo_user);

         VideoView = (VideoView) v.findViewById(R.id.surfaceView_preview_user);

        SurfaceView surfaceViewOppenent = (SurfaceView) v.findViewById(R.id.surfaceView_preview_opponent);
        vlcVideoLibraryOppenent = new VlcVideoLibrary(getActivity(), this, surfaceViewOppenent);

        layoutOppenent = (LinearLayout) v.findViewById(R.id.layout_oppenent);
        btnBack = (ImageButton) v.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VlcVideoLibrary.player.pause();
                VideoView.stopPlayback();
               MainMenuActivity.screenBackCall();
            }
        });
        ViewOnTouch.onTouchEffect(btnBack);


    }


    private void getJsonData(JSONObject json){
        KarsilasmaHistoryVo vo = new KarsilasmaHistoryVo();
        vo.setOpponentId(json.optString("OpponentId"));
        vo.setOpponentName(json.optString("OpponentName"));
        vo.setOpponentImageUrl(json.optString("OpponentImageUrl"));
        vo.setOpponentScore(json.optString("OpponentScore"));
        vo.setOpponentPoint(json.optString("OpponentPoint"));
        vo.setOpponentVideoUrl(json.optString("OpponentVideoUrl"));
        vo.setUserName(json.optString("UserName"));
        vo.setUserAvatarUrl(json.optString("UserAvatarUrl"));
        vo.setShootingScore(json.optString("ShootingScore"));
        vo.setUserPointPerShooting(json.optString("UserPointPerShooting"));
        vo.setShootingVideoUrl(json.optString("ShootingVideoUrl"));
        vo.setUserPoint(json.optString("UserPoint"));
        myList.add(vo);

        String date=vo.getShootingCreationDate();
        String creationDate=date.substring(6,19);

//
//        if(!vo.get.equals("")){
//            Picasso.with(getApplicationContext()).load(imageurl).into( imgPhoto);
//        } else {
//            imgPhoto.setImageResource(R.drawable.defaultprofile);
//        }


    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetHistory(AppController.getInstance().userId));
    }


    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray completedCompetition = obj.getJSONArray("ResultObjects");
                        JSONObject json = completedCompetition.getJSONObject(Integer.parseInt(pos));
                        getJsonData(json);

                    } else {
                        Log.e("Login activity", obj.optString("errorMessage"));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(),"HATA",);
            }


            KarsilasmaHistoryVo vo1= myList.get(0);


            if(vo1.getOpponentId().equals("0")){
                layoutOppenent.setVisibility(View.INVISIBLE);
            }else {
                layoutOppenent.setVisibility(View.VISIBLE);
            }

            userName.setText(vo1.getUserName());
            opponentName.setText(vo1.getOpponentName());

            tvUserPuan.setText(vo1.getShootingScore());
            tvOpponent.setText(vo1.getOpponentScore());


            VideoView.setVideoURI(Uri.parse(vo1.getShootingVideoUrl()));
            VideoView.start();
            vlcVideoLibraryOppenent.play(vo1.getOpponentVideoUrl());

            userPoint.setText(vo1.getUserPoint());
            OppenentPoint.setText(vo1.getOpponentPoint());

            final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                    .threadPriority(Thread.NORM_PRIORITY)
                    .denyCacheImageMultipleSizesInMemory()
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCacheSize(1*1024*2014)
                    .discCacheSize(2*1024*1024)
                    .build();

            ImageLoader.getInstance().init(config);
            ImageLoader imageLoader=ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisc()
                    .cacheInMemory()
                    .build();

            imageLoader.displayImage(vo1.getUserAvatarUrl(), userPhoto, options);
            imageLoader.displayImage(vo1.getOpponentImageUrl(), oppenetPhoto, options);

        }
    };

    @Override
    public void onComplete() {
    }

    @Override
    public void onError() {

    }

}

