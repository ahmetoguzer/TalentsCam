package com.technoface.app.talentscam.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.technoface.app.talentscam.R;

/**
 * Created by Ahmet Oguzer on 27.10.2017.
 */

public class CountdownFragment extends Fragment {

    private CountDownTimer t;
    private long maxTimeInMilliseconds = 90*1000;
    private CircularProgressBar circularProgressBar;
    private TextView tvRemainingTime;
    private String qrcode;
    private Button btnShow;

    public static CountdownFragment newInstance() {
        CountdownFragment fragment = new CountdownFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_countdown, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startRecord(maxTimeInMilliseconds,1000);
        increase_splash_bar(0,100);
    }

    public void init(View rootView){
        circularProgressBar = (CircularProgressBar)rootView.findViewById(R.id.meydan_okuma_CircularProgressbar);
        tvRemainingTime  =(TextView) rootView.findViewById(R.id.tv_remaining_time);
        btnShow = (Button) rootView.findViewById(R.id.btn_show);
        circularProgressBar.setBackgroundColor(Color.parseColor("#E19E66"));
        circularProgressBar.setColor(Color.parseColor("#FF7E0B"));
        circularProgressBar.setProgressBarWidth(40f);
        circularProgressBar.setBackgroundProgressBarWidth(40f);

        qrcode = getArguments().getString("scanr");


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("scanr",qrcode);
                Fragment fragment = SmartCamSendVideoFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


    public void startRecord(final long finish, long tick)
    {
        t = new CountDownTimer(finish, tick) {

            public void onTick(long millisUntilFinished)
            {
                long remainedSecs = millisUntilFinished/1000;
                tvRemainingTime.setText(remainedSecs+" sn");
            }

            public void onFinish()
            {
                tvRemainingTime.setText("0 sn");
                cancel();
            }
        }.start();
    }

    public void increase_splash_bar (int from, int to)
    {
        final Handler handler1 = new Handler();
        class Task implements Runnable {

            int start,end;

            Task(int a,int b) { start = a; end = b;}

            @Override
            public void run() {
                for (int i =start ; i <= end; i++) {
                    final int value = i;
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            int animationDuration = 1000;
                            circularProgressBar.setProgressWithAnimation(Float.valueOf(value), animationDuration);
                        }
                    });
                }
            }
        }

        Thread t = new Thread(new Task(from, to));
        t.start();
    }
}
