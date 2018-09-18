package com.technoface.app.talentscam.Activities;



import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Message;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "Login_Actvity";

    private Button btnLoginFacebook,btnLogin,btnRegister,btnForgotPassword;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;

    private Profile profile;
    private int pos=0;
    private EditText username,password;
    private MySharedpreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();


        if (AccessToken.getCurrentAccessToken() != null) {
            updateWithToken(AccessToken.getCurrentAccessToken());
        }

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("FaceLog", "Success");
            }

            @Override
            public void onCancel() {
                Log.i("FaceLog", "Cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i("FaceLog", "Error");
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                updateWithToken(currentAccessToken);
            }
        };

    }


    private void initView(){
        mySharedPreferences= MySharedpreferences.getInstance(LoginActivity.this);
        btnLoginFacebook = (Button) findViewById(R.id.btn_facebook_login);
        btnLogin= (Button) findViewById(R.id.btn_login);
        username = (EditText) findViewById(R.id.et_kullanıcı_Adı);
        password = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnForgotPassword = (Button) findViewById(R.id.btn_forgot_password);
        btnForgotPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginFacebook.setOnClickListener(this);

        ViewOnTouch.onTouchEffect(btnForgotPassword);
        ViewOnTouch.onTouchEffect(btnLogin);
        ViewOnTouch.onTouchEffect(btnLoginFacebook);
        ViewOnTouch.onTouchEffect(btnRegister);

        if(!isNetworkConnected()){

            AlertDialog alt = null;

            alt = new AlertDialog.Builder(LoginActivity.this).create();

            alt.setTitle("Uyarı");
            alt.setMessage(getResources().getString(R.string.control_connecting_internet));
            alt.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent=new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                            arg0.dismiss();
                        }
                    });

            alt.show();

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            Log.i("FaceLog", "Already Logged.");

            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    Log.i("FaceLog", jsonObject.toString());
                    profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        AppController.getInstance().profileID = profile.getId();
//                        AppController.getInstance().profilImageUrl = profile.getProfilePictureUri(200,200);
                        AppController.getInstance().profilImageUrl= Uri.parse("https://graph.facebook.com/"+
                                AppController.getInstance().profileID
                                +"/picture?type=large");
                        AppController.getInstance().profilName = profile.getName();
                        AppController.getInstance().profilLink = profile.getLinkUri();
                        AppController.getInstance().profileID = profile.getId();
                        try {
                            AppController.getInstance().profilGender = jsonObject.getString("gender");
                            AppController.getInstance().profilEmail = jsonObject.getString("email");
                            AppController.getInstance().profilLocale = jsonObject.getString("locale");
                            AppController.getInstance().profilTimezone = jsonObject.getString("timezone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse graphResponse) {
                            Log.i("FaceLog", graphResponse.getJSONObject().toString());
                            try {
                                AppController.getInstance().profilFriendsCount = graphResponse.getJSONObject().getJSONObject("summary").getString("total_count");
                                JSONArray jsonArray  = graphResponse.getJSONObject().getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    if (i == 0) {
                                        AppController.getInstance().profilFriends = "'"+json.optString("id")+"'";
                                        mySharedPreferences.saveData("user_FacebookFriens","'"+json.optString("id")+"'");
                                    }else {
                                        AppController.getInstance().profilFriends= AppController.getInstance().profilFriends+","+"'"+json.optString("id")+"'";
                                        mySharedPreferences.saveData("user_FacebookFriens",AppController.getInstance().profilFriends+","+"'"+json.optString("id")+"'");
                                    }

                                    MeydanOkumaVo vo = new MeydanOkumaVo();
                                    vo.setCompetitionId(json.optString("CompetitionId"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            attemptLoginFacebook();

                            finish();
                        }
                    }).executeAsync();
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,locale,timezone");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();


        } else {
            Log.i("FaceLog", "Not Logged.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String name = username.getText().toString();
                if(name.equals("")){
                    Common.alert(LoginActivity.this,"UYARI","Lütfen Gerekli Bilgileri Giriniz");
                }else{
                    mySharedPreferences.saveData("user_name",name);
                    mySharedPreferences.saveData("password", password.getText().toString());
                    attemptLogin();
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.btn_forgot_password:
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
            case R.id.btn_facebook_login:
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email","user_birthday"));
                break;
            default:
                break;
        }
    }


    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getApplicationContext(), response);
        customLoginController.execute(ServiceURLCreator.LoginWithPassword(Common.getUniqueID(LoginActivity.this),
                Common.convertUTF8(  username.getText().toString()),
                Common.convertUTF8(  password.getText().toString())));
    }

    private void attemptLoginFacebook() {
        CustomTask customLoginController = new CustomTask(getApplicationContext(), responseLogin);
        customLoginController.execute(ServiceURLCreator.LoginWithFacebook(
                AppController.getInstance().profileID,
                Common.convertUTF8(AppController.getInstance().profilName),
                Common.convertUTF8(String.valueOf(AppController.getInstance().profilImageUrl))
                ,Common.getUniqueID(LoginActivity.this),AppController.getInstance().profileID+"@gmail.com","25")
        );

    }

    CustomTaskFinishedListener responseLogin = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = new JSONArray(obj.optString("ResultObjects"));
                        JSONObject json = jsonArray.getJSONObject(0);
                        mySharedPreferences.saveData("userid",json.optString("UserId"));
                        mySharedPreferences.saveData("user_Email", json.optString("UserEmail"));
                        mySharedPreferences.saveData("user_name", json.optString("UserName"));
                        mySharedPreferences.saveData("user_Puan", json.optString("Puan"));
                        mySharedPreferences.saveData("user_avatarUrl", json.optString("UserAvatarUrl"));
                        mySharedPreferences.saveData("user_age", json.optString("UserAge"));
                        mySharedPreferences.saveData("user_Gender", json.optString("UserGender"));

                        AppController.getInstance().profilEmail=json.optString("UserEmail");
                        AppController.getInstance().userId=json.optString("UserId");
                        AppController.getInstance().userPuan=json.optString("Puan");
                        AppController.getInstance().userLevel=json.optString("PuanLevel");
                        AppController.getInstance().profilAge = json.optString("UserAge");
                        AppController.getInstance().profilGender = json.optString("UserGender");
                        if(json.optString("IsAdmin").equals("1")){
                            startActivity(new Intent(getApplicationContext(), GrupActivity.class));
                        }else{
                            Intent intent = new Intent(getApplicationContext(), MainMenuTutorialsActivity.class);
                            intent.putExtra("state","0");
                            startActivity(intent);
                        }
                    } else {
                        Log.e("Login activity", obj.optString("errorMessage"));
                        Common.alert(LoginActivity.this, "HATA", obj.optString("ResultMessage"));
                    }
                }
