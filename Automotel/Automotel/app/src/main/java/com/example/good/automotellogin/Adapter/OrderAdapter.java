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
 * Created by GOOD on 06/04/2016.
 */
public class OrderAdapter extends CursorAdapter {


    public OrderAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.orderlist, parent, false);

    return retView;
}

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView order_name = (TextView) view.findViewById(R.id.order_name);
        TextView order_price = (TextView) view.findViewById(R.id.order_price);
        TextView order_quantity= (TextView) view.findViewById(R.id.order_quantity);
        Log.i(getClass().getSimpleName(), "Cursor(0) " + cursor.getColumnIndex(cursor.getColumnName(0)) + cursor.getColumnIndex(cursor.getColumnName(1)));
        order_name.setText(cursor.getColumnIndex(cursor.getColumnName(1)));
        order_price.setText(cursor.getColumnIndex(cursor.getColumnName(2)));

        order_quantity.setText(cursor.getColumnIndex(cursor.getColumnName(3)));

    }
}
