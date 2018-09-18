package com.technoface.app.talentscam.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.technoface.app.talentscam.R;

/**
 * Created by Ahmet Oguzer on 21.11.2017.
 */

public class TutorialFragment extends Fragment {

    public static TutorialFragment newInstance() {
        TutorialFragment fragment = new TutorialFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    transaction.replace(R.id.frame_layout, RakingFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();                }
                return true;
            }
        });


        return rootView;
    }
}
