package com.technoface.app.talentscam.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONObject;

/**
 * Created by Ahmet Oguzer on 26.10.2017.
 */

public class ChangePasswordFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private EditText etAvailablePass,etNewPass,etNewPassAgain;
    private Button btnUpdate;
    private ImageButton btnbBack;

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        AppController.getInstance().currentFragment=ChangePasswordFragment.newInstance();
        return rootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View v){
        etAvailablePass = (EditText) v.findViewById(R.id.et_available_pass);
        etNewPass = (EditText) v.findViewById(R.id.et_new_password);
        etNewPassAgain = (EditText) v.findViewById(R.id.et_new_password_again);
        btnbBack = (ImageButton) v.findViewById(R.id.btn_back);

        btnUpdate = (Button) v.findViewById(R.id.btn_update);
        ViewOnTouch.onTouchEffect(btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNewPass.getText().toString().equals(etNewPassAgain.getText().toString()) && etNewPass.length() >= 6)
                   attemptLogin();
                else
                    Common.alert(getActivity(),"UYARI","Yeni şifreler eşleşmiyor.");
            }
        });

        btnbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainMenuActivity.screenBackCall();
            }
        });


        etNewPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                   int lenght =  etNewPass.getText().length();
                    if(lenght < 6 ){
                        Common.alert(getActivity(),"UYARI","Yeni Şifreniz minimum 6 karakter olmalıdır");
                        etNewPass.setText("");
                    }
                }
            }
        });
    }

    private void alert(final Context context, String title, String msg) {
        AlertDialog alt = null;

        alt = new AlertDialog.Builder(context).create();

        alt.setTitle(title);
        alt.setMessage(msg);
        alt.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        transaction1.replace(R.id.frame_layout, ProfileFragment.newInstance());
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        arg0.dismiss();

                    }
                });

        alt.show();
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.ChangeMyPassword(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId,
                Common.convertUTF8(etAvailablePass.getText().toString()),
                Common.convertUTF8(etNewPass.getText().toString())));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        alert(getActivity(), "Bilgi", obj.optString("ResultMessage"));
                    } else {
                        Common.alert(getActivity(), "Uyarı", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
              Common.alert(getActivity(), "Uyarı","Lütfen mevcut şifrenizi giriniz");
            }

        }
    };


}
