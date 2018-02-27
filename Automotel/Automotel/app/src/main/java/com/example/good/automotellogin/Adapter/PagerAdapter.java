package com.example.good.automotellogin.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.good.automotellogin.Bean.MenuResponse;
import com.example.good.automotellogin.Fragments.FragmentA;
import com.example.good.automotellogin.Fragments.MenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by GOOD on 27/01/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    int mnumOftabs;
    Context context;
    ArrayList<FragmentA> fragmentsList;
    ArrayList<MenuFragment> menuFragments;
    MenuResponse response;

    public PagerAdapter(Context context, FragmentManager fm, int numOftabs) {
        super(fm);
        this.mnumOftabs = numOftabs;
        this.context = context;
        fragmentsList = new ArrayList<>();

        //    menuFragments = new ArrayList<>();
//        Asynctask asynctask = new Asynctask();
//        asynctask.execute();
//
//        StringBuffer sb = new StringBuffer();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new InputStreamReader(context.getAssets().open("get_menu_response.json")));
//            String temp;
//            while ((temp = br.readLine()) != null) {
//                sb.append(temp);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String myjson = sb.toString();
//
//        try {
//            JSONObject jsonmain = new JSONObject(myjson);
//            Log.i(getClass().getSimpleName(), "values" + myjson);
//            JSONArray jsonArray = jsonmain.getJSONArray("menu");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                FragmentA fragmentA = new FragmentA();
//                fragmentA.setIndex(i);
//                fragmentsList.add(i, fragmentA);
//            }
////            for(int i=0;i<jsonArray.length();i++){
////                MenuFragment menufragment = new MenuFragment();
////
////                menufragment.setIndex(i);
////                menuFragments.add(i,menufragment);
////            }
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
        @Override
        public Fragment getItem(int i) {
            return

                    fragmentsList.get(i);
        }

        @Override
        public int getCount() {
            return mnumOftabs;
        }

    public ArrayList<FragmentA> getFragmentsList(){
        return fragmentsList;
    }




    }




