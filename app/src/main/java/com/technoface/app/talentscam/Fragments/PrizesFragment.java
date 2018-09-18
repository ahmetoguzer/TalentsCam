package com.technoface.app.talentscam.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.technoface.app.talentscam.Adapters.ContentsAdapter;
import com.technoface.app.talentscam.Adapters.PrizesAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.ContentsModel;
import com.technoface.app.talentscam.Model.PrizesModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.ContentsVo;
import com.technoface.app.talentscam.Vo.PrizesVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class PrizesFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<PrizesVo> myList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog progres = null;
    private RecyclerView rv;

    private String groupid;
    private ImageView imgBosicerik;
    private TextView tvTotalCoins;

    public static PrizesFragment newInstance() {
        PrizesFragment fragment = new PrizesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.prizes_fragment, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_prizes);
        tvTotalCoins =  rootView.findViewById(R.id.tvTotalCoins);
        myList = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        AppController.getInstance().context=getActivity();
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        MySharedpreferences mySharedPreferences =  MySharedpreferences.getInstance(getActivity());

        attemptLogin();
        attemptPoint();

        super.onActivityCreated(savedInstanceState);
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetPrizes(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId));
    }

    private void attemptPoint(){
        CustomTask customLoginController = new CustomTask(getActivity(), responsePoint);
        customLoginController.execute(ServiceURLCreator.GetUserPoints(Common.getUniqueID(getActivity()),
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
                        PrizesModel model = new PrizesModel();
                        myList = model.getPrizes(jsonArray);

                        PrizesAdapter adapter=new PrizesAdapter(getActivity(),myList);
                        rv.setAdapter(adapter);

                    } else {

                        Log.e("PrizesFragment", obj.optString("ResultMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

    CustomTaskFinishedListener responsePoint = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = obj.getJSONArray("ResultObjects");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String point = jsonObject.optString("Point");
                        tvTotalCoins.setText("Basket Coin : "+point);


                    } else {

                        Log.e("PrizesFragment", obj.optString("ResultMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };


}
