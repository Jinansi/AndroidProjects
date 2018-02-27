package com.example.good.automotellogin.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.good.automotellogin.R;

/**
 * Created by GOOD on 09/03/2016.
 */
public class Dataholder {
    TextView res_title,res_location;
    ImageView unliked;

    Dataholder(View v){
        res_title = (TextView) v.findViewById(R.id.res_name);
        res_location = (TextView) v.findViewById(R.id.res_location);
        unliked = (ImageView) v.findViewById(R.id.unliked);

    }
}
