package com.technoface.app.talentscam.Fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.MeydanOkumaModel;
import com.technoface.app.talentscam.Model.TahminModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.Vo.TahminVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static io.fabric.sdk.android.services.common.CommonUtils.md5;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class TahminEtFragment extends BaseFragment{

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private YouTubePlayer YPlayer;

    private MyPlaybackEventListener playbackEventListener;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private ImageButton btnBack;
    private TextView tvcontenttitle,tvWrong,tvRight,tvDescription;
    private String answer,myAnswer;
    private CountDownTimer t;
    private long maxTimeInMilliseconds;
    private ArrayList<TahminVo> myList;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private ImageButton btnWrong,btnRight;
    private int playedVideo=0;
    private AdView mAdView;

    public static TahminEtFragment newInstance() {
        TahminEtFragment fragment = new TahminEtFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview= inflater.inflate(R.layout.fragment_tahmin_et, container, false);
        btnBack = (ImageButton) rootview.findViewById(R.id.back_btn);
        tvcontenttitle = (TextView) rootview.findViewById(R.id.tvcontenttitle);
        tvWrong = (TextView) rootview.findViewById(R.id.tvWrong);
        tvRight = (TextView) rootview.findViewById(R.id.tvRight);
        tvRight = (TextView) rootview.findViewById(R.id.tvRight);
        tvDescription = (TextView) rootview.findViewById(R.id.tvDescription);
        btnRight = (ImageButton) rootview.findViewById(R.id.btnRight);
        btnWrong = (ImageButton) rootview.findViewById(R.id.btnWrong);
        mAdView = rootview.findViewById(R.id.adView);

        myList = new ArrayList<>();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.screenBackCall();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YPlayer.play();
                myAnswer="1";
                if(!(myList.size()-1<playedVideo)){
                    if(myList.get(playedVideo).getAnswer().equals(myAnswer)){
                        answer="1";
                        btnRight.setVisibility(View.GONE);
                        tvRight.setVisibility(View.VISIBLE);
                        tvRight.getBackground().setColorFilter(getResources().getColor(R.color.circular_back),
                                PorterDuff.Mode.SRC_ATOP);
                        tvRight.setTextColor(getResources().getColor(R.color.circular_back));
                        tvRight.setText("1 BC");
                        animate(tvRight);
                        attemptAnswer();
                    }else{
                        answer="0";
                        btnRight.setVisibility(View.GONE);
                        tvRight.setVisibility(View.VISIBLE);
                        tvRight.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                                PorterDuff.Mode.SRC_ATOP);
                        tvRight.setTextColor(getResources().getColor(R.color.colorRed));
                        tvRight.setText("0 BC");
                        animate(tvRight);
                        attemptAnswer();
                    }
                    t = new CountDownTimer(5*1000, 1000) {

                        public void onTick(long millisUntilFinished)
                        {
                            long remainedSecs = millisUntilFinished/1000;
                        }

                        public void onFinish()
                        {    tvRight.setVisibility(View.GONE);
                            tvDescription.setVisibility(View.VISIBLE);
                            btnRight.setVisibility(View.GONE);
                            btnWrong.setVisibility(View.GONE);
                            playedVideo++;
                            if(!(myList.size()-1<playedVideo)){
                                maxTimeInMilliseconds = Integer.valueOf(myList.get(playedVideo).getStopSecond())
                                        -Integer.valueOf(myList.get(playedVideo).getStartSecond());
                                tvcontenttitle.setText(myList.get(playedVideo).getGuessTitle());
                                YPlayer.loadVideo(myList.get(playedVideo).getGuessVideo());
                                YPlayer.play();
                            }
                        }
                    }.start();
                }else{
                    Common.alert(getActivity(),"","There is no more videos");
                    MainMenuActivity.screenBackCall();
                }



            }
        });
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YPlayer.play();
                myAnswer="0";
                if(!(myList.size()-1<playedVideo)){
                    if(myList.get(playedVideo).getAnswer().equals(myAnswer)){
                        answer="1";
                        btnWrong.setVisibility(View.GONE);
                        tvWrong.setVisibility(View.VISIBLE);
                        tvWrong.setText("1 BC");
                        tvWrong.getBackground().setColorFilter(getResources().getColor(R.color.circular_back),
                                PorterDuff.Mode.SRC_ATOP);
                        tvWrong.setTextColor(getResources().getColor(R.color.circular_back));
                        animate(tvWrong);
                        attemptAnswer();
                    }else{
                        answer="0";
                        btnWrong.setVisibility(View.GONE);
                        tvWrong.setVisibility(View.VISIBLE);
                        tvWrong.getBackground().setColorFilter(getResources().getColor(R.color.colorRed),
                                PorterDuff.Mode.SRC_ATOP);
                        tvWrong.setText("0 BC");
                        tvWrong.setTextColor(getResources().getColor(R.color.colorRed));
                        animate(tvWrong);
                        attemptAnswer();
                    }

                    t = new CountDownTimer(5*1000, 1000) {

                        public void onTick(long millisUntilFinished)
                        {
                            long remainedSecs = millisUntilFinished/1000;
                        }

                        public void onFinish()
                        {
                            tvWrong.setVisibility(View.GONE);
                            tvDescription.setVisibility(View.VISIBLE);
                            btnRight.setVisibility(View.GONE);
                            btnWrong.setVisibility(View.GONE);
                            playedVideo++;
                            if(!(myList.size()-1<playedVideo)){
                                maxTimeInMilliseconds = Integer.valueOf(myList.get(playedVideo).getStopSecond())
                                        -Integer.valueOf(myList.get(playedVideo).getStartSecond());
                                tvcontenttitle.setText(myList.get(playedVideo).getGuessTitle());
                                YPlayer.loadVideo(myList.get(playedVideo).getGuessVideo());
                                YPlayer.play();
                            }
                        }
                    }.start();
                }else{
                    Common.alert(getActivity(),"","There is no more videos");
                    MainMenuActivity.screenBackCall();
                }


            }
        });

        MobileAds.initialize(getActivity(), "ca-app-pub-9612034478800875~1564320195");
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

        ViewOnTouch.onTouchEffect(btnBack);
        ViewOnTouch.onTouchEffect(btnWrong);
        ViewOnTouch.onTouchEffect(btnRight);

        return rootview;
    }

    public void animate (View view) {
        ScaleAnimation mAnimation = new ScaleAnimation(1f,1.3f,1f,1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.45f);
        mAnimation.setDuration(500);
        mAnimation.setRepeatCount(8);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setAnimation(mAnimation);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_view, youTubePlayerFragment).commit();
        playbackEventListener = new MyPlaybackEventListener();
        playerStateChangeListener = new MyPlayerStateChangeListener();
        youTubePlayerFragment.initialize("AIzaSyBSKjTHCb9dWm5M1WYVEAa19Mk3I84Pjs4", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    YPlayer = player;
                    YPlayer.setPlaybackEventListener(playbackEventListener);
                    YPlayer.setPlayerStateChangeListener(playerStateChangeListener);
                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                    attemptLogin();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       t.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().

        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
//            vState = "VIDEO_END";
//            attemptLog();

        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.

        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {

            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }
    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
            YPlayer.seekToMillis(Integer.valueOf(myList.get(playedVideo).getStartSecond())*1000);
            maxTimeInMilliseconds = maxTimeInMilliseconds+3;
            t = new CountDownTimer(maxTimeInMilliseconds*1000, 1000) {

                public void onTick(long millisUntilFinished)
                {
                    long remainedSecs = millisUntilFinished/1000;
                }

                public void onFinish()
                {
                    YPlayer.pause();
                    tvDescription.setVisibility(View.GONE);
                    btnRight.setVisibility(View.VISIBLE);
                    btnWrong.setVisibility(View.VISIBLE);
                }
            }.start();
//            vState = "VIDEO_START";

        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetGuessQuestions(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId));
    }

    private void attemptAnswer(){
        CustomTask customLoginController = new CustomTask(getActivity(), responseAnswer);
        customLoginController.execute(ServiceURLCreator.AnswerGuessQuestion(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId,myList.get(playedVideo).getGuessQuestionId(),answer));
    }

    CustomTaskFinishedListener responseAnswer = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {


                    } else {
                        Log.e("TahminetFragment", obj.optString("ResultMessage"));
                        Common.alert(getActivity(), "Uyarı", obj.optString("ResultMessage"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = obj.getJSONArray("ResultObjects");

                        TahminModel model = new TahminModel();
                        myList = model.getTahmin(jsonArray);
                        tvcontenttitle.setText(myList.get(playedVideo).getGuessTitle());
                        maxTimeInMilliseconds = Integer.valueOf(myList.get(playedVideo).getStopSecond())
                                -Integer.valueOf(myList.get(playedVideo).getStartSecond());
                        YPlayer.loadVideo(myList.get(playedVideo).getGuessVideo());
                        YPlayer.play();


                    } else {
                        Log.e("TahminetFragment", obj.optString("ResultMessage"));
                        Common.alert(getActivity(), "Uyarı", obj.optString("ResultMessage"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

}

