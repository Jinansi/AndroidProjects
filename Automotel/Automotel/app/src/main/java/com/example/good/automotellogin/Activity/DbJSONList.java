package com.example.good.automotellogin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.good.automotellogin.Adapter.ResListAdapter2;
import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Bean.Res_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by GOOD on 08/03/2016.
 */
public class DbJSONList extends AppCompatActivity {
    private ListView Reslistview;
    List<Res_list> Reslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        Reslistview = (ListView) findViewById(R.id.res_list);
        Reslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item " + (position + 1), Toast.LENGTH_LONG).show();
                Intent i = new Intent(DbJSONList.this,ScrollTabsMenu.class);
                startActivity(i);
            }
        });
        Reslist = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(getAssets().open("get_hotels_response.json")));
            String temp;
            while((temp = br.readLine()) != null){
            sb.append(temp);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        String myjson = sb.toString();
        try{
            JSONObject jsonmain = new JSONObject(myjson);
            JSONArray jsonArray = jsonmain.getJSONArray("hotels");
            for(int i=0;i<=jsonArray.length();i++){
                JSONObject jsonChildnode = jsonArray.getJSONObject(i);
                int res_id = jsonChildnode.getInt("_id");
                String Resname = jsonChildnode.getString("name");
                String location = jsonChildnode.getString("address");
                Log.e(getClass().getSimpleName(), "Get List ");
                Reslist.add(new Res_list(res_id,Resname, location, R.drawable.dislike, true));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Res_list> adapter = new ResListAdapter2(this,R.layout.restaurant_list,Reslist);
        //  adapter.notifyDataSetChanged();
        Reslistview.setAdapter(adapter);
    }

}
