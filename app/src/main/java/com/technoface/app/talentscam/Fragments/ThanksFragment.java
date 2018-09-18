package com.technoface.app.talentscam.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.ViewOnTouch;

/**
 * Created by Ahmet Oguzer on 27.10.2017.
 */

public class ThanksFragment extends BaseFragment  {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";


    public static ThanksFragment newInstance() {
        ThanksFragment fragment = new ThanksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_thanks, container, false);
        Button btnOk = (Button) rootView.findViewById(R.id.btn_ok);
        AppController.getInstance().CompetitonId = "0";
        AppController.getInstance().OpponentScore= "0";

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, GeneralPlayersFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ViewOnTouch.onTouchEffect(btnOk);

        return rootView;
    }

}
