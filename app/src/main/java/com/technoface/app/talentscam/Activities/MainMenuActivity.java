package com.technoface.app.talentscam.Activities;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;

/**
 * Created by Ahmet on 25.5.2017.
 */

import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karan.churi.PermissionManager.PermissionManager;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Adapters.BestPlayersAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Fragments.BaseFragment;
import com.technoface.app.talentscam.Fragments.ChangePasswordFragment;
import com.technoface.app.talentscam.Fragments.ContentsFragment;
import com.technoface.app.talentscam.Fragments.DetailsFragment;
import com.technoface.app.talentscam.Fragments.FacebookFriendsFragment;
import com.technoface.app.talentscam.Fragments.FragmentHistoryDetails;
import com.technoface.app.talentscam.Fragments.GenerateQRCodeFragment;
import com.technoface.app.talentscam.Fragments.GroupPlayersFragment;
import com.technoface.app.talentscam.Fragments.HistoryFragment;
import com.technoface.app.talentscam.Fragments.LegensDetailsFragment;
import com.technoface.app.talentscam.Fragments.NearbyCamFragment;
import com.technoface.app.talentscam.Fragments.NearbyPlayerFragment;
import com.technoface.app.talentscam.Fragments.PrizesDetayFragment;
import com.technoface.app.talentscam.Fragments.PrizesFragment;
import com.technoface.app.talentscam.Fragments.ProfileFragment;
import com.technoface.app.talentscam.Fragments.RakingFragment;
import com.technoface.app.talentscam.Fragments.RecordChooseFragment;
import com.technoface.app.talentscam.Fragments.RewardVideoFragment;
import com.technoface.app.talentscam.Fragments.TahminEtFragment;
import com.technoface.app.talentscam.Fragments.ThanksFragment;
import com.technoface.app.talentscam.Fragments.VideoSendFragment;
import com.technoface.app.talentscam.helper.AppLocationService;
import com.technoface.app.talentscam.helper.BottomNavigationViewHelper;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Fragments.GeneralPlayersFragment;

import com.technoface.app.talentscam.Fragments.SettingsFragment;
import com.technoface.app.talentscam.Model.MeydanOkumaModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;

import static android.graphics.Color.*;


public class MainMenuActivity extends BaseActivity implements BaseFragment.BackHandlerInterface  {

    public static final String FRAGMENTS = "Fragments";

    private String  groupid,devicetoken;
    private boolean isOpenMontaj=false;
    public static RecyclerView rv;
    private static ArrayList<MeydanOkumaVo> myList;
    public static Fragment selectedFragment = null;


    private static final int CAPTURE_MEDIA = 368;

    private AppLocationService appLocationService;
    private double latitude;
    private double longitude;
    private Location location;
    private Uri outputFileUri;

    private static WeakReference<FragmentActivity> wrActivity = null;
    public static HashMap<String, Stack<Fragment>> mStacks;

    private static Context ctx;
    PermissionManager permission;
    private boolean ffmpegSupported = true;
    Fragment manager;

    private static final int RECORD_VIDEO_REQUEST = 1000;
    protected OnBackPressedListener onBackPressedListener;
    public static RewardedVideoAd mRewardedVideoAd;
    static Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
//    private static ImageButton mBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        wrActivity = new WeakReference<FragmentActivity>(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mBack = findViewById(R.id.back_btn);

