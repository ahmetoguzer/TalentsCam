package com.technoface.app.talentscam.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.HistoryAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.KarsilasmaModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 25.10.2017.
 */

public class HistoryFragment extends BaseFragment  {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private ArrayList<KarsilasmaHistoryVo> myList;
    private ImageButton btnBack;
    private ImageView imgBosicerik;
    private TextView tvBosicerik;


    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_gecmisim);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefresh);
        btnBack = (ImageButton) rootView.findViewById(R.id.btn_back);
        imgBosicerik = (ImageView) rootView.findViewById(R.id.img_bos_icerik);
        tvBosicerik = (TextView) rootView.findViewById(R.id.tv_bos_icerik);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.screenBackCall();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        attemptLogin();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        attemptLogin();

        return rootView;

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetHistory(AppController.getInstance().userId));
    }


    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if(obj.getJSONArray("ResultObjects").length()==0){
                        imgBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setVisibility(View.VISIBLE);
                        tvBosicerik.setText("Geçmişi olmayanın geleceği olmaz. Haydi tarih yazmaya!! Geçmişi olmayanın geleceği olmaz. Haydi tarih yazmaya!!");
                    }

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray completedCompetition = obj.getJSONArray("ResultObjects");
                        KarsilasmaModel model = new KarsilasmaModel();
                        myList = model.getEndedCompetition(completedCompetition);
//
                        HistoryAdapter adapter=new HistoryAdapter(getActivity(),myList);
                        rv.setAdapter(adapter);
                        imgBosicerik.setVisibility(View.GONE);
                        tvBosicerik.setVisibility(View.GONE);
                    } else {

                        Log.e("HistoryFragment", obj.optString("errorMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
