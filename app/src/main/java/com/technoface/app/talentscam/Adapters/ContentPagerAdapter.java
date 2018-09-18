package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Fragments.LegensDetailsFragment;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.ContentsVo;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 8.12.2017.
 */

public class ContentPagerAdapter extends PagerAdapter {
    private Context ctx;

    private ArrayList<ContentsVo> mylist;

    public ContentPagerAdapter(Context ctx,ArrayList<ContentsVo> mylist) {
        this.mylist = mylist;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item_content_pager, null);

        final ContentsVo vo= (ContentsVo) mylist.get(position);

        ImageView imageView = (ImageView) v.findViewById(R.id.ivpager);
        TextView tv = (TextView) v.findViewById(R.id.tvdetail);
        tv.setText(vo.getContentTitle());


        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx)
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

        imageLoader.displayImage(vo.getContentImage(), imageView, options);
        container.addView(v);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().youtubeLink=vo.getContentVideo();
                AppController.getInstance().ContentId=vo.getContentId();
                AppController.getInstance().ContentDetail=vo.getContentDetail();
                AppController.getInstance().ContentTitle=vo.getContentTitle();

                Fragment fragment = LegensDetailsFragment.newInstance();
                FragmentTransaction transaction = ((AppCompatActivity) ctx).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment,LegensDetailsFragment.FRAGMENT_TAG);
                transaction.disallowAddToBackStack();
                transaction.commit();
                MainMenuActivity.mStacks.get("Fragments").push(fragment);
            }
        });
        return v;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}