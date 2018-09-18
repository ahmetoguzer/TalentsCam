package com.technoface.app.talentscam.Adapters;

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
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Ahmet Oguzer on 30.10.2017.
 */

public class GroupPlayersAdapter extends RecyclerView.Adapter<GroupPlayersAdapter.PersonViewHolder> {

    private ArrayList myList;
    private Context context;
    private String imageurl;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        ImageView personPhoto;
        CircularProgressBar circularProgressBar;
        TextView personPuan;


        PersonViewHolder(View itemView) {
            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.txt_meydann_okuma);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            personPuan = (TextView) itemView.findViewById(R.id.person_score);
            circularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.meydan_okuma_CircularProgressbar);
        }
    }


    public GroupPlayersAdapter(Context ctx, ArrayList<MeydanOkumaVo> prgmNameList){
        this.context=ctx;
        this.myList = prgmNameList;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group_list, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        final MeydanOkumaVo meydanOkumaVo= (MeydanOkumaVo) myList.get(i);

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

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(1*1024*2014)
                .discCacheSize(2*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(imageurl, personViewHolder.personPhoto, options);


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

                if( AppController.getInstance().CompetitonId.equals("0")){

                    AppController.getInstance().goOnFragment = DetailsFragment.newInstance();
                    AppController.getInstance().CompetitonId=meydanOkumaVo.getCompetitionId();
                    AppController.getInstance().OpponentScore=meydanOkumaVo.getCompetitionScore();

                    Bundle bundle = new Bundle();
                    bundle.putString("CompetitionId", meydanOkumaVo.getCompetitionId());
                    bundle.putString("url", meydanOkumaVo.getCompetitionVideoUrl());
                    bundle.putString("imageurl", meydanOkumaVo.getCompetitionImageUrl());
                    bundle.putString("name", meydanOkumaVo.getCompetitionName());
                    Fragment fragment = DetailsFragment.newInstance();
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

                    AppController.getInstance().CompetitonId=meydanOkumaVo.getCompetitionId();
                    AppController.getInstance().OpponentScore=meydanOkumaVo.getCompetitionScore();

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