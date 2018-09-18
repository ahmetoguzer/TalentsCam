package com.technoface.app.talentscam.Fragments;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.DropBoxClient;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.fabric.sdk.android.services.common.CommonUtils.md5;

/**
 * Created by Ahmet Oguzer on 27.10.2017.
 */

public class VideoSendFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "Video Send Fragment";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";


    private String videoname;
    public ProgressDialog progres = null;
    private File f;
    private DbxClientV2 client;
    private FileMetadata metadata;
    private boolean isUploaded = false;

    private ImageButton btnBack;
    private Button btnSend;
    VideoView videoView;
    MediaController mediaController;
     File videoFileName;
    private AVLoadingIndicatorView aviProgress;
    FFmpeg ffmpeg;
    private AdView mAdView;

    public static VideoSendFragment newInstance() {
        VideoSendFragment fragment = new VideoSendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_send_video, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        videoView = (VideoView)view.findViewById(R.id.surfaceView_preview);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        btnBack = (ImageButton) view.findViewById(R.id.btn_back);
        aviProgress = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        mAdView = view.findViewById(R.id.adView);


        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        ViewOnTouch.onTouchEffect(btnBack);
        ViewOnTouch.onTouchEffect(btnSend);

         mediaController = new MediaController(getActivity());

        videoname = getArguments().getString("url");

        MobileAds.initialize(getActivity(), "ca-app-pub-9612034478800875~1564320195");
        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send :
                new VideoUpload().execute();
                break;
            case R.id.btn_back :
                MainMenuActivity.screenBackCall();
                break;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onVideoRecordStop();
    }


    private class VideoUpload extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            progres = new ProgressDialog(getActivity());
            progres.setMessage("Yükleniyor");
            progres.show();
            super.onPreExecute();
            Log.d(TAG,"Start video compression");
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... params) {

            try (InputStream in = new FileInputStream(videoFileName)) {
                metadata = DropBoxClient.getClient(getActivity()).files().uploadBuilder("/Basketbul/"+videoFileName.getName())
                        .withMode(WriteMode.ADD)
                        .withClientModified(new Date(videoFileName.lastModified()))
                        .uploadAndFinish(in);

                if(!metadata.toStringMultiline().isEmpty()){
                    metadata.getName();
                    return true;
                }

            } catch (UploadErrorException ex) {
                System.err.println("Error uploading to Dropbox: " + ex.getMessage());

            } catch (DbxException ex) {
                System.err.println("Error uploading to Dropbox: " + ex.getMessage());

            } catch (IOException ex) {
                System.err.println("Error reading from file \"" + videoFileName+ "\": " + ex.getMessage());
            }

            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            progres.dismiss();
            if(result){
                deleteDirectory(videoFileName);

                attemptLogin();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ffmpeg.killRunningProcesses();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ffmpeg.killRunningProcesses();

    }

    public void deleteDirectory(File file) {
        if( file.exists() ) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
            file.delete();
        }
    }


    private void attemptLogin() {
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.AddShooting( Common.getUniqueID(getActivity()),AppController.getInstance().userId,
                AppController.getInstance().CompetitonId,AppController.getInstance().OpponentScore,
                "/Basketbul/"+metadata.getName() ,"0"));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, ThanksFragment.newInstance(),ThanksFragment.FRAGMENT_TAG);
                        transaction.disallowAddToBackStack();
                        transaction.commit();
                        MainMenuActivity.mStacks.get("Fragments").push(ThanksFragment.newInstance());
                    } else {
                        Log.e("Video Send Fragment", obj.optString("errorMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };


    public void onVideoRecordStop() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int s = height-width;
         ffmpeg = FFmpeg.getInstance(getContext());


        File dir = getContext().getExternalCacheDir();

         videoFileName = new File(dir, AppController.getInstance().userId + "-" + AppController.getInstance().CompetitonId + "-" +
                getCurrentTimeStamp() + "c.mp4");


        String[] cmd = {"-i", videoname, "-filter:v","crop=" + 480 + ":" + 480 ,"-threads","5","-preset","ultrafast","-strict","-2" ,"-c:a","copy", videoFileName.getPath()};
        try {
            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                    aviProgress.show();
                }

                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    f = new File(videoname);
                    deleteDirectory(f);
                    aviProgress.hide();
                    videoView.setVideoURI(Uri.parse(videoFileName.getPath()));
                    videoView.setMediaController(mediaController);
                    videoView.start();

                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            Log.e("", "");
        }
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(new Date());
    }


}
