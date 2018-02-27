package com.example.good.automotellogin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.good.automotellogin.Activity.AddRemoveView;
import com.example.good.automotellogin.Activity.DatabaseHelper;
import com.example.good.automotellogin.Adapter.Listeners.AddRemoveItemListener;
import com.example.good.automotellogin.Bean.Data;
import com.example.good.automotellogin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 08/04/2016.
 */
public class CustomMenuAdapter3 extends ArrayAdapter<Data> implements AddRemoveItemListener {
    Context context;

    List<Data> data;
    SharedPreferences sharedPreferences;
    DatabaseHelper orderDatabaseAdapter;
    String name;
    int quantity,price;
    int resource;

    public CustomMenuAdapter3(Context context, int resource, List<Data> data) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.data=data;
        Log.i("CustomMenuAdapter3", data.get(0).title);
      //  init();
    }



    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public long getItemId(int position) {
        return data.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        //init();
        final DataHandler handler;
        AddRemoveView button;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.menu_items,null,true);
            handler = new DataHandler();
            handler.textName = (TextView) row.findViewById(R.id.item_name);
            button = (AddRemoveView) row.findViewById(R.id.add_remove_button);
            button.setAddRemoveItemListener(position, this);
            //    handler.img = (ImageView) row.findViewById(R.id.imageView);
        //    handler.imgicon1 = (ImageView) row.findViewById(R.id.imageView2);
         //   handler.imgicon2 = (ImageView) row.findViewById(R.id.imageView3);
            handler.textPrice = (TextView) row.findViewById(R.id.item_price);
        //    handler.Amount = (EditText) row.findViewById(R.id.quantity);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        final Data values = data.get(position);
        //   handler.img.setImageResource(values.icon);
        handler.textName.setText(values.title);
        handler.textPrice.setText("" + values.price);

//        handler.imgicon1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                data.get(position).counter++;
//                handler.Amount.setText("" + data.get(position).counter);
//                name = handler.textName.getText().toString();
//                price = Integer.parseInt(handler.textPrice.getText().toString());
//                quantity = Integer.parseInt(handler.Amount.getText().toString());
//                addOrder();
//
//                SharedPreferences sharedPreferences = null;
//                Editor editor = sharedPreferences.edit();
//                editor.putString("Item",values.title);
//                editor.putInt("Price",values.price);
//                editor.putInt("counter",data.get(position).counter);
//                editor.commit();

                //     sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
                //      sharedPreferences.save(context,text);


//            }
//        });
//        handler.imgicon2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.get(position).counter > 0) {
//                    int i = data.get(position).counter--;
//                    handler.Amount.setText("" + i);
//                }
//            }
//        });

        return row;

    }

    private void addOrder() {
        orderDatabaseAdapter = new DatabaseHelper(getContext());
        //   String Itemname = String.valueOf(orderDatabaseAdapter.insertData(name,price,quantity));
        Log.i(getClass().getSimpleName(), "get values.." + name + price + quantity);

        Long id = orderDatabaseAdapter.insertOrder(quantity,price,name);
        Log.i(getClass().getSimpleName(), "Inserting data.." + id);

        if (id < 0) {
            Log.i(getClass().getSimpleName(), "Unsuccessfull");
        } else {
            Log.i(getClass().getSimpleName(), "Successfully inserted");
        }
    }


    @Override
    public void onItemChanged(int position, int count) {
        data.get(position).counter = count;
    }

    public List<Data> getData(){
        return data;
    }
}


