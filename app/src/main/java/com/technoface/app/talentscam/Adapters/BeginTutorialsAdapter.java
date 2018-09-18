package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v4.view.PagerAdapter;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.ActivitySplashScreen;
import com.technoface.app.talentscam.Activities.BeginTutorialsActivity;
import com.technoface.app.talentscam.Activities.LoginActivity;
import com.technoface.app.talentscam.Activities.MainMenuTutorialsActivity;
import com.technoface.app.talentscam.R;

/**
 * Created by Ahmet Oguzer on 21.11.2017.
 */

public class BeginTutorialsAdapter extends PagerAdapter {
    private Context ctx;
    private int numberOfRes = 5;
    private BeginTutorialsActivity mActivity;
    private int[] res = {R.drawable.tutorials1,
            R.drawable.tutorials2,
            R.drawable.tutorials3,
            R.drawable.tutorials4,
            R.drawable.tutorials5
           };

    public BeginTutorialsAdapter(Context ctx) {
        this.ctx = ctx;
        mActivity = new BeginTutorialsActivity();
    }

    @Override
    public int getCount() {
        return numberOfRes;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item_begin_tutorials, null);

        TextView tvTutorial = (TextView) v.findViewById(R.id.tv_tutorial);
        ImageView imageView = (ImageView) v.findViewById(R.id.ivpager);


        if(position==0){
            tvTutorial.setText("Artık basketbol sahasında yalnız değilsiniz");
        }else if(position==1){
            imageView.setImageDrawable(null);
            tvTutorial.setText("Attığınız inanılmaz basketleri tüm dünya görsün istiyorsanız");
        }else if(position==2){
            imageView.setImageDrawable(null);
            tvTutorial.setText("Arkadaşlarınızla şut yarışması yapmak");
        }else if(position==3){
            imageView.setImageDrawable(null);
            tvTutorial.setText("Dünya genelindeki en iyileri yenmek istiyorsanız");
        }else if(position==4){
            imageView.setImageDrawable(null);
            tvTutorial.setText("Uygulamamızı kullanmaya başlayabilirsiniz...");
        }

        imageView.setImageResource(res[position]);
        container.addView(v, 0);

        if(position==4){
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