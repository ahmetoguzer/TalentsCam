package com.technoface.app.talentscam.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Activities.RegisterActivity;
import com.technoface.app.talentscam.BuildConfig;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ahmet Oguzer on 26.10.2017.
 */

public class ProfileFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ImageView imgProfil,imgPlusIkon;
    private TextView name,puan;
    private ImageButton btnBack;
    private Button btnChangePassword,btnUpdate;
    private EditText etName,etEmail,etUsername,etPassword,etAge;
    private CheckBox checkBoxErkek,checkBoxKadın;
    private String gender;
    private static int RESULT_LOAD_IMG =1000;
    private boolean isUploadImage=false;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        init(rootView);

        return rootView;
    }

    private void init(View rootView){
        imgProfil = (ImageView)rootView.findViewById(R.id.person_photo);
        name = (TextView) rootView.findViewById(R.id.txt_profilname);
        puan = (TextView) rootView.findViewById(R.id.tv_puan);
        btnChangePassword = (Button) rootView.findViewById(R.id.btn_password_change);
        imgProfil = (ImageView)rootView.findViewById(R.id.person_photo);
        name = (TextView) rootView.findViewById(R.id.txt_profilname);
        btnBack = (ImageButton) rootView.findViewById(R.id.btn_back);
        btnUpdate = (Button)  rootView.findViewById(R.id.btn_update);
        imgPlusIkon = (ImageView) rootView.findViewById(R.id.img_plus_ikon);

        etName = (EditText) rootView.findViewById(R.id.et_kullanıcı_Adı);
        etEmail = (EditText) rootView.findViewById(R.id.et_email);
        etAge = (EditText) rootView.findViewById(R.id.et_yas);

        checkBoxErkek = (CheckBox) rootView.findViewById(R.id.checkbox_erkek);
        checkBoxKadın = (CheckBox) rootView.findViewById(R.id.checkbox_kadin);
        checkBoxErkek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxKadın.setChecked(false);
                gender = "E";
            }
        });
        checkBoxKadın.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxErkek.setChecked(false);
                gender = "K";
            }
        });

        imgPlusIkon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });


        etName.setText(AppController.getInstance().profilName);
        etEmail.setText(AppController.getInstance().profilEmail);
        etAge.setText(AppController.getInstance().profilAge);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.screenBackCall();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ChangePasswordFragment.newInstance(),ChangePasswordFragment.FRAGMENT_TAG);
                transaction.disallowAddToBackStack();
                transaction.commit();
                MainMenuActivity.mStacks.get("Fragments").push(ChangePasswordFragment.newInstance());
                MainMenuActivity.selectedFragment = ChangePasswordFragment.newInstance();
                MainMenuActivity.setTitle();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        ViewOnTouch.onTouchEffect(btnUpdate);
        ViewOnTouch.onTouchEffect(btnBack);
        ViewOnTouch.onTouchEffect(btnChangePassword);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(50*1024*2014)
                .discCacheSize(50*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(String.valueOf(AppController.getInstance().profilImageUrl), imgProfil, options);
        name.setText(AppController.getInstance().profilName);
        puan.setText(AppController.getInstance().userPuan);

        //        etGender.setText(AppController.getInstance().profilGender);

        if(AppController.getInstance().profilGender.equals("E")){
            gender = "E";
            checkBoxKadın.setChecked(false);
            checkBoxErkek.setChecked(true);
        }
        if(AppController.getInstance().profilGender.equals("K")){
            gender = "K";
            checkBoxKadın.setChecked(true);
            checkBoxErkek.setChecked(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                isUploadImage = true;
                final Uri imageUri = data.getData();

                AppController.getInstance().photoFilePath = getRealPathFromURI(getContext(),imageUri);
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imgProfil.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lütfen Tekrar Deneyiniz", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(), "Lütfen Tekrar Deneyiniz",Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);
    }

    private void attemptLogin(){

        if(isUploadImage){
            CustomTask customLoginController = new CustomTask(getActivity(), response,true);
            customLoginController.execute(ServiceURLCreator.UpdateMyProfile(Common.getUniqueID(getActivity()),
                    AppController.getInstance().userId,
                    Common.convertUTF8(etEmail.getText().toString()),
                    Common.convertUTF8(etName.getText().toString()),
                    etAge.getText().toString(),
                    gender)
            );
        }

    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        Common.alert(getActivity(), "Bilgi", obj.optString("ResultMessage"));
                        AppController.getInstance().profilEmail = etEmail.getText().toString();
                        AppController.getInstance().profilAge = etAge.getText().toString();
                        AppController.getInstance().profilName = etName.getText().toString();
                        AppController.getInstance().profilGender = gender;
                    } else {
//                        Log.e(TAG, obj.optString("ResultMessage"));
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
