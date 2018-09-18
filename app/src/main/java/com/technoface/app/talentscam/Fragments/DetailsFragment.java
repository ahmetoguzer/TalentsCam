package com.technoface.app.talentscam.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dropbox.core.DbxException;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.HistoryDetailsAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.DropBoxClient;
import com.technoface.app.talentscam.Model.HistoryModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.HistoryVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static io.fabric.sdk.android.services.common.CommonUtils.md5;

/**
 * Created by Ahmet Oguzer on 24.10.2017.
 */

public class DetailsFragment extends BaseFragment implements VlcListener,View.OnClickListener {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private  String movLink,imageurl;
    private VlcVideoLibrary vlcVideoLibrary;
    private TextView tvName,tvPuan,tvTotalPuan;
    private ImageView imgPhoto;
    private CircularProgressBar circularProgressBar;
    private Button btnStart;
    private ImageButton btnBack,btnPlay;
    private AVLoadingIndicatorView aviProgress;
    private AdView mAdView;
    //    private RecyclerView rv;
    private ArrayList<HistoryVo> myList;


    private boolean isPlayVideo = false;


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((MainMenuActivity) getActivity()).setOnBackPressedListener(this);
        init(view);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        vlcVideoLibrary.play(AppController.getInstance().selectedChallengeVideoLink);
        isPlayVideo = true;
        aviProgress.show();
//        AdView adView = new AdView(getActivity());
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-9612034478800875/3232625080");
        MobileAds.initialize(getActivity(), "ca-app-pub-9612034478800875~1564320195");
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        super.onActivityCreated(savedInstanceState);
    }

    private void init(View rootview){
        SurfaceView surfaceView = (SurfaceView) rootview.findViewById(R.id.surfaceView_preview);
        vlcVideoLibrary = new VlcVideoLibrary(getActivity(), this, surfaceView);
        tvName = (TextView) rootview.findViewById(R.id.txt_profilname);
        tvPuan = (TextView) rootview.findViewById(R.id.tv_puan);
        tvTotalPuan = (TextView) rootview.findViewById(R.id.tvTotalPuan);

        imgPhoto = (ImageView) rootview.findViewById(R.id.person_photo);
        circularProgressBar = (CircularProgressBar)rootview.findViewById(R.id.meydan_okuma_CircularProgressbar);

        btnStart = (Button) rootview.findViewById(R.id.btn_start);
        btnBack = (ImageButton) rootview.findViewById(R.id.back_btn);
        aviProgress = (AVLoadingIndicatorView) rootview.findViewById(R.id.avi);
        btnPlay = (ImageButton) rootview.findViewById(R.id.btn_play_video);
        mAdView = rootview.findViewById(R.id.adView);

//        rv = (RecyclerView) rootview.findViewById(R.id.rv_history_details);
//
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);

        surfaceView.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        myList = new ArrayList<>();

        if(!AppController.getInstance().selectedChallengeImage.contains("https")
                &&  !AppController.getInstance().selectedChallengeImage.contains("technoface")
                &&  !AppController.getInstance().selectedChallengeImage.equals(""))
        {
            String[] pathArray =   AppController.getInstance().selectedChallengeImage.split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= AppController.getInstance().selectedChallengeImage;
        }

        if(!imageurl.equals("")){
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

            imageLoader.displayImage(String.valueOf(imageurl), imgPhoto, options);
        } else {
            imgPhoto.setImageResource(R.drawable.defaultprofile);
        }



        circularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
        circularProgressBar.setProgressBarWidth(4f);
        circularProgressBar.setBackgroundProgressBarWidth(4f);
        int animationDuration = 2000;
        circularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);

        tvName.setText(AppController.getInstance().selectedChallengeName);
        tvPuan.setText(AppController.getInstance().OpponentScore);
        tvTotalPuan.setText(getResources().getString(R.string.total_score)+" : "+AppController.getInstance().OpponentPoint);

        ViewOnTouch.onTouchEffect(btnStart);
        ViewOnTouch.onTouchEffect(btnBack);
        attemptLogin();
    }

    @Override
    public void onPause() {
        super.onPause();
        VlcVideoLibrary.player.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VlcVideoLibrary.player.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start :
                VlcVideoLibrary.player.pause();
                Bundle bundle = new Bundle();
                bundle.putString("state", "0");
                Fragment fragment = RecordChooseFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction1.replace(R.id.frame_layout, fragment,RecordChooseFragment.FRAGMENT_TAG);
                transaction1.disallowAddToBackStack();
                transaction1.commit();
                MainMenuActivity.mStacks.get("Fragments").push(fragment);

                break;
            case R.id.back_btn :
                VlcVideoLibrary.player.pause();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, GeneralPlayersFragment.newInstance());
//                transaction.addToBackStack(null);
//                transaction.commit();
                MainMenuActivity.screenBackCall();

                break;
            case R.id.surfaceView_preview :
                if(isPlayVideo){
                    VlcVideoLibrary.player.pause();
                    isPlayVideo = false;
                    btnPlay.setVisibility(View.VISIBLE);
                }else{
                    float length=VlcVideoLibrary.player.getPosition();
                    VlcVideoLibrary.player.setPosition(length);
                    VlcVideoLibrary.player.play();
                    btnPlay.setVisibility(View.GONE);
                    isPlayVideo = true;
                }
                break;
            case R.id.btn_play_video :
                float length=VlcVideoLibrary.player.getPosition();
                VlcVideoLibrary.player.setPosition(length);
                VlcVideoLibrary.player.play();
                btnPlay.setVisibility(View.GONE);
                isPlayVideo = true;
                break;
        }

    }

    @Override
    public void onComplete() {
        aviProgress.hide();
    }

    @Override
    public void onError() {

    }

//    @Override
//    public void doBack() {
//        VlcVideoLibrary.player.pause();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, GeneralPlayersFragment.newInstance());
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }


    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetUserHistory(AppController.getInstance().CompetitonId,"2"));
    }



    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray completedCompetition = obj.getJSONArray("ResultObjects");
                        HistoryModel model = new HistoryModel();
                        myList = model.getHistory(completedCompetition);
//
//                        HistoryDetailsAdapter adapter=new HistoryDetailsAdapter(getActivity(),myList);
//                        rv.setAdapter(adapter);
                    } else {

                        Log.e("Login activity", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

}