        init();
        appLocationService = new AppLocationService(MainMenuActivity.this);
        location = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }else{
            Common.alert(MainMenuActivity.this,"Uyarı",getResources().getString(R.string.location_error));
        }
        permission=new PermissionManager() {};
        permission.checkAndRequestPermissions(this);


        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                    ffmpegSupported = false;
                }

                @Override
                public void onSuccess() {
                    ffmpegSupported = true;
                }

                @Override
                public void onFinish() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            ffmpegSupported = false;
        }





    }

    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale){
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
            getApplicationContext().createConfigurationContext(configuration);
        }
        else{
            configuration.locale=locale;
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }

    public interface OnBackPressedListener {
        void doBack();
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        permission.checkResult(requestCode,permissions, grantResults);
        ArrayList<String> granted=permission.getStatus().get(0).granted;
        for(int i = 1; i < granted.size(); i++){
            if(granted.get(i).contains("LOCATION")){
                appLocationService = new AppLocationService(MainMenuActivity.this);
                location = appLocationService
                        .getLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                }else{
                    Common.alert(MainMenuActivity.this,"Uyarı",getResources().getString(R.string.location_error));
                }
            }
        }

    }

    private void init(){
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        devicetoken= FirebaseInstanceId.getInstance().getToken();

        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(FRAGMENTS, new Stack<Fragment>());

        ctx = MainMenuActivity.this;


         bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        final BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        
                        switch (item.getItemId()) {

                            case R.id.action_item1:
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikontrue);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legensiconfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikonfalse);
                                if(AppController.getInstance().isAdmin)
                                    selectedFragment = GroupPlayersFragment.newInstance();
                                else
                                    selectedFragment = GeneralPlayersFragment.newInstance();
                                pushFragment(selectedFragment,GeneralPlayersFragment.FRAGMENT_TAG);
                                setTitle();
                                break;
                            case R.id.action_item2:
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legendsicontrue);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikonfalse);
                                selectedFragment = RecordChooseFragment.newInstance();
                                Bundle bundle = new Bundle();
                                bundle.putString("state", "0");
                                Fragment fragment = RecordChooseFragment.newInstance();
                                fragment.setArguments(bundle);
                                FragmentTransaction transaction1 = MainMenuActivity.this
                                        .getSupportFragmentManager().beginTransaction();
                                MainMenuActivity.this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                transaction1.replace(R.id.frame_layout, fragment,RecordChooseFragment.FRAGMENT_TAG);
                                transaction1.disallowAddToBackStack();
                                transaction1.commit();
                                MainMenuActivity.mStacks.get("Fragments").push(fragment);
                                setTitle();
