package com.example.good.automotellogin.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.good.automotellogin.Adapter.CustomMenuAdapter;
import com.example.good.automotellogin.Bean.Data;
import com.example.good.automotellogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOOD on 15/03/2016.
 */
public class MenuFragment extends ListFragment {
        int resource;
    String itemname;
    ListAdapter adapter;
    int index;
    private String JsonMenu;
    private String Url = "http://192.168.137.1/Android_Detail/menu3.php";
    List<Data> items = new ArrayList<Data>();
    public MenuFragment() {

    }
    public void setIndex(int index){
        this.index = index;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        accesswebservice();
        Log.i(getClass().getSimpleName(),"After accessing web service");
        adapter = new CustomMenuAdapter(getActivity(),resource,items);
        setListAdapter(adapter);
        return view;
    }

    private void accesswebservice() {
        JsonMenuTask task = new JsonMenuTask();
        Log.e(getClass().getSimpleName(), "JsonMenuTask executed.");
        task.execute(new String[]{Url});
    }

    private class JsonMenuTask extends AsyncTask<String,Void,String>{
        String json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
//            try{
//                MenuResponse response = (MenuResponse) Common.getJson(getActivity(), "menu3.php", null,MenuResponse.class);
//
//            }catch(Exception e){
//
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           JsonMenu = s;

            try{

                JSONObject jsonmain = new JSONObject(JsonMenu);
                Log.i(getClass().getSimpleName(),"values"+JsonMenu);
                JSONArray jsonArray = jsonmain.getJSONArray("menu1");

//            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonChildnode = jsonArray.getJSONObject(index);
//                JSONArray jsonArray1 = jsonChildnode.getJSONArray("category");
//                for( i=0;i<jsonArray1.length();i++) {
//                    JSONObject jsoncategory = jsonArray1.getJSONObject(i);
                JSONArray jsonArray2 = jsonChildnode.getJSONArray("menu_items");
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    itemname = jsonObject.getString("item_name");
                    int value = jsonObject.getInt("price");
                    Log.i(getClass().getSimpleName(), "Get List ");
                    items.add(new Data(itemname, value));
                }} catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
