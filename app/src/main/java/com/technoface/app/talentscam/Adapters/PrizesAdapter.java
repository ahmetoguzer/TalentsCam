package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pedro.vlc.VlcVideoLibrary;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Fragments.ChangePasswordFragment;
import com.technoface.app.talentscam.Fragments.PrizesDetayFragment;
import com.technoface.app.talentscam.Fragments.RecordChooseFragment;
import com.technoface.app.talentscam.Model.ContentsModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.ContentsVo;
import com.technoface.app.talentscam.Vo.PrizesVo;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.videolan.libvlc.Dialog;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 10.01.2018.
 */

public class PrizesAdapter extends RecyclerView.Adapter<PrizesAdapter.PersonViewHolder> {

    private ArrayList myList;
    private static Context context;
    public Dialog.ProgressDialog proggres;
    private ArrayList<String> arrayList;


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView tvCoins,tvRemaining;
        private ImageView img;


        PersonViewHolder(View itemView) {
            super(itemView);
            tvCoins = (TextView)itemView.findViewById(R.id.tvCoins);
            tvRemaining = (TextView)itemView.findViewById(R.id.tvRemaining);
            img = (ImageView)itemView.findViewById(R.id.img_thumbnail);

        }
    }

    public PrizesAdapter(Context ctx, ArrayList<PrizesVo> prgmNameList){
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prizes, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        final PrizesVo Vo= (PrizesVo) myList.get(i);

        personViewHolder.tvCoins.setText(context.getResources().getString(R.string.coins)+" : "+
        Vo.getPrizePoint());

        personViewHolder.tvRemaining.setText(context.getResources().getString(R.string.remaining)+" : "+
                Vo.getPrizeQuote());

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

        imageLoader.displayImage(Vo.getPrizeImage(), personViewHolder.img, options);

        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("image", Vo.getPrizeImage());
                bundle.putString("detail", Vo.getExtraDataMessage());
                bundle.putString("title", Vo.getPrizeTitle());
                bundle.putString("PrizeId", Vo.getPrizeId());
                Fragment fragment = PrizesDetayFragment.newInstance();
                fragment.setArguments(bundle);
                FragmentTransaction transaction1 =((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                ((AppCompatActivity) context).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction1.replace(R.id.frame_layout, fragment,PrizesDetayFragment.FRAGMENT_TAG);
                transaction1.disallowAddToBackStack();
                transaction1.commit();
                MainMenuActivity.mStacks.get("Fragments").push(fragment);
                MainMenuActivity.selectedFragment = PrizesDetayFragment.newInstance();
                MainMenuActivity.setTitle();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

}

