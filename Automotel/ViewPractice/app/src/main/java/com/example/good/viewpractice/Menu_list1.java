package com.example.good.viewpractice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class Menu_list1 extends AppCompatActivity {
//        String[] list={"value1","two","three"};
        ListView listviewMenu;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list1);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Data item_data[] = new Data[]{
                new Data(R.drawable.image1,"Burger",30),
                new Data(R.drawable.image1,"Pizza",70),
                new Data(R.drawable.image1,"HotDog",50)
        };
        CustomValues adapter = new CustomValues(this,R.layout.items,item_data);
        listviewMenu = (ListView) findViewById(R.id.listView);
    //    View header =(View) getLayoutInflater().inflate(R.layout.header,null);
      //  listviewMenu.addHeaderView(header);
        listviewMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//          Quantity.setOnClickListener();
//        listview= (ListView) findViewById(R.id.listView);
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
//        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            switch (position)
//            {
//                case 0:
//                    Intent i = new Intent(getApplicationContext(),Activity2.class);
//                    startActivity(i);
//                    break;
//            }
//    }
}
