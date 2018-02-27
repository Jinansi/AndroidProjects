package com.example.good.automotellogin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.good.automotellogin.ResListAdapter;
import com.example.good.automotellogin.R;

public class RestaurantList extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_restaurant_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        Res_list res_list[] = new Res_list[]{
//                new Res_list(R.drawable.unliked, R.drawable.liked, true, "City Lake", "Maninagar")
//
//        };
        ResListAdapter adapter = new ResListAdapter(getApplicationContext(),R.layout.menu_items,null);
        listView = (ListView) findViewById(R.id.res_list);
        listView.setAdapter(adapter);

    }
    public void onItemClick(AdapterView<?> parent, View view,int position,int longID)
    {
        Toast.makeText(getApplicationContext(),"Item " + (position+1),Toast.LENGTH_LONG).show();
        Intent i = new Intent(RestaurantList.this,ScrollTabsMenu.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds menu_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_list, menu);
        return true;
    }
//    public boolean OnPrepareOptionsMenu(Menu menu)
//    {
//        mSearchAction=menu.findItem(R.id.action_search);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