//                                selectedFragment =  TahminEtFragment.newInstance();
//                                pushFragment(selectedFragment,TahminEtFragment.FRAGMENT_TAG);
                                break;
                            case R.id.action_item3:
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legensiconfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikontrue);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikonfalse);
                                selectedFragment = ContentsFragment.newInstance();
                                pushFragment(selectedFragment,ContentsFragment.FRAGMENT_TAG);
                                setTitle();
                                break;
                            case R.id.action_item4:
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legensiconfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikontrue);
                                selectedFragment = SettingsFragment.newInstance();
                                pushFragment(selectedFragment,SettingsFragment.FRAGMENT_TAG);
                                setTitle();

                                break;
                            case R.id.action_item0:
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikontrue);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legensiconfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikonfalse);

                                selectedFragment =  PrizesFragment.newInstance();
                                pushFragment(selectedFragment,PrizesFragment.FRAGMENT_TAG);
                                setTitle();
                                break;
                        }

                        if(!isOpenMontaj){
//                            pushFragment(selectedFragment);
                        }

                        return true;

                    }
                });

        if(AppController.getInstance().isAdmin){
           pushFragment(GroupPlayersFragment.newInstance(),GroupPlayersFragment.FRAGMENT_TAG);
        }else{
           pushFragment(PrizesFragment.newInstance(),ContentsFragment.FRAGMENT_TAG);
        }
        attemptLogin();
        attemptLoginbestPlayer();
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
//            Common.alert(MainMenuActivity.this,"VaillantClub",message);
            //alert data here
        }
    };

    @Override
    public void onBackPressed() {
//        if (onBackPressedListener != null) {
//            onBackPressedListener.doBack();
//        } else {
//            super.onBackPressed();
//        }
        screenBackCall();
    }

    public static void setTitle(){
        if (selectedFragment instanceof PrizesFragment) {
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof PrizesDetayFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof GeneralPlayersFragment){
            toolbar.setVisibility(View.VISIBLE);
        }else if(selectedFragment instanceof DetailsFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof ChangePasswordFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof ProfileFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof SettingsFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof RecordChooseFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof ContentsFragment){
            toolbar.setVisibility(View.VISIBLE);
        }else if(selectedFragment instanceof LegensDetailsFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof TahminEtFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof RewardVideoFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof HistoryFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof FragmentHistoryDetails){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof RakingFragment){
            toolbar.setVisibility(View.GONE);
        }else if(selectedFragment instanceof NearbyCamFragment){
            toolbar.setVisibility(View.GONE);
        }
    }


    public void pushFragment(Fragment fragment,String fragmentTag){
        mStacks.get(FRAGMENTS).push(fragment);
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction1.replace(R.id.frame_layout, fragment,fragmentTag);
        transaction1.disallowAddToBackStack();
        transaction1.commit();
    }

    public static void popFragments() {
      /*
       *    Select the second last fragment in current tab's stack..
       *    which will be shown after the fragment transaction given below
       */
      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        Fragment fragment = mStacks.get(FRAGMENTS).elementAt(mStacks.get(FRAGMENTS).size() - 2);
        mStacks.get(FRAGMENTS).pop();

        final FragmentActivity activity = wrActivity.get();
        if (activity != null && !activity.isFinishing()) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.frame_layout, fragment);
            ft.commit();
        }
    }

    public void popAllFragments() {
        mStacks.get(FRAGMENTS).clear();
    }

    public static void screenBackCall(){
        if (mStacks.get(FRAGMENTS) != null) {
            if (mStacks.get(FRAGMENTS).lastElement() != null) {

                if (((BaseFragment) mStacks.get(FRAGMENTS).lastElement()).onBackPressed() == false) {
                    if (mStacks.get(FRAGMENTS).size() == 1) {
//                        MainMenuActivity.this.super.onBackPressed();  // or call finish..
                    } else {
                        popFragments();
                    }
                } else {
                    //do nothing.. fragment already handled back button press.
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(MainMenuActivity.this).registerReceiver(mMessageReceiver,
                new IntentFilter("myFunction"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainMenuActivity.this).unregisterReceiver(mMessageReceiver);
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getApplicationContext(), response);
        customLoginController.execute(ServiceURLCreator.RegisterDevice(devicetoken,
                "Basketbul","1",Common.getUniqueID(MainMenuActivity.this),
                android.os.Build.MODEL, Locale.getDefault().getLanguage(),
                 getResources().getConfiguration().locale.getCountry(),
                AppController.getInstance().userId,String.valueOf(latitude), String.valueOf(longitude)));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                    } else {
//                        Log.e(TAG, obj.optString("ResultMessage"));
                        Common.alert(MainMenuActivity.this, "Uyarı", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }

        }
    };


    public static void attemptLoginbestPlayer(){
        CustomTask customLoginController = new CustomTask(ctx, responseBestPlayer);
        customLoginController.execute(ServiceURLCreator.GetOpponentsBest(
                AppController.getInstance().userId));
    }

    static CustomTaskFinishedListener responseBestPlayer = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray accountAndContractListArray = obj.getJSONArray("ResultObjects");
                        MeydanOkumaModel model = new MeydanOkumaModel();
                        myList = model.getCompetition(accountAndContractListArray);

                        BestPlayersAdapter adapter = new BestPlayersAdapter(ctx,myList);
                        rv.setAdapter(adapter);
                    } else {

                        Log.e("Login activity", obj.optString("errorMessage"));
                        Common.alert(ctx, "Uyarı", obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };




    @Override
    public void setSelectedFragment(BaseFragment backHandledFragment) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH);

            File old = new File(filePath);

            File dir = MainMenuActivity.this.getExternalCacheDir();
            File videoFileName = new File(dir, AppController.getInstance().userId + "-" + AppController.getInstance().CompetitonId + "-" +
                    getCurrentTimeStamp() + ".mp4");

            old.renameTo(videoFileName);

            Uri outputFileUri = FileProvider.getUriForFile(MainMenuActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    old);

            AppController.getInstance().outputfilename = String.valueOf(outputFileUri.getPath());


            Bundle bundle = new Bundle();
            bundle.putString("url", videoFileName.getPath());
            Fragment fragment = VideoSendFragment.newInstance();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment,VideoSendFragment.FRAGMENT_TAG);
            transaction.disallowAddToBackStack();
            transaction.commit();
            MainMenuActivity.mStacks.get(FRAGMENTS).push(fragment);
        }
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(new Date());
    }

}


