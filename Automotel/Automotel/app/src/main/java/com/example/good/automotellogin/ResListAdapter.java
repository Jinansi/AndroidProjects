package com.example.good.automotellogin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Res_list;

import java.util.List;

/**
 * Created by GOOD on 29/02/2016.
 */
public class ResListAdapter extends ArrayAdapter<Res_list> {
    Context context;
    int resource;
    List<Res_list> list;
    Dataholder1 dataholder= null;

    public ResListAdapter(Context context, int resource,List<Res_list> list) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }




    @Override
    public int getCount() {
        return list.size();
    }



    @Override

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row=convertView;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.restaurant_list,parent,false);
            dataholder = new Dataholder1(row);
            //           dataholder.res_title = (TextView) row.findViewById(R.id.res_name);
//            dataholder.res_location = (TextView) row.findViewById(R.id.res_location);
//            dataholder.liked= (ImageView) row.findViewById(R.id.liked);
//            dataholder.unliked = (ImageView) row.findViewById(R.id.unliked);
            row.setTag(dataholder);
        }else{
            dataholder = (Dataholder1) row.getTag();
        }
        Res_list temp = list.get(position);
        if(list.get(position).isFlag()) {
            dataholder.unliked.setImageResource(R.drawable.dislike);
        }
        else{
            dataholder.unliked.setImageResource(R.drawable.likefilled);
        }
        dataholder.unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).isFlag()){
                    list.get(position).setFlag(false);
                    dataholder.unliked.setImageResource(R.drawable.likefilled);
                    adduser();
                    Log.i(getClass().getSimpleName(), "Creating Database..");
                }
                else{
                    list.get(position).setFlag(true);
                    dataholder.unliked.setImageResource(R.drawable.dislike);
                }

            }
        });
        dataholder.res_title.setText(temp.title);
        dataholder.res_location.setText(temp.location);
        return row;
    }
    public class Dataholder1 {
        TextView res_title,res_location;
        ImageView unliked;

        Dataholder1(View v){
            res_title = (TextView) v.findViewById(R.id.res_name);
            res_location = (TextView) v.findViewById(R.id.res_location);
            unliked = (ImageView) v.findViewById(R.id.unliked);

        }


    }
    public void adduser(){
        //Long id = favdb.insertData(user);
        String saveres_name = dataholder.res_title.getText().toString();
        String savelocation = dataholder.res_location.getText().toString();
//        String user = username.getText().toString();
//        Long id = favdb.insertData(user);
        Log.i(getClass().getSimpleName(), "Inserting data.." + savelocation + saveres_name);
//        if(id<0){
//            Log.i(getClass().getSimpleName(),"Unsuccessfull");
//        }else{
//            Log.i(getClass().getSimpleName(),"Successfully inserted");
//        }

    }


}

