package com.technoface.app.talentscam.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 6.12.2017.
 */

public class LegensDetailsFragment extends BaseFragment{

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private YouTubePlayer YPlayer;

    private MyPlaybackEventListener playbackEventListener;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private ImageButton btnBack;
    private TextView tvcontenttitle,tvcontentdetails;

    public static LegensDetailsFragment newInstance() {
        LegensDetailsFragment fragment = new LegensDetailsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootview= inflater.inflate(R.layout.fragment_legends_detail, container, false);
        btnBack = (ImageButton) rootview.findViewById(R.id.back_btn);
        tvcontenttitle = (TextView) rootview.findViewById(R.id.tvcontenttitle);
        tvcontentdetails = (TextView) rootview.findViewById(R.id.tvcontentdetails);
        tvcontenttitle.setText(AppController.getInstance().ContentTitle);
        tvcontentdetails.setText(AppController.getInstance().ContentDetail);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainMenuActivity.screenBackCall();
            }
        });

        ViewOnTouch.onTouchEffect(btnBack);


        return rootview;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);
        final YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
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
                    YPlayer.loadVideo(AppController.getInstance().youtubeLink);
                    YPlayer.play();
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

//            vState = "VIDEO_START";
            attemptLog();
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

    private void attemptLog(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.LogContentWatching(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId, AppController.getInstance().ContentId));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {


                    } else {
                        Log.e("LegendsFragment", obj.optString("ResultMessage"));
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
