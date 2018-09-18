package com.technoface.app.talentscam.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;

import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.RewardModel;
import com.technoface.app.talentscam.R;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.RewardVo;
import com.technoface.app.talentscam.Vo.TahminVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;

import java.util.ArrayList;

import static com.technoface.app.talentscam.Activities.MainMenuActivity.mRewardedVideoAd;
import static io.fabric.sdk.android.services.common.CommonUtils.md5;

/**
 * Created by Ahmet Oguzer on 5.01.2018.
 */

public class RewardVideoFragment extends BaseFragment {
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<RewardVo> myList;
    private TextView tvSoru,tvPoint;
    private int playedQuestion=0;
    private VlcVideoLibrary vlcVideoLibrary;
    private Button btnA,btnB,btnC,btnD,btnSkip,btnJoker;
    private String QuestionId;
    private SurfaceView surfaceView;
    private ImageButton btnBack;
    private String answer;
    private String gift="0";


    public static RewardVideoFragment newInstance() {
        RewardVideoFragment fragment = new RewardVideoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reward_video, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View rootView) {
        myList=new ArrayList<>();
        tvSoru = rootView.findViewById(R.id.tvSoru);
        tvPoint = rootView.findViewById(R.id.tvPoint);
        btnA = rootView.findViewById(R.id.btnA);
        btnB = rootView.findViewById(R.id.btnB);
        btnC = rootView.findViewById(R.id.btnC);
        btnD = rootView.findViewById(R.id.btnD);
        btnSkip = rootView.findViewById(R.id.btnSkip);
        btnJoker = rootView.findViewById(R.id.btnJoker);

        btnBack = rootView.findViewById(R.id.btn_back);
        ViewOnTouch.onTouchEffect(btnA);
        ViewOnTouch.onTouchEffect(btnB);
        ViewOnTouch.onTouchEffect(btnC);
        ViewOnTouch.onTouchEffect(btnD);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();

            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer();
                if( btnA.getText().equals(answer)){

                    btnA.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                            PorterDuff.Mode.SRC_ATOP);
                    attemptWatch();
                }else{
                    btnA.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                            PorterDuff.Mode.SRC_ATOP);

                }

            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer();
                if( btnB.getText().equals(answer)){
                    btnB.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                            PorterDuff.Mode.SRC_ATOP);
                    attemptWatch();
                }else{
                    btnB.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                            PorterDuff.Mode.SRC_ATOP);

                }
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer();
                if( btnC.getText().equals(answer)){
                    btnC.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                            PorterDuff.Mode.SRC_ATOP);
                    attemptWatch();
                }else{
                    btnC.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                            PorterDuff.Mode.SRC_ATOP);
                    CountDownTimer t = new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished)
                        {
                            long remainedSecs = millisUntilFinished/1000;
                        }

                        public void onFinish()
                        {   nextQuestion();
                        }
                    }.start();
                }
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctAnswer();
                if( btnD.getText().equals(answer)){
                    btnD.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                            PorterDuff.Mode.SRC_ATOP);
                    attemptWatch();
                }else{
                    btnD.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                            PorterDuff.Mode.SRC_ATOP);

                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenuActivity.screenBackCall();
            }
        });

        btnJoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRewardedVideoAd.show();
            }
        });

        ViewOnTouch.onTouchEffect(btnBack);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        super.onActivityCreated(savedInstanceState);
        attemptLogin();


        MobileAds.initialize(getActivity(), "ca-app-pub-9612034478800875~1564320195");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        mRewardedVideoAd.loadAd("ca-app-pub-9612034478800875/3331083033", new AdRequest.Builder().build());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
                Toast.makeText(getActivity(), "Ad triggered reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(getActivity(), "Ad loaded.", Toast.LENGTH_SHORT).show();
                btnJoker.setText("Joker");
                btnJoker.setClickable(true);
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Toast.makeText(getActivity(), "Ad opened.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(getActivity(), "Ad started.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Toast.makeText(getActivity(), "Ad closed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(getActivity(), "Ad left application.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(getActivity(), "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void correctAnswer(){
        if( btnA.getText().equals(answer)){
            btnA.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
        }
        if( btnB.getText().equals(answer)){
            btnB.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
        }
        if( btnC.getText().equals(answer)){
            btnC.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
        }
        if( btnD.getText().equals(answer)){
            btnD.getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void nextQuestion(){
        playedQuestion++;
        btnA.setBackground(getResources().getDrawable(R.drawable.custom_orange_border));
        btnB.setBackground(getResources().getDrawable(R.drawable.custom_orange_border));
        btnC.setBackground(getResources().getDrawable(R.drawable.custom_orange_border));
        btnD.setBackground(getResources().getDrawable(R.drawable.custom_orange_border));

        gift="0";
        tvSoru.setText(myList.get(playedQuestion).getQuestionText());
        tvPoint.setText(myList.get(playedQuestion).getPoint());
        answer = myList.get(playedQuestion).getAnswer();
        QuestionId = myList.get(playedQuestion).getQuestionId();
        JSONArray jArray = myList.get(playedQuestion).getChoices();

        try {
            btnA.setText( jArray.get(0)+"");
            btnB.setText( jArray.get(1)+"");
            btnC.setText(jArray.get(2)+"");
            btnD.setText(jArray.get(3)+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetQuizQuestions(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId));
    }

    private void attemptWatch(){
        CustomTask customLoginController = new CustomTask(getActivity(), responseWatch);
        customLoginController.execute(ServiceURLCreator.AddWatch(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId,QuestionId,gift));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = obj.getJSONArray("ResultObjects");

                        if(obj.getJSONArray("ResultObjects").length()==0){
                            Common.alert(getActivity(),"BasketCoin","There is no Watch&Gain right now.Please try again later");
                            MainMenuActivity.screenBackCall();
                        }

                        RewardModel model = new RewardModel();
                        myList = model.getReward(jsonArray);

                        tvSoru.setText(myList.get(playedQuestion).getQuestionText());
                        tvPoint.setText(myList.get(playedQuestion).getPoint());
                        answer = myList.get(playedQuestion).getAnswer();
                        JSONArray jArray = myList.get(playedQuestion).getChoices();

                        btnA.setText( jArray.get(0)+"");
                        btnB.setText( jArray.get(1)+"");
                        btnC.setText(jArray.get(2)+"");
                        btnD.setText(jArray.get(3)+"");


                    } else {

                        Log.e("PrizesFragment", obj.optString("ResultMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

    CustomTaskFinishedListener responseWatch = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
//                        FragmentTransaction transac = getActivity().getSupportFragmentManager().beginTransaction();
//                        transac.replace(R.id.frame_layout, RewardVideoFragment.newInstance());
//                        transac.addToBackStack(null);
//                        transac.commit();

                    } else {

                        Log.e("PrizesFragment", obj.optString("ResultMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };



}
