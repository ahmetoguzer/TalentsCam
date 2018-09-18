package com.technoface.app.talentscam.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.CountryAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.CountryModel;
import com.technoface.app.talentscam.Model.PrizesModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.CountryVo;
import com.technoface.app.talentscam.Vo.PrizesVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ahmet Oguzer on 15.01.2018.
 */

public class ChangeCountryFragment extends BaseFragment  {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";
    private RadioGroup RadioGroupTab;
    private RadioButton tab1,tab2;
    private Spinner spinnerCountry;
    private ArrayList<CountryVo> myList;
    private CountryAdapter adapter;
    private String  selectedCountry,myLanguage,myCountry;
    private Button btnUpdate;
    private MySharedpreferences mySharedPreferences;
    private ImageButton btnBack;
    private String cntry;

    public static ChangeCountryFragment newInstance() {
        ChangeCountryFragment fragment = new ChangeCountryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_change_country, container, false);

        tab1 = rootView.findViewById(R.id.tab1);
        tab2 = rootView.findViewById(R.id.tab2);
        spinnerCountry = rootView.findViewById(R.id.spinner_country);
        spinnerCountry.setOnItemSelectedListener(setOnCountrySelectListener);
        btnUpdate = rootView.findViewById(R.id.btn_update);
        btnBack = rootView.findViewById(R.id.btn_back);
        mySharedPreferences= MySharedpreferences.getInstance(getActivity());

        myLanguage= Locale.getDefault().getLanguage();
        mySharedPreferences.saveData("lang",myLanguage);
        myCountry = getActivity().getResources().getConfiguration().locale.getCountry();
        mySharedPreferences.saveData("country",myCountry);
        if(myLanguage.equals("en")){
            tab2.setChecked(true);

        }else if(myLanguage.equals("tr")){
            tab1.setChecked(true);
        }
        RadioGroupTab = (RadioGroup) rootView.findViewById(R.id.RadioGroupTab_normal);
        RadioGroupTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.tab1:
                        changeLang(getActivity(),"tr");
                        myLanguage="tr";
                        break;
                    case R.id.tab2:
                        changeLang(getActivity(),"en");
                        myLanguage="en";
                        break;
                    default:
                        break;
                }
            }
        });
        myList = new ArrayList<>();

        ViewOnTouch.onTouchEffect(btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdateUserLocale();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenuActivity.screenBackCall();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        attemptLogin();
        attemptGetUserLocale();
    }

    public void changeLang(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//        startActivity(new Intent(getActivity(),MainMenuActivity.class));
    }

    private AdapterView.OnItemSelectedListener setOnCountrySelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
//                CountryVo cityItem = (CountryVo) adapter.getItem1(position);
                selectedCountry = String.valueOf(adapter.getItem1(position));

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.GetCountries(Common.getUniqueID(getActivity())));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = new JSONArray(obj.optString("ResultObjects"));

                        CountryModel model = new CountryModel();
                        myList = model.getCountry(jsonArray);
                        adapter = new CountryAdapter(getActivity(), android.R.layout.simple_list_item_single_choice, myList);
                        spinnerCountry.setAdapter(adapter);

                    } else {
                        Log.e("ChangeCountryFragment", obj.optString("ResultMessage"));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

    private void attemptGetUserLocale(){
        CustomTask customLoginController = new CustomTask(getActivity(), responseUserLocale);
        customLoginController.execute(ServiceURLCreator.
                GetUserLocale(Common.getUniqueID(getActivity()),AppController.getInstance().userId));
    }

    CustomTaskFinishedListener responseUserLocale = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray jsonArray = new JSONArray(obj.optString("ResultObjects"));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                         cntry = jsonObject.optString("CountryCode");
                        for(int i=0; i < adapter.getCount(); i++) {
                            if(adapter.getItem1(i).toString().contains(cntry.trim())){
                                spinnerCountry.setSelection(i);
                            }
                        }

                    } else {
                        Log.e("ChangeCountryFragment", obj.optString("ResultMessage"));
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };

    private void attemptUpdateUserLocale(){
        CustomTask customLoginController = new CustomTask(getActivity(), responseUpdateUserLocale);
        customLoginController.execute(ServiceURLCreator.UpdateUserLocale(Common.getUniqueID(getActivity()),AppController.getInstance().userId,
                myLanguage,selectedCountry));
    }

    CustomTaskFinishedListener responseUpdateUserLocale = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        mySharedPreferences.saveData("lang",myLanguage);
                        mySharedPreferences.saveData("country",myCountry);
                        MainMenuActivity.screenBackCall();


                    } else {
                        Log.e("ChangeCountryFragment", obj.optString("ResultMessage"));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }
        }
    };
}

