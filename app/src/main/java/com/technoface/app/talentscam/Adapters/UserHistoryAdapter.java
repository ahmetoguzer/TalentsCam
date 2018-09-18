package com.technoface.app.talentscam.Adapters;

import android.content.Context;
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
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.KarsilasmaHistoryVo;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 9.11.2017.
 */

public class UserHistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.PersonViewHolder> {

private ArrayList myList;
private String imageurl;
private Context context;

public static class PersonViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView personName,tvscore,tvHistory;
    ImageView personPhoto;
    CircularProgressBar circularProgressBar;


    PersonViewHolder(View itemView) {
        super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
        personName = (TextView)itemView.findViewById(R.id.person_name);
        personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        tvscore = (TextView) itemView.findViewById(R.id.tv_score);
        circularProgressBar = (CircularProgressBar)itemView.findViewById(R.id.meydan_okuma_CircularProgressbar);
    }
}


    public UserHistoryAdapter(Context context , ArrayList<KarsilasmaHistoryVo> prgmNameList){
        this.myList = prgmNameList;
        this.context =context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HistoryAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player_list, viewGroup, false);
        HistoryAdapter.PersonViewHolder pvh = new HistoryAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.PersonViewHolder personViewHolder, final int i) {

        final KarsilasmaHistoryVo karsilasmaHistoryVo = (KarsilasmaHistoryVo) myList.get(i);

//        personViewHolder.personName.setText(karsilasmaHistoryVo.getOpponentName());
        personViewHolder.tvscore.setText(karsilasmaHistoryVo.getOpponentScore());

        if(!karsilasmaHistoryVo.getOpponentImageUrl().contains("https")
                && !karsilasmaHistoryVo.getOpponentImageUrl().contains("technoface")
                && !karsilasmaHistoryVo.getOpponentImageUrl().equals(""))
        {
            String[] pathArray =  karsilasmaHistoryVo.getOpponentImageUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= karsilasmaHistoryVo.getOpponentImageUrl();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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

//        imageLoader.displayImage(imageurl, personViewHolder.personPhoto, options);
//
//
//        personViewHolder.circularProgressBar.setBackgroundColor(Color.parseColor("#04c528"));
//        personViewHolder.circularProgressBar.setProgressBarWidth(4f);
//        personViewHolder.circularProgressBar.setBackgroundProgressBarWidth(4f);
//        int animationDuration = 2000; // 2500ms = 2,5s
//        personViewHolder.circularProgressBar.setProgressWithAnimation(Float.valueOf(0), animationDuration);



    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


}
