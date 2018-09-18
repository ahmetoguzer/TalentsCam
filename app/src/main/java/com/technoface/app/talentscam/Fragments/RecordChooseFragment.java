package com.technoface.app.talentscam.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.ViewOnTouch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;

import static android.app.Activity.RESULT_OK;
import static com.technoface.app.talentscam.Activities.MainMenuActivity.mRewardedVideoAd;

/**
 * Created by Ahmet Oguzer on 25.10.2017.
 */

public class RecordChooseFragment extends BaseFragment implements  View.OnClickListener {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private static final int CAPTURE_MEDIA = 368;
    private static final int RECORD_VIDEO_REQUEST = 1000;

    private ImageButton btnOpenCam;
    private RelativeLayout btnKendinCek, btnSmartCam,btnIzleKazan;
    private String state;
    private Map<String, String> userMap;
    public ArrayList<String> vidPaths, uriarry;

    public static RecordChooseFragment newInstance() {
        RecordChooseFragment fragment = new RecordChooseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_capture_video, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View rootView) {
        btnKendinCek = (RelativeLayout) rootView.findViewById(R.id.layout_kendincek);
        btnSmartCam = (RelativeLayout) rootView.findViewById(R.id.layout_smartcam);
        btnIzleKazan = (RelativeLayout) rootView.findViewById(R.id.layout_izlekazan);
        btnOpenCam = (ImageButton) rootView.findViewById(R.id.btnOpenCams);
//        btnBack.setOnClickListener(this);
        btnKendinCek.setOnClickListener(this);
        btnIzleKazan.setOnClickListener(this);
        btnSmartCam.setOnClickListener(this);
        btnOpenCam.setOnClickListener(this);


        vidPaths = new ArrayList();
        uriarry = new ArrayList<>();
        userMap = new HashMap<String, String>();

        ViewOnTouch.onTouchEffect(btnKendinCek);
        ViewOnTouch.onTouchEffect(btnIzleKazan);
        ViewOnTouch.onTouchEffect(btnOpenCam);
        ViewOnTouch.onTouchEffect(btnSmartCam);

        state = getArguments().getString("state");

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
//                if (state.equals("1")) {
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_layout, GeneralPlayersFragment.newInstance());
//                    transaction.commit();
//                } else {
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_layout, DetailsFragment.newInstance());
//                    transaction.commit();
//                }

                MainMenuActivity.screenBackCall();

                break;
            case R.id.layout_kendincek:
                int n = (int) (Math.random() * Integer.MAX_VALUE);

                File dir = getContext().getExternalCacheDir();

                File mThumbnailFile = new File(dir,
                        AppController.getInstance().userId + "-" + AppController.getInstance().CompetitonId + "-" +
                                getCurrentTimeStamp() + ".jpg");

                File videoFileName = new File(dir, AppController.getInstance().userId + "-" + AppController.getInstance().CompetitonId + "-" +
                        getCurrentTimeStamp() + ".mp4");

                Uri outputFileUri = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        videoFileName);

                AppController.getInstance().outputfilename = String.valueOf(outputFileUri.getPath());

                startActivity(videoFileName, mThumbnailFile);
                break;
            case R.id.layout_smartcam:
                MainMenuActivity.mStacks.get("Fragments").push(TahminEtFragment.newInstance());
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.frame_layout, TahminEtFragment.newInstance());
                transaction1.disallowAddToBackStack();
                transaction1.commit();
                MainMenuActivity.setTitle();
                break;
            case R.id.btnOpenCams:
                Bundle bund = new Bundle();
                bund.putString("state", "1");
                Fragment frg = NearbyCamFragment.newInstance();
                frg.setArguments(bund);
                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.frame_layout, frg);
                transaction2.disallowAddToBackStack();
                transaction2.commit();

                break;
            case R.id.layout_izlekazan:

                MainMenuActivity.mStacks.get("Fragments").push(RewardVideoFragment.newInstance());
                FragmentTransaction transac = getActivity().getSupportFragmentManager().beginTransaction();
                transac.replace(R.id.frame_layout, RewardVideoFragment.newInstance());
                transac.disallowAddToBackStack();
                transac.commit();
                MainMenuActivity.setTitle();
                break;
        }

    }

    public void startActivity(File videoFile, File thumbnailFile) {
        AnncaConfiguration.Builder videoLimited = new AnncaConfiguration.Builder(getActivity(), CAPTURE_MEDIA);
        videoLimited.setMediaAction(AnncaConfiguration.MEDIA_ACTION_VIDEO);
        videoLimited.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_LOW);
        videoLimited.setVideoFileSize(20 * 1024 * 1024);
        videoLimited.setVideoDuration(60000);
        videoLimited.setMinimumVideoDuration(5 * 60 * 1000);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        new Annca(videoLimited.build()).launchCamera();

//        startActivity(new Intent(getActivity(), SquareCameraActivity.class));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case RECORD_VIDEO_REQUEST:
                switch (resultCode) {
                    case RESULT_OK:
                        Uri videoUri = data.getData();
//
                        Bundle bundle = new Bundle();
                        bundle.putString("url", String.valueOf(videoUri));
                        Fragment fragment = VideoSendFragment.newInstance();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, fragment);
                        transaction.commit();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;

                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(new Date());
    }

}
