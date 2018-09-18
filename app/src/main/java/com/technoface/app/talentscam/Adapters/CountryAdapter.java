package com.technoface.app.talentscam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Vo.CountryVo;

import java.util.ArrayList;

/**
 * Created by Ahmet Oguzer on 15.01.2018.
 */

public class CountryAdapter  extends ArrayAdapter {
    private ArrayList<CountryVo> countryList ;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public CountryAdapter(Context context, int resource, ArrayList<CountryVo> _list) {
        super(context, resource);
        this.countryList = _list;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);


    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getDropDownView(position, convertView,
                parent);

        return v;
    }

    @Override
    public Object getItem(int position) {

        return countryList.get(position).getCountryName();
    }

    public Object getItem1(int position) {
        return countryList.get(position).getCountryCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {

         View  row = mLayoutInflater.inflate(R.layout.spinner_country, parent, false);

        TextView label = (TextView) row.findViewById(R.id.item);
        label.setText(countryList.get(position).getCountryName());

        return label;
    }


    public void setData(ArrayList<CountryVo> list) {
        this.countryList = list;
        notifyDataSetChanged();
    }

}
