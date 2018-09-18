package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technoface.app.talentscam.Model.ContentsModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.ContentsVo;
import com.technoface.app.talentscam.Vo.MyPointsVo;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.videolan.libvlc.Dialog;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 18.01.2018.
 */

public class MyPointsAdapter extends RecyclerView.Adapter<MyPointsAdapter.PersonViewHolder> {

    private ArrayList myList;
    private static Context context;



    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView tvPointName,tvPoint,tvDate,tvPointDescription;
        LinearLayout layoutMyPoints;


        PersonViewHolder(View itemView) {
            super(itemView);
            tvPointName = (TextView)itemView.findViewById(R.id.tvPointName);
            tvPoint = (TextView)itemView.findViewById(R.id.tvPoint);
            tvDate = (TextView)itemView.findViewById(R.id.tvDate);
            tvPointDescription = (TextView)itemView.findViewById(R.id.tvPointDescription);
            layoutMyPoints = (LinearLayout)itemView.findViewById(R.id.layoutMyPoints);

        }
    }

    public MyPointsAdapter(Context ctx, ArrayList<MyPointsVo> prgmNameList){
        this.context=ctx;
        this.myList = prgmNameList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mypoints, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        final MyPointsVo Vo= (MyPointsVo) myList.get(i+1);

        personViewHolder.tvPointName.setText(Vo.getPointName());
        personViewHolder.tvPoint.setText(Vo.getPoint());
        personViewHolder.tvDate.setText(Vo.getPointDate());
        personViewHolder.tvPointDescription.setText(Vo.getPointDescription());

        int tmp = Integer.valueOf(Vo.getPoint());

        if(tmp<0){
            personViewHolder.layoutMyPoints.setBackgroundColor(Color.parseColor("#8b103f"));
        }else{
            personViewHolder.layoutMyPoints.setBackgroundColor(Color.parseColor("#4c59d8"));
        }

    }

    @Override
    public int getItemCount() {
        return myList.size()-1;
    }

}
