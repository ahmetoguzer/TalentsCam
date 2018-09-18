package com.technoface.app.talentscam.Fragments;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.HistoryAdapter;
import com.technoface.app.talentscam.Adapters.MyPointsAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.KarsilasmaModel;
import com.technoface.app.talentscam.Model.MyPointsModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;
import com.technoface.app.talentscam.Vo.MyPointsVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 17.01.2018.
 */

public class MyPointsFragment extends BaseFragment  {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private RecyclerView rv;
    private TextView tvPointName,tvPoint;
    private ArrayList<MyPointsVo> myList;
    private ImageButton btnBack;



    public static MyPointsFragment newInstance() {
        MyPointsFragment fragment = new MyPointsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_points, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_gecmisim);
        tvPointName = (TextView) rootView.findViewById(R.id.tvPointName);
        tvPoint = (TextView) rootView.findViewById(R.id.tvPoint);
        btnBack = rootView.findViewById(R.id.btn_back);

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
        customLoginController.execute(ServiceURLCreator.GetBasketCoinHistory(Common.getUniqueID(getActivity()),AppController.getInstance().userId));
    }


    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = obj.getJSONArray("ResultObjects");

                        JSONObject jsonObject =jsonArray.getJSONObject(0);
                        String pointName = jsonObject.optString("PointName");
                        String point = jsonObject.optString("Point");

                        tvPointName.setText(pointName);
                        tvPoint.setText(point);

                        MyPointsModel model = new MyPointsModel();
                        myList = model.getMyPoint(jsonArray);

                        MyPointsAdapter adapter=new MyPointsAdapter(getActivity(),myList);
                        rv.setAdapter(adapter);

                    } else {

                        Log.e("MyPointsFragment", obj.optString("errorMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}