//                 attemptCompletedCompetition();
            } catch (Exception e) {
                e.printStackTrace();
                Common.alert(LoginActivity.this, "HATA", "Lütfen bilgileninizi kontrol edin");
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
                        JSONArray jsonArray = new JSONArray(obj.optString("ResultObjects"));
                        JSONObject json = jsonArray.getJSONObject(0);
                        mySharedPreferences.saveData("userid",json.optString("UserId"));
                        mySharedPreferences.saveData("user_Email", json.optString("UserEmail"));
                        mySharedPreferences.saveData("user_name", json.optString("UserName"));
                        mySharedPreferences.saveData("user_Puan", json.optString("Puan"));
                        mySharedPreferences.saveData("user_avatarUrl", json.optString("UserAvatarUrl"));
                        mySharedPreferences.saveData("user_age", json.optString("UserAge"));
                        mySharedPreferences.saveData("user_Gender", json.optString("UserGender"));

                        AppController.getInstance().profilEmail=json.optString("UserEmail");
                        AppController.getInstance().profilName=json.optString("UserName");
                        AppController.getInstance().profilAge=json.optString("UserAge");
                        AppController.getInstance().userId=json.optString("UserId");
                        AppController.getInstance().userPuan=json.optString("Puan");
                        AppController.getInstance().userLevel=json.optString("PuanLevel");
                        AppController.getInstance().profilImageUrl= Uri.parse(json.optString("UserAvatarUrl"));
                        AppController.getInstance().profilGender = json.optString("UserGender");

                        if(json.optString("IsAdmin").equals("1")){
                            startActivity(new Intent(getApplicationContext(), GrupActivity.class));
                        }else{
                            Intent intent = new Intent(getApplicationContext(), MainMenuTutorialsActivity.class);
                            intent.putExtra("state","0");
                            startActivity(intent);
                        }

                    } else {
                        Log.e(TAG, obj.optString("ResultMessage"));
                        Common.alert(LoginActivity.this, "HATA", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Common.alert(LoginActivity.this, "HATA", "Lütfen bilgileninizi kontrol edin");
            }

        }
    };



}