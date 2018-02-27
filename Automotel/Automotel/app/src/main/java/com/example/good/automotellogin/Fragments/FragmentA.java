package com.example.good.automotellogin.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.good.automotellogin.Adapter.CustomMenuAdapter;
import com.example.good.automotellogin.Adapter.CustomMenuAdapter3;
import com.example.good.automotellogin.Bean.Data;
import com.example.good.automotellogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends ListFragment {
//    String[] list={"one","two","three"};
//    int price[] = {20,50,100};
String itemname;
int resource;
    CustomMenuAdapter3 adapter;
    int index;
    private String JsonResult;
    List<Data> items = new ArrayList<Data>();

 //   View header;
    public FragmentA() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent i = new Intent(getActivity(),Menu_list1.class);
//        startActivity(i);

    }

    private void AccessWebService(String email) {
        JsonReadTask task = new JsonReadTask(email);
        Log.e(getClass().getSimpleName(), "JsonReadTask executed.");
        task.execute();
    }
    private class JsonReadTask extends AsyncTask<String,Void,String>{
        String json;
        String email;
        public JsonReadTask(String email){
            this.email = email;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1*1024*1024;
            String urlString = "http://192.168.1.4:8081/get_hotel_menu";
            try{
                //------------------ CLIENT REQUEST
                // open a URL connection to the Servlet
                URL url = new URL(urlString);
                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Don't use a cached copy.
                conn.setUseCaches(false);
                // Use a post method.
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                dos = new DataOutputStream( conn.getOutputStream() );
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"email\";" + lineEnd); // uploaded_file_name is the Name of the File to be uploaded
                dos.writeBytes(lineEnd);
                dos.writeBytes(email);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                dos.flush();
                dos.close();
            }
            catch (MalformedURLException ex){
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            }
            catch (IOException ioe){
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            }
            //------------------ read the SERVER RESPONSE
            try {
                inStream = new DataInputStream( conn.getInputStream() );
                String str;
                while (( str = inStream.readLine()) != null){
                    Log.i("Debug","Server Response "+str);
                    json=str;
                }
                inStream.close();
            }
            catch (IOException ioex){
                Log.i("Debug", "error: " + ioex.getMessage(), ioex);
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JsonResult = s;
            Log.i(getClass().getSimpleName(), "JsonResult " + JsonResult);

            try{
                JSONObject jsonmain = new JSONObject(JsonResult);
                Log.i(getClass().getSimpleName(),"values"+ JsonResult);
                JSONArray jsonArray = jsonmain.getJSONArray("menu");

//            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonChildnode = jsonArray.getJSONObject(index);
//                JSONArray jsonArray1 = jsonChildnode.getJSONArray("category");
//                for( i=0;i<jsonArray1.length();i++) {
//                    JSONObject jsoncategory = jsonArray1.getJSONObject(i);
                //   JSONArray jsonArray2 = jsonChildnode.getJSONArray("menu_items");
                JSONArray jsonArray2 = jsonChildnode.getJSONArray("category");
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    itemname = jsonObject.getString("name");
                    int value = jsonObject.getInt("value");
                    Log.e(getClass().getSimpleName(), "Get List ");
                    items.add(new Data(itemname, value));
                }
//                }
//            }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void setIndex(int index){
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a,container,false);
        AccessWebService("ccd@gmail.com");

//        List<Data> items = new ArrayList<Data>();
//        StringBuffer sb = new StringBuffer();
//        BufferedReader br = null;
//        try
//        {
//            br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("get_menu_response.json")));
//            String temp;
//            while((temp = br.readLine()) != null){
//                sb.append(temp);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        String myjson = sb.toString();
//
//        try{
//            JSONObject jsonmain = new JSONObject(myjson);
//            Log.i(getClass().getSimpleName(),"values"+myjson);
//            JSONArray jsonArray = jsonmain.getJSONArray("menu");
//
////            for(int i=0;i<jsonArray.length();i++){
//                JSONObject jsonChildnode = jsonArray.getJSONObject(index);
////                JSONArray jsonArray1 = jsonChildnode.getJSONArray("category");
////                for( i=0;i<jsonArray1.length();i++) {
////                    JSONObject jsoncategory = jsonArray1.getJSONObject(i);
//         //   JSONArray jsonArray2 = jsonChildnode.getJSONArray("menu_items");
//                    JSONArray jsonArray2 = jsonChildnode.getJSONArray("category");
//                    for (int i = 0; i < jsonArray2.length(); i++) {
//                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
//                        itemname = jsonObject.getString("name");
//                        int value = jsonObject.getInt("value");
//                        Log.e(getClass().getSimpleName(), "Get List ");
//                        items.add(new Data(itemname, value));
//                    }
////                }
////            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        adapter = new CustomMenuAdapter3(getActivity(),resource,items);
        setListAdapter(adapter);
       return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }

    public CustomMenuAdapter3 getAdapter(){
        return adapter;
    }
}
