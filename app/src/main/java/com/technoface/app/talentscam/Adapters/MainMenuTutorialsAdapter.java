package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;

import com.technoface.app.talentscam.Activities.MainMenuTutorialsActivity;
import com.technoface.app.talentscam.R;

/**
 * Created by Ahmet Oguzer on 21.11.2017.
 */

public class MainMenuTutorialsAdapter  extends PagerAdapter {
    private Context ctx;
    private int numberOfRes = 3;
    private MainMenuTutorialsActivity mActivity;
    private int[] res = {R.drawable.bilgilendirme2,
            R.drawable.bilgilendirme3,
            R.drawable.bilgilendirme4,
    };

    public MainMenuTutorialsAdapter(Context ctx) {
        this.ctx = ctx;
        mActivity = new MainMenuTutorialsActivity();
    }

    @Override
    public int getCount() {
        return numberOfRes;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item_begin_tutorials, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.ivpager);

        imageView.setImageDrawable(null);

        imageView.setImageResource(res[position]);
        container.addView(v, 0);

        if(position==2){
            mActivity.btnGo.setVisibility(View.VISIBLE);
        }



        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewGroup) container).removeView((View) object);
    }

    public void destroyItem(View container, int position, Object object) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}