package com.example.good.automotellogin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.good.automotellogin.Activity.DatabaseHelper;
import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Bean.Res_list;

import java.util.List;

/**
 * Created by GOOD on 29/02/2016.
 */
public class ResListAdapter2 extends ArrayAdapter<Res_list> {
    DatabaseHelper favdb;
    Context context;
    int resource;
    List<Res_list> list;
    String name;
    String location;
    int index;


    public ResListAdapter2(Context context, int resource, List<Res_list> list) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.list = list;
   //     sharedPreferances = new SharedPreferances();
    }
    public void setIndex(int index){
        this.index = index;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override

    public long getItemId(int position) {
        return list.get(position).res_id;
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
        Res_list temp = list.get(position);
        if (list.get(position).isFlag()) {
            dataholder.unliked.setImageResource(R.drawable.dislike);
            dataholder.unliked.setTag("dislike");
        } else {
            dataholder.unliked.setImageResource(R.drawable.likefilled);
            dataholder.unliked.setTag("liked");
        }
        dataholder.unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isFlag()) {
                    list.get(position).setFlag(false);
                    dataholder.unliked.setImageResource(R.drawable.likefilled);
                    name = dataholder.res_title.getText().toString();
                   location = dataholder.res_location.getText().toString();

                    adduser();
                    Log.i(getClass().getSimpleName(), "Creating Database..");
                } else {
                    list.get(position).setFlag(true);
                    dataholder.unliked.setImageResource(R.drawable.dislike);
                }
            }

        });

        dataholder.res_title.setText(temp.title);
        dataholder.res_location.setText(temp.location);
        return row;
    }

//    }   /*Checks whether a particular product exists in SharedPreferences*/
//    public boolean checkFavoriteItem(Res_list checkRestaurant) {
//        boolean check = false;
//        List<Res_list> favorites = sharedPreferances.getFavorites(context);
//        if (favorites != null) {
//            for (Res_list restaurant : favorites) {
//                if (restaurant.equals(checkRestaurant)) {
//                    check = true;
//                    break;
//                }
//            }
//        }
//        return check;
 //   }

//    @Override
//    public void add(Res_list object) {
//        super.add(object);
//        list.add(object);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void remove(Res_list object) {
//        super.remove(object);
//        list.remove(object);
//        notifyDataSetChanged();
//    }

    public void adduser() {

//        String name = dataholder.res_title.getText().toString();
//        String location = dataholder.res_location.getText().toString();
//        String user = username.getText().toString();
        favdb = new DatabaseHelper(getContext());
        Log.i(getClass().getSimpleName(), "get values.." + name + location);

         favdb.insertData(name, location);
        Log.i(getClass().getSimpleName(), "Inserting data.." + location + name);

//        if (id < 0) {
//            Log.i(getClass().getSimpleName(), "Unsuccessfull");
//        } else {
//            Log.i(getClass().getSimpleName(), "Successfully inserted");
//        }
    }

}