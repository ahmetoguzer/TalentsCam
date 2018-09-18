package com.technoface.app.talentscam.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.RakingAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.LigModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.LigVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class RakingFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<LigVo> myList;
    private ImageView resmim;
    private ListView list;
    private int pos=2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView siram;
    private TextView adim;
    private TextView puanim;
    private ProgressDialog progres = null;
    protected MainMenuActivity mActivity;

    private RadioGroup RadioGroupTab;
    private LinearLayout layoutNormal;

    private RadioGroup RadioGroupTabgrup;
    private LinearLayout layoutGrup;

    private ImageButton btnBack;

    public static RakingFragment newInstance() {
        RakingFragment fragment = new RakingFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_raking, container, false);
        list = (ListView) rootView.findViewById(R.id.ListView_Ben);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeToRefreshLig);


        resmim = (ImageView) rootView.findViewById(R.id.img_myprofile_pre_screen);
        siram = (TextView) rootView.findViewById(R.id.txt_siralamam);
        adim = (TextView) rootView.findViewById(R.id.txt_adim);
        puanim = (TextView) rootView.findViewById(R.id.txt_puanim);
        layoutNormal = (LinearLayout) rootView.findViewById(R.id.layout_puan_durumu_normal);
        layoutGrup = (LinearLayout) rootView.findViewById(R.id.layout_puan_durumu_grup);
        btnBack = (ImageButton) rootView.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.screenBackCall();
            }
        });

        myList = new  ArrayList<>();
        attemptGetRaking(responseKendim);
        attemptGetRaking(responseGenel);
        Boolean[] tmp={true,false,false};

        RadioGroupTabgrup = (RadioGroup) rootView.findViewById(R.id.RadioGroupTab_grup);
        RadioGroupTab = (RadioGroup) rootView.findViewById(R.id.RadioGroupTab_normal);

            layoutNormal.setVisibility(View.VISIBLE);
            layoutGrup.setVisibility(View.GONE);
            RadioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.tab1:
                            pos=0;
                            new LoadingData(getActivity()).execute();
                            break;
                        case R.id.tab2:
                            pos=1;
                            new LoadingData(getActivity()).execute();
                            break;
                        case R.id.tab3:
                            pos=2;
                            new LoadingData(getActivity()).execute();
                            break;
                        default:
                            break;
                    }
                }
            });
//        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new LoadingData(getActivity()).execute();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainMenuActivity) activity;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            if(pos==0){
                attemptGetRaking(responseGenel);
            }else if(pos == 1){
                attemptGetRaking(responseAylik);
            }else if(pos == 2){
                attemptGetRaking(responseGrup);
            }if(pos == 3){
                attemptGetRaking(responseGrup);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    private class LoadingData extends AsyncTask<Object, Object, String> {

        private Context ctx;

        public LoadingData(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            progres = new ProgressDialog(getActivity());
            progres.setMessage(getString(R.string.loading_str));
            progres.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... voids) {
            if(pos==0){
                attemptGetRaking(responseGenel);
            }else if(pos == 1){
                attemptGetRaking(responseAylik);
            }else if(pos == 2){
                attemptGetRaking(responseGrup);
            }else if(pos == 3){
                attemptGetRaking(responseGrup);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String compressed) {
            super.onPostExecute(compressed);
//            progressBar.setVisibility(View.GONE);
            progres.dismiss();

        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainMenuActivity.rv.setVisibility(View.GONE);
    }


    private void attemptGetRaking(CustomTaskFinishedListener response ){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetRaking(AppController.getInstance().userId,AppController.getInstance().grupid));
    }

    CustomTaskFinishedListener responseGrup = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray raking= obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(raking.get(4).toString());
                        JSONArray haftalıkSıra= objs.getJSONArray("RankingList");
                        LigModel model = new LigModel();
                        myList = model.getRaking(haftalıkSıra);


                        RakingAdapter adapter=new RakingAdapter(getActivity(),myList);
                        list.setAdapter(adapter);
                    } else {

                        Log.e("Raking Fragment", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };


    CustomTaskFinishedListener responseHaftalik = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray raking= obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(raking.get(0).toString());
                        JSONArray haftalıkSıra= objs.getJSONArray("RankingList");
                        LigModel model = new LigModel();
                        myList = model.getRaking(haftalıkSıra);


                        RakingAdapter adapter=new RakingAdapter(getActivity(),myList);
                        list.setAdapter(adapter);
                    } else {

                        Log.e("Lig Fragment", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };


    CustomTaskFinishedListener responseAylik = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray raking= obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(raking.get(1).toString());
                        JSONArray haftalıkSıra= objs.getJSONArray("RankingList");
                        LigModel model = new LigModel();
                        myList = model.getRaking(haftalıkSıra);

                        RakingAdapter adapter=new RakingAdapter(getActivity(),myList);
                        list.setAdapter(adapter);
                    } else {

                        Log.e("Lig Fragment", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };

    CustomTaskFinishedListener responseGenel = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray raking= obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(raking.get(2).toString());
                        JSONArray haftalıkSıra= objs.getJSONArray("RankingList");
                        LigModel model = new LigModel();
                        myList = model.getRaking(haftalıkSıra);

                        RakingAdapter adapter=new RakingAdapter(getActivity(),myList);
                        list.setAdapter(adapter);
                    } else {

                        Log.e("Lig Fragment", obj.optString("errorMessage"));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };


    CustomTaskFinishedListener responseKendim = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {

                        JSONArray raking= obj.getJSONArray("ResultObjects");
                        JSONObject objs = new JSONObject(raking.get(3).toString());
                        JSONArray haftalıkSıra= objs.getJSONArray("RankingList");
                        LigModel model = new LigModel();
                        myList = model.getRaking(haftalıkSıra);

                        LigVo ligVo=myList.get(0);
                        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                                .threadPriority(Thread.NORM_PRIORITY)
                                .denyCacheImageMultipleSizesInMemory()
                                .tasksProcessingOrder(QueueProcessingType.LIFO)
                                .memoryCacheSize(300*1024*2014)
                                .discCacheSize(300*1024*1024)
                                .build();

                        ImageLoader.getInstance().init(config);
                        final ImageLoader imageLoader=ImageLoader.getInstance();
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .cacheOnDisc()
                                .cacheInMemory()
                                .build();

                        imageLoader.displayImage(ligVo.getUserAvatarUrl(), resmim, options);
//                        Picasso.with(getActivity()).load(ligVo.getUserAvatarUrl()).into( resmim);
                        adim.setText(ligVo.getUserName());
                        siram.setText(ligVo.getSira());
                        puanim.setText(ligVo.getToplamPuan());
                    } else {
                        Log.e("Lig Fragment", obj.optString("errorMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                Common.alert(getActivity(), "", "");
            }
        }
    };

}
