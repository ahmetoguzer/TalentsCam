package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Fragments.DetailsFragment;
import com.technoface.app.talentscam.Fragments.LegensDetailsFragment;
import com.technoface.app.talentscam.Fragments.RecordChooseFragment;
import com.technoface.app.talentscam.Model.ContentsModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.ContentsVo;
import com.technoface.app.talentscam.Vo.MeydanOkumaVo;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.videolan.libvlc.Dialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Ahmet Oguzer on 6.12.2017.
 */

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.PersonViewHolder> {

    private ArrayList myList;
    private static Context context;
    private String imageurl,movLink;
    public Dialog.ProgressDialog proggres;
    private ArrayList<String> arrayList;


public static class PersonViewHolder extends RecyclerView.ViewHolder {

    TextView txtcontents;
    private ViewPager pager;
    private CirclePageIndicator indicator;


    PersonViewHolder(View itemView) {
        super(itemView);
        txtcontents = (TextView)itemView.findViewById(R.id.txt_contents);
        pager = (ViewPager) itemView.findViewById(R.id.pager);
        indicator = (CirclePageIndicator)itemView.findViewById(R.id.indicator);
    }
}

    public ContentsAdapter(Context ctx, ArrayList<ContentsVo> prgmNameList){
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contents, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        final ContentsVo Vo= (ContentsVo) myList.get(i);

        personViewHolder.txtcontents.setText(Vo.getCategoryTitle());

        JSONArray jsArry=Vo.getItems();
        ContentsModel model = new ContentsModel();
        ArrayList<ContentsVo> List = model.getContents(jsArry);

        ContentPagerAdapter adapter = new ContentPagerAdapter(context,List);
        personViewHolder.pager.setAdapter(adapter);
        personViewHolder.indicator.setViewPager( personViewHolder.pager);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

}
