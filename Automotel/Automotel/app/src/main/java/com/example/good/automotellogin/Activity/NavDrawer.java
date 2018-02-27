package com.example.good.automotellogin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.good.automotellogin.Adapter.NavDrawerAdapter;
import com.example.good.automotellogin.Adapter.ResListAdapter2;
import com.example.good.automotellogin.Bean.ItemObject;
import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Bean.Res_list;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NavDrawer extends AppCompatActivity {
        long res_id;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[] titles = {"Automotel", "FinalOrder", "Favorites", "Reviews"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    private Button View;

    private String JsonResult;
    private String Url = "http://192.168.137.1:80/Android_Detail/Restaurantlist.php";
    private ListView Reslistview;
    List<Res_list> Reslist;
    private TextView stickyView;
    private View heroImageView;
    private View stickyViewSpacer;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    AlertDialog alertDialog;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int WRITE_EXTERNAL = 1;
    private static final int READ_EXTERNAL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.navdrawer);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.isAttachedToWindow();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Write here anything that you wish to do on click of FAB

                // Code to Add an item with default animation
                    Intent i = new Intent(NavDrawer.this,QrActivity.class);
                startActivity(i);
                //Ends Here
            }
        });
        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());

        // get Internet status
        isInternetPresent = cd.isConnectedToInternet();

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests

//            showAlertDialog(NavDrawer.this, "Internet Connection",
//                    "You have internet connection", true);
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(NavDrawer.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
//      //  ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout),E);
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Restaurants");
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        heroImageView = findViewById(R.id.heroImageView);
        stickyView = (TextView) findViewById(R.id.stickyView);

          /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.parallax_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);



        Reslistview = (ListView) findViewById(R.id.res_list);
        Reslistview.addHeaderView(listHeader);
        Reslistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(Reslistview.getFirstVisiblePosition()==0){
                    View firstChild = Reslistview.getChildAt(0);
                    int topY = 0;
                    if(firstChild != null){
                       // Log.e(getClass().getSimpleName(),firstChild.getClass().getSimpleName());
                        topY = firstChild.getTop();
                    }
                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0,topY+heroTopY));

                         /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY*1f );
                }
            }
        });
        accessWebservice();

//        setSupportActionBar(topToolBar);
        // topToolBar.setLogo(R.drawable.logo);
        // topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        LayoutInflater inflater1 = getLayoutInflater();
        View listHeaderView = inflater1.inflate(R.layout.nav_header_list, null, false);

        mDrawerList.addHeaderView(listHeaderView);

        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("Order", R.drawable.order));
        listViewItems.add(new ItemObject("Favorite", R.drawable.favorites));
        listViewItems.add(new ItemObject("Review", R.drawable.reviews));


        mDrawerList.setAdapter(new NavDrawerAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(NavDrawer.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                Log.i(getClass().getSimpleName(), "Item Clicked");
                switch (position) {
                    case 0:
                        Intent i = new Intent(NavDrawer.this, FacebookLogout.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent i1 = new Intent(NavDrawer.this, FinalOrder.class);
                        startActivity(i1);
                        break;
                    case 2:
                        Intent i2 = new Intent(NavDrawer.this, Favorites.class);
                        startActivity(i2);
                        break;
                    case 3:
                        Intent i3 = new Intent(NavDrawer.this, Reviews.class);
                        startActivity(i3);
                        break;

                }

            }
        });

        Reslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item " + (position + 1)+ "res_id = "+ Reslist.get(position).res_id, Toast.LENGTH_LONG).show();

                Intent i = new Intent(NavDrawer.this, ScrollTabsMenu.class);
                i.putExtra("res_id",id);
                Log.i(getClass().getSimpleName(), "res_id " + id);
                startActivity(i);

            }
        });

    }


    private void showAlertDialog(Context context, String s, String s1, boolean b) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(s1);
        alertDialog.show();

    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds menu_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action menu_items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_settings:
                return true;

        }


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void accessWebservice() {
        JsonReadTask task = new JsonReadTask();
        Log.e(getClass().getSimpleName(), "JsonReadTask executed.");
        task.execute(new String[]{Url});
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
                Log.e(getClass().getSimpleName(), "Connection Established..");
                System.setProperty("http.keepAlive", "false");
                // urlConnection.setConnectTimeout(30000);
                //urlConnection.setReadTimeout(30000);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");
                Log.e(getClass().getSimpleName(), "Post method..");
                // urlConnection.setDoInput(true);
//                urlConnection.setRequestMethod("GET");
                connection.setDoOutput(true);


                connection.getResponseCode();

                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                // Read the stream
//                byte[] b = new byte[1024];
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Log.e(getClass().getSimpleName(), "retrieving list..");

                while ((json = bufferedReader.readLine()) != null) {
                    Log.e(getClass().getSimpleName(), " DAta..");
                    sb.append(json + "\n");
                }

                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e(getClass().getSimpleName(), "getting null values..");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonResult = result;
            Log.e(getClass().getSimpleName(), "List Drawer class " + result);
            ListDrawer();

        }
    }


    public void ListDrawer() {
        Log.e(getClass().getSimpleName(), "Inside ListDrawer class");
        Reslist = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(JsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("ResList");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildnode = jsonMainNode.getJSONObject(i);
               res_id = jsonChildnode.getInt("res_id");
                String Resname = jsonChildnode.getString("restaurant");
                String location = jsonChildnode.getString("location");
                Log.e(getClass().getSimpleName(), "Get List ");
                Reslist.add(new Res_list(res_id,Resname, location, R.drawable.dislike, true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<Res_list> adapter = new ResListAdapter2(this, R.layout.restaurant_list, Reslist);
        //  adapter.notifyDataSetChanged();
        Reslistview.setAdapter(adapter);


    }


}