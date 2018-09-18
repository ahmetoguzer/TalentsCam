package com.technoface.app.talentscam.Adapters;

/**
 * Created by Ahmet on 25.5.2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.LigVo;

import java.util.ArrayList;

public class RakingAdapter extends BaseAdapter{

    ArrayList mylist;
    Context context;
    private static LayoutInflater inflater=null;
    private ArrayList<String> imageList ;
    private String imageurl;

    public RakingAdapter(Context mainActivity, ArrayList<LigVo> list) {
        // TODO Auto-generated constructor stub
        mylist=list;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        TextView tvraking;
        TextView tvpuan;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;


        final LigVo ligVo = (LigVo) mylist.get(position);

        rowView = inflater.inflate(R.layout.item_raking, null);

        holder.tvpuan=(TextView) rowView.findViewById(R.id.txt_profile_puan);
        holder.tvraking=(TextView) rowView.findViewById(R.id.txt_rak);
        holder.tv=(TextView) rowView.findViewById(R.id.txt_profile_name_pre_screen);
        holder.img=(ImageView) rowView.findViewById(R.id.img_profile_pre_screen);


        holder.tv.setText(ligVo.getUserName());
        holder.tvpuan.setText(ligVo.getToplamPuan());


        if(!ligVo.getUserAvatarUrl().contains("https")
                &&  !ligVo.getUserAvatarUrl().contains("technoface")
                &&  !ligVo.getUserAvatarUrl().equals(""))
        {
            String[] pathArray =   ligVo.getUserAvatarUrl().split("\\:");
            imageurl=pathArray[0]+"s:"+pathArray[1];
        }else{
            imageurl= ligVo.getUserAvatarUrl();
        }

        // image caching
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

        imageLoader.displayImage(imageurl, holder.img, options);

        int pos = position+1;
        holder.tvraking.setText(pos+".");
        pos++;

        if(position%2==0){
            holder.img.setBackgroundResource(R.drawable.circlered);
        }else{
            holder.img.setBackgroundResource(R.drawable.circleblue);
        }
//        holder.img.setImageResource((Integer) imageId.get(position));


        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+mylist.get(position), Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

}