package com.technoface.app.talentscam.Adapters;

/**
 * Created by Ahmet Oguzer on 7.11.2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Fragments.FragmentHistoryDetails;
import com.technoface.app.talentscam.Fragments.PrizesDetayFragment;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ahmet Oguzer on 25.10.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.PersonViewHolder> {

    private ArrayList myList;
    private String imageurl,oppenenturl;
    private Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView myName,tvscore,tvHistory,opponentName;
        ImageView myPhoto,opponentPhoto;
        CircularProgressBar mycircularProgressBar,opponentCircularProgressbar;


        PersonViewHolder(View itemView) {
            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
            myName = (TextView)itemView.findViewById(R.id.my_name);
            opponentName =  (TextView)itemView.findViewById(R.id.opponent_name);
            myPhoto = (ImageView)itemView.findViewById(R.id.my_photo);
            opponentPhoto = (ImageView)itemView.findViewById(R.id.opponent_photo);
            tvscore = (TextView) itemView.findViewById(R.id.tv_score);
            mycircularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.myCircularProgressbar);
            opponentCircularProgressbar = (CircularProgressBar)itemView.findViewById(R.id.opponent_CircularProgressbar);
        }
    }


    public HistoryAdapter(Context context , ArrayList<KarsilasmaHistoryVo> prgmNameList){
        this.myList = prgmNameList;
        this.context =context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HistoryAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        HistoryAdapter.PersonViewHolder pvh = new HistoryAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.PersonViewHolder personViewHolder, final int i) {

        final KarsilasmaHistoryVo karsilasmaHistoryVo = (KarsilasmaHistoryVo) myList.get(i);

        personViewHolder.myName.setText(karsilasmaHistoryVo.getUserName());
        personViewHolder.opponentName.setText(karsilasmaHistoryVo.getOpponentName());

        if(!karsilasmaHistoryVo.getOpponentImageUrl().contains("https")
                && !karsilasmaHistoryVo.getOpponentImageUrl().contains("technoface")
                && !karsilasmaHistoryVo.getOpponentImageUrl().equals(""))
        {
            String[] pathArray =  karsilasmaHistoryVo.getOpponentImageUrl().split("\\:");
            oppenenturl=pathArray[0]+"s:"+pathArray[1];
        }else{
            oppenenturl= karsilasmaHistoryVo.getOpponentImageUrl();
        }

        if(!karsilasmaHistoryVo.getUserAvatarUrl().contains("https")
                && !karsilasmaHistoryVo.getOpponentImageUrl().contains("technoface")
                && !karsilasmaHistoryVo.getOpponentImageUrl().equals(""))
        {
            String[] pathArray =  karsilasmaHistoryVo.getUserAvatarUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= karsilasmaHistoryVo.getUserAvatarUrl();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(300*1024*2014)
                .discCacheSize(300*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(oppenenturl, personViewHolder.opponentPhoto, options);
        imageLoader.displayImage(imageurl, personViewHolder.myPhoto, options);

        int ss = Integer.parseInt(karsilasmaHistoryVo.getShootingScore());
        int os = Integer.parseInt(karsilasmaHistoryVo.getOpponentScore());

        if(ss > os){
            personViewHolder.mycircularProgressBar.setBackgroundColor(Color.parseColor("#1100FF"));
            personViewHolder.opponentCircularProgressbar.setBackgroundColor(Color.parseColor("#F60E0E"));
        }

        if(ss < os){
            personViewHolder.mycircularProgressBar.setBackgroundColor(Color.parseColor("#F60E0E"));
            personViewHolder.opponentCircularProgressbar.setBackgroundColor(Color.parseColor("#1100FF"));
        }

        if(ss == os){
            personViewHolder.mycircularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
            personViewHolder.opponentCircularProgressbar.setBackgroundColor(Color.parseColor("#04c528"));
        }


        personViewHolder.mycircularProgressBar.setProgressBarWidth(4f);
        personViewHolder.mycircularProgressBar.setBackgroundProgressBarWidth(4f);
        int animationDuration = 2000; // 2500ms = 2,5s
        personViewHolder.mycircularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);


        personViewHolder.opponentCircularProgressbar.setProgressBarWidth(4f);
        personViewHolder.opponentCircularProgressbar.setBackgroundProgressBarWidth(4f);
        personViewHolder.opponentCircularProgressbar.setProgressWithAnimation(Float.valueOf(0), animationDuration);


//        String date=karsilasmaHistoryVo.getShootingCreationDate();
//        String creationDate=date.substring(6,19);


        if(karsilasmaHistoryVo.getOpponentId().equals("0")){
            personViewHolder.opponentCircularProgressbar.setVisibility(View.INVISIBLE);
            personViewHolder.opponentPhoto.setVisibility(View.INVISIBLE);
            personViewHolder.opponentName.setVisibility(View.INVISIBLE);
            personViewHolder.tvscore.setText(karsilasmaHistoryVo.getShootingScore());
        }else{
            personViewHolder.opponentCircularProgressbar.setVisibility(View.VISIBLE);
            personViewHolder.opponentPhoto.setVisibility(View.VISIBLE);
            personViewHolder.opponentName.setVisibility(View.VISIBLE);
            personViewHolder.tvscore.setText(karsilasmaHistoryVo.getShootingScore()+" - "+karsilasmaHistoryVo.getOpponentScore());

        }

//        personViewHolder.tvHistory.setText( getDate(Long.parseLong(creationDate),"dd/MM/yyyy"));

        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("pos", String.valueOf(i));
                Fragment fragment = FragmentHistoryDetails.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment,FragmentHistoryDetails.FRAGMENT_TAG);
                transaction.disallowAddToBackStack();
                transaction.commit();
                MainMenuActivity.mStacks.get("Fragments").push(fragment);
                MainMenuActivity.selectedFragment = FragmentHistoryDetails.newInstance();
                MainMenuActivity.setTitle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}