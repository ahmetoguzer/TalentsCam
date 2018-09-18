package com.technoface.app.talentscam.Fragments;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.PlayersAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.MeydanOkumaModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.helper.AppLocationService;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 6.11.2017.
 */

public class NearbyPlayerFragment extends Fragment implements MainMenuActivity.OnBackPressedListener {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<MeydanOkumaVo> myList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView rv;
    private AppLocationService appLocationService;
    private double latitude;
    private double longitude;
    private Location location;
    private ImageView imgBosicerik;
    private TextView tvBosicerik;

    public static NearbyPlayerFragment newInstance() {
        NearbyPlayerFragment fragment = new NearbyPlayerFragment();
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

        appLocationService = new AppLocationService(getActivity());
        location = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();

        }else{
            Common.alert(getActivity(),"Uyarı",getResources().getString(R.string.location_error));
        }

        attemptLogin();

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
                    }
                }, 2000);
            }
        });


        AppController.getInstance().context=getActivity();
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setOnBackPressedListener(this);
        MainMenuActivity.rv.setVisibility(View.VISIBLE);
        super.onActivityCreated(savedInstanceState);
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetOpponentsByLocation(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId,String.valueOf(latitude), String.valueOf(longitude)));
    }



    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if(obj.getJSONArray("ResultObjects").equals("[]")){
                        imgBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setText("Lokasyon bilgisine erişmemize izin mi vermediniz? Yoksa, diğerleri sizden korkup saklandı mı? İnceliyoruz. :)");

                    }

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray accountAndContractListArray = obj.getJSONArray("ResultObjects");
                        MeydanOkumaModel model = new MeydanOkumaModel();
                        myList = model.getCompetition(accountAndContractListArray);

                        PlayersAdapter adapter=new PlayersAdapter(getActivity(),myList);
                        rv.setAdapter(adapter);
                        imgBosicerik.setVisibility(View.GONE);
                        tvBosicerik.setVisibility(View.GONE);
                    } else {
                        imgBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setText("Lokasyon bilgisine erişmemize izin mi vermediniz? Yoksa, diğerleri sizden korkup saklandı mı? İnceliyoruz. :)");

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
}

