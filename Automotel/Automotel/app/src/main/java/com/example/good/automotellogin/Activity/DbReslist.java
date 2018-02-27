package com.example.good.automotellogin.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.good.automotellogin.Adapter.ResListAdapter2;
import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Bean.Res_list;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DbReslist extends NavDrawer implements AdapterView.OnItemClickListener {
    private String JsonResult;
    private String Url = "http://192.168.137.1/Android_Detail/Restaurantlist.php";
    private ListView Reslistview;
    List<Res_list> Reslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
    //    super.onCreateDrawer();
        Reslistview = (ListView) findViewById(R.id.res_list);
        accessWebservice();
        Reslistview.setOnItemClickListener(this);


    }



    private void accessWebservice() {
        JsonReadTask task = new JsonReadTask();
        Log.e(getClass().getSimpleName(), "JsonReadTask executed.");
        task.execute(new String[]{Url});
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Item " + (position + 1), Toast.LENGTH_LONG).show();
        Intent i = new Intent(DbReslist.this,ScrollTabsMenu.class);
        startActivity(i);
    }


    private class JsonReadTask extends AsyncTask<String, Void, String> {
        String json;
    //    public ProgressDialog dialog = new ProgressDialog();
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            BufferedReader bufferedReader;
            try {
                URL url = new URL(Url);
                connection = (HttpURLConnection) url.openConnection();
                Log.e(getClass().getSimpleName(),"Connection Established..");
                System.setProperty("http.keepAlive", "false");
                // urlConnection.setConnectTimeout(30000);
                //urlConnection.setReadTimeout(30000);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                Log.e(getClass().getSimpleName(),"Post method..");
                // urlConnection.setDoInput(true);
//                urlConnection.setRequestMethod("GET");
                connection.setDoOutput(true);


                connection.getResponseCode();

                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                // Read the stream
//                byte[] b = new byte[1024];
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     Log.e(getClass().getSimpleName(),"retrieving list..");

                while((json = bufferedReader.readLine())!= null){
                    Log.e(getClass().getSimpleName()," DAta..");
                    sb.append(json + "\n");
                }

                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(getClass().getSimpleName(),"getting null values..");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonResult=result;
            Log.e(getClass().getSimpleName(),"List Drawer class "+ JsonResult);
            ListDrawer();

        }
    }



    public void ListDrawer() {
        Log.e(getClass().getSimpleName(),"Inside ListDrawer class");
         Reslist = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(JsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("ResList");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildnode = jsonMainNode.getJSONObject(i);
                int res_id = jsonChildnode.getInt("res_id");
                String Resname = jsonChildnode.getString("restaurant");
                String location = jsonChildnode.getString("location");
                Log.e(getClass().getSimpleName(), "Get List ");

                Reslist.add(new Res_list(res_id,Resname,location,R.drawable.dislike,true));
            }
        } catch (Exception e) {
e.printStackTrace();
        }
        ArrayAdapter<Res_list> adapter = new ResListAdapter2(this,R.layout.restaurant_list,Reslist);
      //  adapter.notifyDataSetChanged();
        Reslistview.setAdapter(adapter);



    }

}
