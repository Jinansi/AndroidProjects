package com.example.good.automotellogin.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.good.automotellogin.R;

/**
 * Created by GOOD on 07/04/2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.favitems, parent, false);

        return retView;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView fav_res_name = (TextView) view.findViewById(R.id.fav_res);
        TextView fav_res_location = (TextView) view.findViewById(R.id.fav_res_location);
        Log.i(getClass().getSimpleName(),"Cursor(0) "+ cursor.getColumnIndex(cursor.getColumnName(0))+ cursor.getColumnIndex("ResName"));
     //   Log.i(getClass().getSimpleName(),"name "+cursor.getColumnName())
       // fav_res_name.setText(cursor.getString(cursor.getColumnIndex("name")));
        fav_res_name.setText(cursor.getColumnIndex("ResName"));
        fav_res_location.setText(cursor.getColumnIndex("ResLocation"));
    }
}
