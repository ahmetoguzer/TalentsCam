 package com.technoface.app.talentscam.Fragments;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.technoface.app.talentscam.Activities.LoginActivity;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.PlayersAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.MeydanOkumaModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.helper.AppLocationService;
import com.technoface.app.talentscam.helper.BottomNavigationViewHelper;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;

 public class GeneralPlayersFragment extends BaseFragment implements MainMenuActivity.OnBackPressedListener {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

     private ArrayList<MeydanOkumaVo> myList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog progres = null;
    private RecyclerView rv;

    private String groupid;
     private ImageView imgBosicerik;
     private TextView tvBosicerik;
     private MySharedpreferences mySharedPreferences;
     private JSONArray  jArray;
     public static BottomNavigationView bottomNavigationView;
     private AppLocationService appLocationService;
     private double latitude;
     private double longitude;
     private Location location;

     public static GeneralPlayersFragment newInstance() {
        GeneralPlayersFragment fragment = new GeneralPlayersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_players, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_meydan_okuma);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefresh);
        imgBosicerik = (ImageView) rootView.findViewById(R.id.img_bos_icerik);
        tvBosicerik = (TextView) rootView.findViewById(R.id.tv_bos_icerik);
        myList = new ArrayList<>();
        mySharedPreferences= MySharedpreferences.getInstance(getActivity());

        if(AppController.getInstance().isOpenGeneralPlayersFragment){
            try {
                 jArray = new JSONArray(mySharedPreferences.getData("genelist"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MeydanOkumaModel model = new MeydanOkumaModel();
            myList = model.getCompetition(jArray);

            PlayersAdapter adapter=new PlayersAdapter(getActivity(),myList);
            rv.setAdapter(adapter);
            imgBosicerik.setVisibility(View.GONE);
            tvBosicerik.setVisibility(View.GONE);

        }else{
            attemptLogin();
        }

        appLocationService = new AppLocationService(getActivity());
        location = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }else{
            Common.alert(getActivity(),"Uyarı",getResources().getString(R.string.location_error));
        }


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        attemptLogin();
                        swipeRefreshLayout.setRefreshing(false);
//                        MainMenuActivity.attemptLoginbestPlayer();
                    }
                }, 2000);
            }
        });


        AppController.getInstance().context=getActivity();

        bottomNavigationView = (BottomNavigationView)
                rootView.findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.action_item1:
                             attemptLoginFacebook();
                                break;
                            case R.id.action_item2:
                                attemptLogiLocation();
                                break;
                            case R.id.action_item0:
                                attemptLogin();
                                break;
                        }

                        return true;

                    }
                });
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        ((MainMenuActivity) getActivity()).setOnBackPressedListener(this);
        super.onActivityCreated(savedInstanceState);
    }

     private void attemptLogin(){
         CustomTask customLoginController = new CustomTask(getActivity(), response);
         customLoginController.execute(ServiceURLCreator.GetCompetition(
                 AppController.getInstance().userId));
     }



     CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
         @Override
         public void taskFinished(Message msg) {
             try {
                 if( msg.obj.toString().length()>0) {
                     JSONObject obj = new JSONObject(msg.obj.toString());

                     if (obj.optString("HasError").equals("false")) {

                         JSONArray jsonArray = obj.getJSONArray("ResultObjects");
                         MeydanOkumaModel model = new MeydanOkumaModel();
                         myList = model.getCompetition(jsonArray);

                         mySharedPreferences.saveData("genelist", String.valueOf(jsonArray));
                         AppController.getInstance().isOpenGeneralPlayersFragment = true;

                         PlayersAdapter adapter=new PlayersAdapter(getActivity(),myList);
                         rv.setAdapter(adapter);
                         imgBosicerik.setVisibility(View.GONE);
                         tvBosicerik.setVisibility(View.GONE);
                     } else {
                         imgBosicerik.setVisibility(View.VISIBLE);
                         tvBosicerik.setVisibility(View.VISIBLE);
                         tvBosicerik.setText("Henüz kimse karşınıza çıkmaya ceserat edemedi. Merak etmeyin, biraz sonra buralar dolar :)");
                         Log.e("Login activity", obj.optString("errorMessage"));

                     }
                 }
             } catch (Exception e) {
                 e.printStackTrace();
//               Common.alert(getActivity(), "", "");
             }

         }
     };





     @Override
     public void doBack() {

     }


     private void attemptLoginFacebook(){
         CustomTask customLoginController = new CustomTask(getActivity(), responseFacebook);
         customLoginController.execute(ServiceURLCreator.GetOpponentsByFacebook(Common.getUniqueID(getActivity()),
                 AppController.getInstance().profilFriends));
     }


     CustomTaskFinishedListener responseFacebook = new CustomTaskFinishedListener() {
         @Override
         public void taskFinished(Message msg) {
             try {
                 if( msg.obj.toString().length()>0) {
                     JSONObject obj = new JSONObject(msg.obj.toString());

                     if (obj.optString("HasError").equals("false")) {

                         JSONArray jArray = obj.getJSONArray("ResultObjects");

                         MeydanOkumaModel model = new MeydanOkumaModel();
                         myList = model.getCompetition(jArray);

                         PlayersAdapter adapter=new PlayersAdapter(getActivity(),myList);
                         rv.setAdapter(adapter);

                         imgBosicerik.setVisibility(View.GONE);
                         tvBosicerik.setVisibility(View.GONE);
                     } else {
                         imgBosicerik.setVisibility(View.VISIBLE);
                         tvBosicerik.setVisibility(View.VISIBLE);
                         tvBosicerik.setText("Facebook arkadaşlarınız  uygulamamızı kullanmak için bekliyor olabilir :)");
                         Log.e("Login activity", obj.optString("Resultmessage"));

                     }
                 }
             } catch (Exception e) {
                 e.printStackTrace();
//               Common.alert(getActivity(), "", "");
             }

         }
     };

     private void attemptLogiLocation(){
         CustomTask customLoginController = new CustomTask(getActivity(), response);
         customLoginController.execute(ServiceURLCreator.GetOpponentsByLocation(Common.getUniqueID(getActivity()),
                 AppController.getInstance().userId,String.valueOf(latitude), String.valueOf(longitude)));
     }


 }
