package com.example.good.viewpractice;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by GOOD on 03/02/2016.
 */
public class CustomValues extends ArrayAdapter<Data> {

    Context context;
    int resource;
    Data[] data = null;

    public CustomValues(Context context, int resource, Data[] data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final DataHandler handler;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            handler = new DataHandler();
            handler.textName = (TextView) row.findViewById(R.id.item_name);
            handler.img = (ImageView) row.findViewById(R.id.imageView);
            handler.imgicon1 = (ImageView) row.findViewById(R.id.imageView2);
            handler.imgicon2 = (ImageView) row.findViewById(R.id.imageView3);
            handler.textPrice = (TextView) row.findViewById(R.id.item_price);
            handler.Amount = (EditText) row.findViewById(R.id.quantity);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        Data values = data[position];
        handler.img.setImageResource(values.icon);
        handler.textName.setText(values.title);
        handler.textPrice.setText("" + values.price);

        handler.imgicon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data[position].counter++;
                handler.Amount.setText("" + data[position].counter);

            }
        });
        handler.imgicon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data[position].counter > 0) {
                    data[position].counter--;
                    handler.Amount.setText("" + data[position].counter);
                }
            }
        });

        return row;

    }


    private class DataHandler {
        ImageView img, imgicon1, imgicon2;
        TextView textName, textPrice;
        EditText Amount;

    }


}

class Data {
    public int icon;
    public String title;
    public int price;
    public int counter;

    public Data(int icon, String title, int price) {
        super();
        this.icon = icon;
        this.title = title;
        this.price = price;
    }

}


