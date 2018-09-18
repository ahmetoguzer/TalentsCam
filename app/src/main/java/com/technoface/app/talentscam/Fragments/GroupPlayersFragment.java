package com.technoface.app.talentscam.Fragments;

import android.content.Context;
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

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.GroupPlayersAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.MeydanOkumaModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Ahmet Oguzer on 30.10.2017.
 */

public class GroupPlayersFragment extends Fragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<MeydanOkumaVo> myList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context ctx;

    private RecyclerView rv;

    public static GroupPlayersFragment newInstance() {
        GroupPlayersFragment fragment = new GroupPlayersFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_players, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        MainMenuActivity.rv.setVisibility(View.VISIBLE);
    }

    private void init(View view){
        rv = (RecyclerView) view.findViewById(R.id.rv_group_players);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeToRefresh);
        myList = new ArrayList<>();

        attemptLoginGroupMembers();

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
                        attemptLoginGroupMembers();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        AppController.getInstance().context=getActivity();
    }

    private void attemptLoginGroupMembers() {
        CustomTask customLoginController = new CustomTask(getApplicationContext(), responseGroupMember);
        customLoginController.execute(ServiceURLCreator.GetGroupMembers(
                Common.getUniqueID(getApplicationContext()),AppController.getInstance().userId,AppController.getInstance().groupId)
        );

    }

    CustomTaskFinishedListener responseGroupMember = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray accountAndContractListArray = obj.getJSONArray("ResultObjects");
                        MeydanOkumaModel model = new MeydanOkumaModel();
                        myList = model.getCompetition(accountAndContractListArray);

                        GroupPlayersAdapter adapter=new GroupPlayersAdapter(getActivity(),myList);
                        rv.setAdapter(adapter);
                    } else {

                        Log.e(String.valueOf(ctx), obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Common.alert(getActivity(), "HATA",msg.obj.toString());
            }
        }
    };
}
