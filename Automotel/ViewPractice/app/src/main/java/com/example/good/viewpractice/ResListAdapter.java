package com.example.good.viewpractice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 10/02/2016.
 */
public class ResListAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<Res_list> list;
        public ResListAdapter(Context context){
            this.context=context;
            list = new ArrayList<>();
            String[] Res_title = context.getResources().getStringArray(R.array.RestaurantList);
            String[] Res_location = context.getResources().getStringArray(R.array.ResLocation);

            int i=0;
            for (String rece:Res_title){

                Res_list data = new Res_list(Res_title[i],Res_location[i],R.drawable.unliked,true);
                list.add(data);
                i++;
            }

        }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            View row=convertView;
        final Dataholder dataholder;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.restaurant_list,parent,false);
            dataholder = new Dataholder(row);
//            dataholder.res_title = (TextView) row.findViewById(R.id.res_name);
//            dataholder.res_location = (TextView) row.findViewById(R.id.res_location);
//            dataholder.liked= (ImageView) row.findViewById(R.id.liked);
//            dataholder.unliked = (ImageView) row.findViewById(R.id.unliked);
            row.setTag(dataholder);
        }else{
            dataholder = (Dataholder) row.getTag();
        }
        Res_list temp = list.get(position);
       if(list.get(position).isFlag()) {
           dataholder.unliked.setImageResource(R.drawable.unliked);
       }
        else{
           dataholder.unliked.setImageResource(R.drawable.liked);
       }
       dataholder.unliked.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(list.get(position).isFlag()){
                   list.get(position).setFlag(false);
                   dataholder.unliked.setImageResource(R.drawable.liked);
               }
                else{
                   list.get(position).setFlag(true);
                   dataholder.unliked.setImageResource(R.drawable.unliked);
               }

           }
       });
        dataholder.res_title.setText(temp.title);
        dataholder.res_location.setText(temp.location);
        return row;
    }

    public class Dataholder {
        TextView res_title,res_location;
        ImageView unliked;

        Dataholder(View v){
            res_title = (TextView) v.findViewById(R.id.res_name);
            res_location = (TextView) v.findViewById(R.id.res_location);
            unliked = (ImageView) v.findViewById(R.id.unliked);

        }

    }
}
