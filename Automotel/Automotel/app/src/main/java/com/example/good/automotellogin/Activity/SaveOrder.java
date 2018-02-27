package com.example.good.automotellogin.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.good.automotellogin.Adapter.OrderAdapter;
import com.example.good.automotellogin.R;

/**
 * Created by GOOD on 08/03/2016.
 */
public class SaveOrder extends AppCompatActivity {
    SharedPreferences sharedPreferences;
 //   ArrayAdapter adapter;
        DatabaseHelper orderDatabaseAdapter;
//    SharedPreferences.Editor ed;

    ListView listView;
    TextView order;

    public static final String PREFS_NAME = "ORDER_PREFS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_order);
    //    listView = (ListView) findViewById(R.id.save_menu);
        //Define a HashMap object
        Bundle bundle = getIntent().getExtras();
        String item_name = bundle.getString("data");





        orderDatabaseAdapter = new DatabaseHelper(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                OrderAdapter  orderAdapter= new OrderAdapter(SaveOrder.this,orderDatabaseAdapter.getData());
                listView.setAdapter(orderAdapter);
            }
        });



    }
}
