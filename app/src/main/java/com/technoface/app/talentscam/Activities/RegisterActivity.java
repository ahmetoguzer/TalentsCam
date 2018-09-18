package com.technoface.app.talentscam.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Ahmet Oguzer on 26.10.2017.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private static int RESULT_LOAD_IMG =1000;

    private ImageView imgAddPhoto,imgPlusIkon;
    private ImageButton btnBack;
    private EditText etName,etEmail,etUsername,etPassword,etAge,etGender;
    private Button btnGenerate;
    private CheckBox checkBoxErkek,checkBoxKadın;
    private String gender;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    int lenght = etPassword.getText().length();
                    if(lenght<6){
                        Common.alert(RegisterActivity.this,"Şifre hakkında : ","Şifreniz Minimum 6 karakter olmalıdır.");
                        etPassword.setText("");
                    }
                }
            }
        });

    }

    private void initView(){
        imgAddPhoto = (ImageView) findViewById(R.id.img_add_photo);
        imgPlusIkon = (ImageView) findViewById(R.id.img_plus_ikon);
        btnBack = (ImageButton) findViewById(R.id.btn_back);

        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etUsername = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        etAge = (EditText) findViewById(R.id.et_birthday);

        checkBoxErkek = (CheckBox) findViewById(R.id.checkbox_erkek);
        checkBoxKadın = (CheckBox) findViewById(R.id.checkbox_kadin);
        checkBoxErkek.setOnClickListener(this);
        checkBoxKadın.setOnClickListener(this);

        btnGenerate = (Button) findViewById(R.id.btn_generate);

        btnBack.setOnClickListener(this);
        imgAddPhoto.setOnClickListener(this);
        btnGenerate.setOnClickListener(this);

        ViewOnTouch.onTouchEffect(btnBack);
        ViewOnTouch.onTouchEffect(btnGenerate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add_photo:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
            case R.id.btn_back:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                break;
            case R.id.btn_generate:
                if(isNotEmpty(etName) && isNotEmpty(etAge) && isNotEmpty(etEmail) && isNotEmpty(etUsername) && isNotEmpty(etPassword) && gender.length()!=0)
                    attemptLogin();
                else
                    Common.alert(RegisterActivity.this,"Uyarı","Lütfen Gerekli Alanları Doldurunuz");
                break;
            case R.id.checkbox_erkek:
                checkBoxKadın.setChecked(false);
                gender = "E";
                break;
            case R.id.checkbox_kadin:
                checkBoxErkek.setChecked(false);
                gender = "K";
                break;
            default:
                break;
        }
    }

    private boolean isNotEmpty(EditText etText) {
        if(etText.getText().toString().trim().length() != 0 || etText.getText().equals(null))
            return true;
        else
            return false;
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();

                AppController.getInstance().photoFilePath = getRealPathFromURI(RegisterActivity.this,imageUri);
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgAddPhoto.setImageBitmap(selectedImage);
                imgPlusIkon.setVisibility(View.GONE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Lütfen Tekrar Deneyiniz", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(RegisterActivity.this, "Lütfen Tekrar Deneyiniz",Toast.LENGTH_LONG).show();
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void customAlert(final Context context, String title, String msg) {
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
        CustomTask customLoginController = new CustomTask(getApplicationContext(), response,true);
        customLoginController.execute(ServiceURLCreator.CreateNewUser(Common.getUniqueID(RegisterActivity.this),
                Common.convertUTF8(etEmail.getText().toString()),
                Common.convertUTF8(etName.getText().toString()),
                Common.convertUTF8(etUsername.getText().toString()),
                Common.convertUTF8(etPassword.getText().toString()),
                etAge.getText().toString(),
                gender));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        customAlert(RegisterActivity.this, "BİLGİ", obj.optString("ResultMessage"));

                    } else {
                        Common.alert(RegisterActivity.this, "UYARI", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
              Common.alert(RegisterActivity.this, "UYARI :", "Bilgilerinizi tam doldurduğunuza emin misiniz? \n-Fotoğraf , kullanıcı adı ,şifre ...");
            }

        }
    };

}
