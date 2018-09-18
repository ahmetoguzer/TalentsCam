package com.technoface.app.talentscam.Adapters;

/**
 * Created by Ahmet Oguzer on 23.10.2017.
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
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Fragments.DetailsFragment;
import com.technoface.app.talentscam.Fragments.RecordChooseFragment;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;

import java.util.ArrayList;

public class BestPlayersAdapter extends RecyclerView.Adapter<BestPlayersAdapter.PersonViewHolder> {

    private ArrayList myList;
    private Context context;
    private String imageurl;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        ImageView personPhoto;
        CircularProgressBar circularProgressBar;
        ImageView imgPlus;

        PersonViewHolder(View itemView) {
            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            imgPlus = (ImageView) itemView.findViewById(R.id.img_plus);
            circularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.meydan_okuma_CircularProgressbar);
        }
    }


    public BestPlayersAdapter(Context ctx,ArrayList<MeydanOkumaVo> prgmNameList){
        this.context=ctx;
        this.myList = prgmNameList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_best_player, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        final MeydanOkumaVo meydanOkumaVo= (MeydanOkumaVo) myList.get(i);

        if(i==0){
            personViewHolder.imgPlus.setVisibility(View.VISIBLE);
        }else{
            personViewHolder.imgPlus.setVisibility(View.GONE);
        }

        personViewHolder.personName.setText(meydanOkumaVo.getCompetitionName());


        if(!meydanOkumaVo.getCompetitionImageUrl().contains("https")
                && !meydanOkumaVo.getCompetitionImageUrl().contains("technoface")
                && !meydanOkumaVo.getCompetitionImageUrl().equals(""))
        {
            String[] pathArray =  meydanOkumaVo.getCompetitionImageUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= meydanOkumaVo.getCompetitionImageUrl();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(50*1024*2014)
                .discCacheSize(50*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(imageurl, personViewHolder.personPhoto, options);


//        Picasso.with(context).load(imageurl).into( personViewHolder.personPhoto);

        if(meydanOkumaVo.getCompetitionImageUrl().equals(" ")){
            personViewHolder.personPhoto.setImageResource(R.drawable.defaultprofile);
        }


        personViewHolder.circularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
        personViewHolder.circularProgressBar.setProgressBarWidth(4f);
        personViewHolder.circularProgressBar.setBackgroundProgressBarWidth(4f);
        int animationDuration = 2000; // 2500ms = 2,5s
        personViewHolder.circularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);

        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().CompetitonId=meydanOkumaVo.getCompetitionId();
                AppController.getInstance().OpponentScore=meydanOkumaVo.getCompetitionScore();

                AppController.getInstance().selectedChallengeName =  meydanOkumaVo.getCompetitionName();
                AppController.getInstance().selectedChallengeImage =   meydanOkumaVo.getCompetitionImageUrl();
                AppController.getInstance().selectedChallengeVideoLink =   meydanOkumaVo.getCompetitionVideoUrl();

                if( AppController.getInstance().CompetitonId.equals("0")){
                    Bundle bundle = new Bundle();
                    bundle.putString("state", "1");
                    Fragment fragment = RecordChooseFragment.newInstance();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragment);
                    transaction.commit();


//                    Intent myIntent = new Intent(context,
//                            DuelloActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    myIntent.putExtra("CompetitionId",meydanOkumaVo.getCompetitionId());
//                    myIntent.putExtra("url",meydanOkumaVo.getCompetitionVideoUrl());
//                    myIntent.putExtra("imageurl",meydanOkumaVo.getCompetitionImageUrl());
//                    myIntent.putExtra("name",meydanOkumaVo.getCompetitionName());
//                    v.getContext().startActivity(myIntent);
                }else{


                    Bundle bundle = new Bundle();
                    bundle.putString("CompetitionId", meydanOkumaVo.getCompetitionId());
                    bundle.putString("url", meydanOkumaVo.getCompetitionVideoUrl());
                    bundle.putString("imageurl", meydanOkumaVo.getCompetitionImageUrl());
                    bundle.putString("name", meydanOkumaVo.getCompetitionName());
                    Fragment fragment = DetailsFragment.newInstance();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

//                    Intent myIntent = new Intent(context,
//                            DuelloPreActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    myIntent.putExtra("CompetitionId",meydanOkumaVo.getCompetitionId());
//                    myIntent.putExtra("url",meydanOkumaVo.getCompetitionVideoUrl());
//                    myIntent.putExtra("imageurl",meydanOkumaVo.getCompetitionImageUrl());
//                    myIntent.putExtra("name",meydanOkumaVo.getCompetitionName());
//                    v.getContext().startActivity(myIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}