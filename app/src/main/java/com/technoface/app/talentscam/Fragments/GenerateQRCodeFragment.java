package com.technoface.app.talentscam.Fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import me.ydcool.lib.qrmodule.encoding.QrGenerator;

/**
 * Created by Ahmet on 2.10.2017.
 */

public class GenerateQRCodeFragment extends Fragment implements MainMenuActivity.OnBackPressedListener {

    private String qrCodeString;
    private ImageView myImage;
    private String status;
    private ImageButton btnBack;
    private MainMenuActivity mActivity;

    public static GenerateQRCodeFragment newInstance() {
        GenerateQRCodeFragment fragment = new GenerateQRCodeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_qrcode, container, false);
        ((MainMenuActivity) getActivity()).setOnBackPressedListener(this);

        myImage = (ImageView) rootView.findViewById(R.id.img_qrcode);
        btnBack = (ImageButton) rootView.findViewById(R.id.btn_back);

        mActivity = new MainMenuActivity();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("state", "0");
                Fragment fragment = RecordChooseFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction1.replace(R.id.frame_layout, fragment);
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });
        attemptLogin();
//
//        CountDownTimer t = new CountDownTimer(10000, 1000) {
//
//            public void onTick(long millisUntilFinished)
//            {
//
//            }
//
//            public void onFinish()
//            {
////                attemptLoginCheck();
//                cancel();
//            }
//        }.start();


        return rootView;
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GenerateQrCode( AppController.getInstance().userId,
                Common.getUniqueID(getActivity())));
    }

    private void attemptLoginCheck(){
        CustomTask customLoginController = new CustomTask(getActivity(), responseCheck);
        customLoginController.execute(ServiceURLCreator.GetCheckQrcodeStatus( AppController.getInstance().userId,qrCodeString
               ));
    }

    CustomTaskFinishedListener responseCheck = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jarry = obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(jarry.get(0).toString());
                        status = objs.optString("QrCodeStatus");
                        AppController.getInstance().SmartCamId = objs.optString("SmartCamId");

                    } else {
//                        Log.e("", obj.optString("Re"));
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout, RecordChooseFragment.newInstance());
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                    if(status.equals("2")){
                        Bundle bundle = new Bundle();
                        bundle.putString("scanr",qrCodeString);
                        Fragment fragment = CountdownFragment.newInstance();
                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else if(status.equals("1")){

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
                        qrCodeString = obj.optString("ResultObjects");

                    } else {
                        Log.e("Login activity", obj.optString("errorMessage"));
                    }

                    Bitmap qrCode = new QrGenerator.Builder()
                            .content(qrCodeString)
                            .qrSize(300)
                            .margin(2)
                            .color(Color.BLACK)
                            .bgColor(Color.WHITE)
                            .ecc(ErrorCorrectionLevel.H)
                            .encode();

                    myImage.setImageBitmap(qrCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }

        }
    };


    @Override
    public void doBack() {
        Bundle bundle = new Bundle();
        bundle.putString("state", "0");
        Fragment fragment = RecordChooseFragment.newInstance();
        fragment.setArguments(bundle);
        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction1.replace(R.id.frame_layout, fragment);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }
}
