package com.example.good.automotellogin.Activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.good.automotellogin.Adapter.CustomCursorAdapter;
import com.example.good.automotellogin.Adapter.FavoriteAdapter;
import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Res_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 07/03/2016.
 */
public class Favorites extends AppCompatActivity {
    DatabaseHelper favdb;
    private ListView FavReslistview;
//    ArrayAdapter<String> adapter;
    CustomCursorAdapter customCursorAdapter;
    String data[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
        FavReslistview= (ListView) findViewById(R.id.fav_list);
        favdb = new DatabaseHelper(this);
   //     data = new String[]{favdb.getAllFav()};

        new Handler().post(new Runnable() {
            @Override
            public void run() {
               customCursorAdapter = new CustomCursorAdapter(Favorites.this, favdb.getAllFav());
                FavReslistview.setAdapter(customCursorAdapter);
            }
        });
//        List<Res_list> data = new ArrayList<>();
//        data.add(new Res_list(favdb.displayfavdata());
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Favorites.this,android.R.layout.simple_list_item_1,data);
//            setListAdapter(adapter);
//        adapter = new FavoriteAdapter(this,R.layout.restaurant_list,data);
//        FavReslistview.setAdapter(adapter);
    }
}
