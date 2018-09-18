package com.technoface.app.talentscam.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


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

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {


    private ImageButton btnBack;
    private EditText etMail;
    private Button btnSend;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();

    }

    private void initView(){
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        etMail = (EditText) findViewById(R.id.et_mail);
        btnSend = (Button)  findViewById(R.id.btn_send);
        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        ViewOnTouch.onTouchEffect(btnBack);
        ViewOnTouch.onTouchEffect(btnSend);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
            case R.id.btn_send:
                attemptLogin();
            break;
        }
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
                        startActivity(new Intent(context,LoginActivity.class));
                        arg0.dismiss();

                    }
                });

        alt.show();
    }



    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getApplicationContext(), response);
        customLoginController.execute(ServiceURLCreator.ForgotMyPassword(Common.getUniqueID(ForgotPasswordActivity.this),
                Common.convertUTF8(etMail.getText().toString())));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        alert(ForgotPasswordActivity.this, "Bilgi", obj.optString("ResultMessage"));
                    } else {
//                        Log.e(TAG, obj.optString("ResultMessage"));
                        Common.alert(ForgotPasswordActivity.this, "Uyarı", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }

        }
    };
}
