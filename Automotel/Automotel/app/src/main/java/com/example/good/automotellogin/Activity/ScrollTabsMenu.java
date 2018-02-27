package com.example.good.automotellogin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.good.automotellogin.Adapter.CustomMenuAdapter3;
import com.example.good.automotellogin.Adapter.PagerAdapter;
import com.example.good.automotellogin.Bean.Data;
import com.example.good.automotellogin.Bean.MenuResponse;
import com.example.good.automotellogin.Fragments.FragmentA;
import com.example.good.automotellogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScrollTabsMenu extends FragmentActivity {
  //  private Toolbar mtoolbar;
   TabLayout tablayout;
    Activity context;
    public ViewPager viewPager;
    PagerAdapter adapter;
    ActionBar actionbar;
    long res_id;
    DatabaseHelper orderDatabaseAdapter;
    CircleImageView circleimage;
    private String JsonResult;
    Data data;

 //   private String Url = "http://192.168.1.5/Android_Detail/menu3.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolltabs_menu);
        Log.e(getClass().getSimpleName(), "Menu Class...");
        context=this;
   //     mtoolbar = (Toolbar) findViewById(R.id.toolbar);
   //     setSupportActionBar(mtoolbar);
        circleimage = (CircleImageView) findViewById(R.id.saveorder);
        circleimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<FragmentA> fragmentsList = adapter.getFragmentsList();
                    int length = fragmentsList.size();
                    for (int j = 0; j < length; j++) {
                        FragmentA fragmentA = fragmentsList.get(j);
                        fragmentA.setIndex(j);
                        CustomMenuAdapter3 adapter = fragmentA.getAdapter();
                        List dataList = adapter.getData();
                        int dataSize = dataList.size();
                        for (int k = 0; k < dataSize; k++) {
                             data = (Data) dataList.get(k);
                            Log.i("Data", data.title + " " + data.counter);
                                addOrder();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent i = new Intent(ScrollTabsMenu.this,SaveOrder.class);


                startActivity(i);
            }
        });
         tablayout = (TabLayout) findViewById(R.id.tab_layout);
//        res_id = getIntent().getExtras().getLong("res_id");
   //     Log.i(getClass().getSimpleName(), "res_id " + res_id);

        AccessWebService("ccd@gmail.com");
        Log.i(getClass().getSimpleName(),"After Accesswebservice method..");
    }

    private void addOrder() {

            orderDatabaseAdapter = new DatabaseHelper(context);
            //   String Itemname = String.valueOf(orderDatabaseAdapter.insertData(name,price,quantity));
            Log.i(getClass().getSimpleName(), "get values.." + data.counter + data.price + data.counter);

            Long id = orderDatabaseAdapter.insertOrder(data.counter,data.price,data.title);
            Log.i(getClass().getSimpleName(), "Inserting data.." + id);

            if (id < 0) {
                Log.i(getClass().getSimpleName(), "Unsuccessfull");
            } else {
                Log.i(getClass().getSimpleName(), "Successfully inserted");
            }

    }

    private void setupTabs(String jsonString){

        try{
            JSONArray mainArray = new JSONArray(jsonString);
            JSONObject jsonMainObject = mainArray.getJSONObject(0);
            JSONArray jsonMainArray = jsonMainObject.getJSONArray("menu");

            for (int i = 0; i < jsonMainArray.length(); i++) {
                JSONObject jsonObject = jsonMainArray.getJSONObject(i);
                //   String tab = jsonObject.getString("tag_name");
                String tab = jsonObject.getString("category");
                tablayout.addTab(tablayout.newTab().setText(tab));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PagerAdapter(ScrollTabsMenu.this,getSupportFragmentManager(), tablayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        //     tablayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("VIVZ", "on Page scrolled at : " + "position" + position + "from" + positionOffset + "with no. of pixels" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                    actionbar.setSelectedNavigationItem(position);
                //              tablayout.setOnTabSelectedListener(tablayout.setOnTabSelectedListener(););
                tablayout.getTabAt(position).select();
                Log.d("VIVZ", "on Page Selected at : " + "position" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Log.d("VIVZ", "Idle");
                }
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    Log.d("VIVZ", "drag");
                }
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.d("VIVZ", "settling");
                }
            }
        });
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }



            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void AccessWebService(String email) {
        JsonReadTask task = new JsonReadTask(email);
        Log.e(getClass().getSimpleName(), "JsonReadTask executed.");
        task.execute();
    }

    private class JsonReadTask extends AsyncTask<String,Void,String> {
        String json;
        String email;

        public JsonReadTask(String email){
            this.email = email;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
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
            setupTabs(s);
            JsonResult = s;
            Log.i(getClass().getSimpleName(),"JsonResult " + JsonResult);
//            try{
////                    String menu_name = response.res_id;
////                     name = response.menu1.toString();
//
//                //response.menu.
//
//                JSONArray jsonMainArray = new JSONArray(JsonResult);
//                JSONObject jsonMainObject = jsonMainArray.getJSONObject(0);
////                    for(int j=0;j<jsonMainArray.length();j++) {
////                        JSONObject jsonmenuobject = jsonMainArray.getJSONObject(j);
//                        JSONArray jsonmenuarray = jsonMainObject.getJSONArray("menu");
//
//
//                        for (int i = 0; i < jsonMainArray.length(); i++) {
//                            JSONObject jsonObject = jsonmenuarray.getJSONObject(i);
//                            String tab = jsonObject.getString("category");
//                            Log.i(getClass().getSimpleName(), "category" + tab);
//                            //    String tab = jsonObject.getString("category");
////                            tablayout.addTab(tablayout.newTab().setText(tab));
//                        }
////                    }
//
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds menu_items to the action bar if it is present.
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
            Intent i;
            i = new Intent(ScrollTabsMenu.this,FacebookLogout.class);
            startActivity(i);
        }

        if(id==  R.id.Logout){
            Intent i;
            i = new Intent(ScrollTabsMenu.this,FacebookLogout.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}