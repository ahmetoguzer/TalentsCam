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
import com.technoface.app.talentscam.Vo.HistoryVo;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 25.10.2017.
 */

public class HistoryDetailsAdapter extends RecyclerView.Adapter<HistoryDetailsAdapter.PersonViewHolder> {

    private ArrayList myList;
    private String imageurl;
    private Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName,tvshootingscore,tvoppenentscore;
        ImageView personPhoto,imgResult;
        CircularProgressBar circularProgressBar;


        PersonViewHolder(View itemView) {
            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            imgResult = (ImageView)itemView.findViewById(R.id.img_result);
            tvshootingscore = (TextView) itemView.findViewById(R.id.tv_shootingscore);

        }
    }


    public HistoryDetailsAdapter(Context context, ArrayList<HistoryVo> prgmNameList){
        this.myList = prgmNameList;
        this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public HistoryDetailsAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_details, viewGroup, false);
        HistoryDetailsAdapter.PersonViewHolder pvh = new HistoryDetailsAdapter.PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HistoryDetailsAdapter.PersonViewHolder personViewHolder, int i) {

        final HistoryVo historyVo= (HistoryVo) myList.get(i);

        if(!historyVo.getOpponentImageUrl().contains("https")
                && !historyVo.getOpponentImageUrl().contains("technoface")
                && !historyVo.getOpponentImageUrl().equals(""))
        {
            String[] pathArray =  historyVo.getOpponentImageUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= historyVo.getOpponentImageUrl();
        }


        personViewHolder.tvshootingscore.setText(historyVo.getShootingScore()+" - "+historyVo.getOpponentScore());

        int ss = Integer.parseInt(historyVo.getShootingScore());
        int os = Integer.parseInt(historyVo.getOpponentScore());

        if(ss > os) personViewHolder.imgResult.setImageResource(R.drawable.smile);
        if(ss < os) personViewHolder.imgResult.setImageResource(R.drawable.sad);
        if(ss == os) personViewHolder.imgResult.setImageResource(R.drawable.draw);

        personViewHolder.personName.setText(historyVo.getOpponentName());

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

        imageLoader.displayImage(imageurl, personViewHolder.personPhoto, options);


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
