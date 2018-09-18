package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Vo.GrupVo;
import com.technoface.app.talentscam.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ahmet on 5.9.2017.
 */
public class GrupListAdapter extends BaseAdapter {

    // Declare Variables
    private Context mContext;
    private LayoutInflater inflater;
    private List<GrupVo> mylist = null;
    private ArrayList<GrupVo> arraylist;

    public GrupListAdapter(Context context, List<GrupVo> countrylist) {
        mContext = context;
        this.mylist = countrylist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<GrupVo>();
        this.arraylist.addAll(mylist);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public GrupVo getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final GrupVo grupVo=  mylist.get(position);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_search_list, null);
            holder.country = (TextView) view.findViewById(R.id.tv_grupName);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.country.setText(grupVo.getGroupName());

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AppController.getInstance().grupid=grupVo.getGroupId();
                Intent intent = new Intent(mContext, MainMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("state","1");
                intent.putExtra("groupid",grupVo.getGroupId());
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        ViewHolder holder = new ViewHolder();
        charText = charText.toLowerCase(Locale.getDefault());
        mylist.clear();
        if (charText.length() == 0) {
            mylist.addAll(arraylist);
        }
        else
        {
            for (GrupVo wp : arraylist)
            {
                if (wp.getGroupName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mylist.add(wp);

                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {

        TextView country;

    }

}