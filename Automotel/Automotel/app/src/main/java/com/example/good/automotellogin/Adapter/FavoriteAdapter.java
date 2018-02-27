package com.example.good.automotellogin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.good.automotellogin.Bean.Res_list;
import com.example.good.automotellogin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 06/04/2016.
 */
public class FavoriteAdapter extends ArrayAdapter<String> {
    private ArrayList<String> fav_res_name;
    private ArrayList<String> fav_res_location;
    Context context;
    public FavoriteAdapter(Context context, int resource,ArrayList<String> fav_res_name,ArrayList<String> fav_res_location) {
        super(context, resource);
        this.fav_res_name = fav_res_name;
        this.fav_res_location = fav_res_location;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final Dataholder dataholder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.restaurant_list, parent, false);
            dataholder = new Dataholder(row);
            //           dataholder.res_title = (TextView) row.findViewById(R.id.res_name);
//            dataholder.res_location = (TextView) row.findViewById(R.id.res_location);
//            dataholder.liked= (ImageView) row.findViewById(R.id.liked);
//            dataholder.unliked = (ImageView) row.findViewById(R.id.unliked);
            row.setTag(dataholder);
        } else {
            dataholder = (Dataholder) row.getTag();
        }

        dataholder.res_title.setText(fav_res_name.get(position));
        dataholder.res_location.setText(fav_res_location.get(position));
        return row;


    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
