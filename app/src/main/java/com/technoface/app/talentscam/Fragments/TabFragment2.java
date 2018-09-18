package com.technoface.app.talentscam.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by landforce on 08.09.2014.
 */
public class TabFragment2 extends Fragment {

    private GridView gv;
    private ImageView imgDuello;
    private TextView txtDuello;

    private ArrayList<KarsilasmaHistoryVo> myList;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog progres = null;
    private MainMenuActivity mActivity;
//    public ProgressBar pb;

    public static TabFragment2 newInstance() {
        TabFragment2 fragment = new TabFragment2();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
        imgDuello = (ImageView) rootView.findViewById(R.id.img_duello);
        txtDuello = (TextView) rootView.findViewById(R.id.txt_duello);
        gv=(GridView) rootView.findViewById(R.id.gridView2);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefreshDuello);
//        pb = (ProgressBar) rootView.findViewById(R.id.pb_reviews);
        myList = new ArrayList<>();




        LoadingData loadingData= new LoadingData(getActivity());
        loadingData.execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingData loadingData= new LoadingData(getActivity());
                        loadingData.execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        return rootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
//          pb.setVisibility(View.VISIBLE);
          attemptLogin();
//          int tmp = gv.getChildCount();
//            if(tmp>0){
//                pb.setVisibility(View.INVISIBLE);
//            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ctx = getActivity();
    }
//
//    @Override public void setUserVisibleHint(boolean isVisibleToUser) {
//        if(isVisibleToUser){
//            LoadingData loadingData = new LoadingData(getActivity());
//            loadingData.execute();
//
//
//        }
//        super.setUserVisibleHint(isVisibleToUser);
//    }


    private class LoadingData extends AsyncTask<Object, Object, String> {

        private Context ctx;

        public LoadingData(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            progres = new ProgressDialog(ctx);
            progres.setMessage(getString(R.string.loading_str));
            progres.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... voids) {
            attemptLogin();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String compressed) {
            super.onPostExecute(compressed);
//            progressBar.setVisibility(View.GONE);
            progres.dismiss();

        }
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getApplicationContext(), response);
        customLoginController.execute(ServiceURLCreator.GetCompletedCompetition(Common.getUniqueID(getApplicationContext())
                ,AppController.getInstance().userId));
    }


    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray completedCompetition = obj.getJSONArray("ResultObjects");
//                        KarsilasmaModel model = new KarsilasmaModel();
//                        myList = model.getEndedCompetition(completedCompetition);
////
//                        KarsilasmaAdapter adapter=new KarsilasmaAdapter(getApplicationContext(),myList);
//                        gv.setAdapter(adapter);
                    } else {

                        Log.e("Login activity", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(),"HATA",);
            }
        }
    };
}
