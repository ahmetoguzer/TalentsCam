package com.technoface.app.talentscam.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Ahmet Oguzer on 30.10.2017.
 */

public class SmartCamSendVideoFragment extends Fragment implements VlcListener {

    public ProgressDialog progres = null;

    private DbxClientV2 client;
    private FileMetadata metadata;
    private boolean isUpload = false;
    private VlcVideoLibrary vlcVideoLibrary;
    private String qrcode,movLink;

    public static SmartCamSendVideoFragment newInstance() {
        SmartCamSendVideoFragment fragment = new SmartCamSendVideoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_smartcam_video_send, container, false);
        Button btnSend = (Button) rootView.findViewById(R.id.btn_send);

        SurfaceView surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView_vlc);
        vlcVideoLibrary = new VlcVideoLibrary(getActivity(), this, surfaceView);

        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        client = new DbxClientV2(config, "sxZIns8jgCgAAAAAAAABt9ZiPXdR8zILzsJR-EMdcI0lkV0GIaG2IvXdYq_wUHhM");

        qrcode = getArguments().getString("scanr");

        new RenderMovLink().execute();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              attemptLogin();
            }
        });

        return rootView;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }

    class RenderMovLink extends AsyncTask {

        @Override
        protected void onPreExecute() {
            progres = new ProgressDialog(getActivity());
            progres.setMessage("Video Başlatılıyor");
            progres.show();
            super.onPreExecute();
            Log.d(TAG,"Link Alınıyor");
        }


        @Override
        protected Object doInBackground(Object[] params) {
            try {
                movLink = client.files().getTemporaryLink("/Basketbul/"+qrcode+".mov").getLink();
            } catch (DbxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progres.dismiss();
            vlcVideoLibrary.play(movLink);
        }
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.AddShootingBySmartCam(Common.getUniqueID(getActivity()), AppController.getInstance().userId,
                AppController.getInstance().CompetitonId,AppController.getInstance().OpponentScore,
                "/Basketbul/"+qrcode+".mov" ,AppController.getInstance().SmartCamId,"0"));
    }


    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        isUpload=true;
                    } else {
                        Log.e(TAG, obj.optString("ResultMessage"));
                        Common.alert(getActivity(), "Hata", obj.optString("ResultMessage"));
                    }

                    if(isUpload){
                        AlertDialog alt = null;
                        alt = new AlertDialog.Builder(getActivity()).create();

                        alt.setTitle("");
                        alt.setMessage("Video Yüklendi");
                        alt.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        vlcVideoLibrary.stop();
                                        Intent intent = new Intent(getActivity(),
                                                MainMenuActivity.class);
                                        intent.putExtra("state","0");
                                        startActivity(intent);

                                        arg0.dismiss();
                                    }
                                });

                        alt.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
