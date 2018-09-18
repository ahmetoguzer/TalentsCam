package com.technoface.app.talentscam.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Fragments.DetailsFragment;
import com.technoface.app.talentscam.Fragments.PrizesDetayFragment;
import com.technoface.app.talentscam.Fragments.RecordChooseFragment;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;

import java.util.ArrayList;


/**
 * Created by Ahmet Oguzer on 24.10.2017.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PersonViewHolder> {
    public static final String FRAGMENTS = "Fragments";

    private ArrayList myList;
    private static Context context;
    private String imageurl,movLink;
    public ProgressDialog proggres;
    private ArrayList<String> arrayList;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView personName,tvCompettionPoint;
        public static ImageView personPhoto,thumbnail;
        CircularProgressBar circularProgressBar;
        TextView personPuan;


        PersonViewHolder(View itemView) {
            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.txt_meydann_okuma);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            personPuan = (TextView) itemView.findViewById(R.id.person_score);
            thumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvCompettionPoint = (TextView)  itemView.findViewById(R.id.tv_competitionpoint);
            circularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.meydan_okuma_CircularProgressbar);
        }
    }


    public PlayersAdapter(Context ctx, ArrayList<MeydanOkumaVo> prgmNameList){
        this.context=ctx;
        this.myList = prgmNameList;
        arrayList = new ArrayList<>();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player_list, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        final MeydanOkumaVo meydanOkumaVo= (MeydanOkumaVo) myList.get(i);

        personViewHolder.personName.setText(meydanOkumaVo.getCompetitionName());
        personViewHolder.tvCompettionPoint.setText(meydanOkumaVo.getCompetitionScore());
        personViewHolder.personPuan.setText(meydanOkumaVo.getOpponentPoint());


        if(!meydanOkumaVo.getCompetitionImageUrl().contains("https")
                && !meydanOkumaVo.getCompetitionImageUrl().contains("technoface")
                && !meydanOkumaVo.getCompetitionImageUrl().equals(""))
        {
            String[] pathArray =  meydanOkumaVo.getCompetitionImageUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= meydanOkumaVo.getCompetitionImageUrl();
        }

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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

        imageLoader.displayImage(imageurl, personViewHolder.personPhoto, options);
        imageLoader.displayImage(meydanOkumaVo.getPreviewUrl(), personViewHolder.thumbnail, options);

        if(meydanOkumaVo.getCompetitionImageUrl().equals(" ")){
            personViewHolder.personPhoto.setImageResource(R.drawable.defaultprofile);
        }

        personViewHolder.circularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
        personViewHolder.circularProgressBar.setProgressBarWidth(4f);
        personViewHolder.circularProgressBar.setBackgroundProgressBarWidth(4f);
        int animationDuration = 2000;
        personViewHolder.circularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);




        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppController.getInstance().CompetitonId=meydanOkumaVo.getCompetitionId();
                AppController.getInstance().OpponentScore=meydanOkumaVo.getCompetitionScore();
                AppController.getInstance().OpponentPoint=meydanOkumaVo.getOpponentPoint();
                AppController.getInstance().selectedChallengeName =  meydanOkumaVo.getCompetitionName();
                AppController.getInstance().selectedChallengeImage =   meydanOkumaVo.getCompetitionImageUrl();
                AppController.getInstance().selectedChallengeVideoLink =   meydanOkumaVo.getCompetitionVideoUrl();

                if( AppController.getInstance().CompetitonId.equals("0")){

                    Bundle bundle = new Bundle();
                    bundle.putString("state", "1");
                    Fragment fragment = RecordChooseFragment.newInstance();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragment,DetailsFragment.FRAGMENT_TAG);
                    transaction.disallowAddToBackStack();
                    transaction.commit();

                }else{

                    Bundle bundle = new Bundle();
                    bundle.putString("CompetitionId", meydanOkumaVo.getCompetitionId());
                    bundle.putString("url", meydanOkumaVo.getCompetitionVideoUrl());
                    bundle.putString("imageurl", meydanOkumaVo.getCompetitionImageUrl());
                    bundle.putString("name", meydanOkumaVo.getCompetitionName());
                    Fragment fragment = DetailsFragment.newInstance();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragment,DetailsFragment.FRAGMENT_TAG);
                    transaction.disallowAddToBackStack();
                    transaction.commit();
                    MainMenuActivity.mStacks.get(FRAGMENTS).push(fragment);
                    MainMenuActivity.selectedFragment = DetailsFragment.newInstance();
                    MainMenuActivity.setTitle();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}