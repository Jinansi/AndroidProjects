package com.example.good.automotellogin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.good.automotellogin.Activity.DatabaseHelper;
import com.example.good.automotellogin.Bean.Data;
import com.example.good.automotellogin.R;

import java.util.List;

/**
 * Created by GOOD on 03/02/2016.
 */
public class CustomMenuAdapter extends ArrayAdapter<Data> {
    public static final String PREFS_NAME = "ORDER_PREFS";
    public static final String PREFS_KEY = "ORDER_PREFS_String";
    Context context;
    int resource;
    List<Data> data = null;
    SharedPreferences sharedPreferences;
    DatabaseHelper orderDatabaseAdapter;
    String name;
    int quantity,price;


    public CustomMenuAdapter(Context context,int resource, List<Data> data) {
       super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public long getItemId(int position) {
        return data.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final DataHandler handler;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.menu_items,null);
            handler = new DataHandler();
            handler.textName = (TextView) row.findViewById(R.id.item_name);
      //     handler.img = (ImageView) row.findViewById(R.id.imageView);
        //    handler.imgicon1 = (ImageView) row.findViewById(R.id.imageView2);
         //   handler.imgicon2 = (ImageView) row.findViewById(R.id.imageView3);
            handler.textPrice = (TextView) row.findViewById(R.id.item_price);
         //   handler.Amount = (EditText) row.findViewById(R.id.quantity);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        final Data values = (Data) getItem(position);
     //   handler.img.setImageResource(values.icon);
        handler.textName.setText(values.title);
        handler.textPrice.setText("" + values.price);

        handler.imgicon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).counter++;
                handler.Amount.setText("" + data.get(position).counter);
                name=handler.textName.getText().toString();
                price= Integer.parseInt(handler.textPrice.getText().toString());
                quantity= Integer.parseInt(handler.Amount.getText().toString());
                addOrder();

//                SharedPreferences sharedPreferences = null;
//                Editor editor = sharedPreferences.edit();
//                editor.putString("Item",values.title);
//                editor.putInt("Price",values.price);
//                editor.putInt("counter",data.get(position).counter);
//                editor.commit();

                //     sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
                //      sharedPreferences.save(context,text);


            }
        });
        handler.imgicon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).counter > 0) {
                    int i = data.get(position).counter--;
                    handler.Amount.setText("" + i);
                }
            }
        });

        return row;

    }

    public void addOrder() {
        orderDatabaseAdapter = new DatabaseHelper(getContext());
     //   String Itemname = String.valueOf(orderDatabaseAdapter.insertData(name,price,quantity));
        Log.i(getClass().getSimpleName(), "get values.."+ name + price + quantity);

        Long id = orderDatabaseAdapter.insertOrder(quantity,price,name);
        Log.i(getClass().getSimpleName(), "Inserting data.." + id);

        if (id < 0) {
            Log.i(getClass().getSimpleName(), "Unsuccessfull");
        } else {
            Log.i(getClass().getSimpleName(), "Successfully inserted");
        }
    }
    }


//    public void save() {
//        Log.i(getClass().getSimpleName(),"Save values");
//            sharedPreferences= context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putString("Name", " ");
//       // editor.putInt("quantity"," " );
//       // editor.putInt("price", '');
//        editor.commit();
//
////    }



class DataHandler {
    ImageView  imgicon1, imgicon2;
    TextView textName, textPrice;
    EditText Amount;

}



