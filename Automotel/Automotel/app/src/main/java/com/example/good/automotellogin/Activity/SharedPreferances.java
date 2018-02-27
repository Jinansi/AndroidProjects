package com.example.good.automotellogin.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.good.automotellogin.Bean.Res_list;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by GOOD on 08/03/2016.
 */
public class SharedPreferances  {
    public static final String PREFS_NAME = "Favorite_PREFS";
    public static final String Favorites = "Res_Favorite";

    public SharedPreferances() {
        super();
    }
    public void saveFavorites(Context context, List<Res_list> favorites) {
        SharedPreferences settings=null;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        Gson gson = new Gson();
        String jsonfavorites = gson.toJson(favorites);
        editor.putString(Favorites, jsonfavorites); //3

        editor.commit(); //4
    }

    public ArrayList<Res_list> getFavorites(Context context) {

        SharedPreferences settings;
       List<Res_list> favorites;


        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(settings.contains(Favorites)){
            String jsonfavorites = settings.getString(Favorites,null);
            Gson gson = new Gson();
            Res_list[] favoriteItems = gson.fromJson(jsonfavorites,
                    Res_list[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Res_list>(favorites);

        }else{
            Log.i(getClass().getSimpleName(), "Data not saved..");
            return null;

        }
        return (ArrayList<Res_list>)favorites;
    }
    public void addFavorite(Context context,Res_list list){
        List<Res_list> addlist = getFavorites(context);
        if(addlist == null){
            addlist=new ArrayList<Res_list>();
            addlist.add(list);
        }

    }
    public void removefavorite(Context context,Res_list list){
        ArrayList<Res_list> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(favorites);
            saveFavorites(context, favorites);
        }
    }
}
