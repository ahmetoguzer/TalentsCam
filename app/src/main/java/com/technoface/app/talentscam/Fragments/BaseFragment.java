package com.technoface.app.talentscam.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.technoface.app.talentscam.Activities.MainMenuActivity;

/**
 * Created by Ahmet on 3.7.2017.
 */

public abstract class BaseFragment extends Fragment {

    protected BackHandlerInterface backHandlerInterface;
    public MainMenuActivity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity     =   (MainMenuActivity) this.getActivity();
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Mark this fragment as the selected Fragment.
        backHandlerInterface.setSelectedFragment(this);
    }


    public boolean onBackPressed(){
        return false;
    }

    public interface BackHandlerInterface {
        public void setSelectedFragment(BaseFragment backHandledFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}