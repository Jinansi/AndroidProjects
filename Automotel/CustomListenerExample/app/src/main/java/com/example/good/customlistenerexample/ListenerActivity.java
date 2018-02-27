package com.example.good.customlistenerexample;

import android.widget.TextView;

/**
 * Created by GOOD on 25/02/2016.
 */
public class ListenerActivity  {
    DemoListener d;


    public ListenerActivity(DemoListener d) {
        this.d=d;
    }

    public void setD() {
        if(d!=null){
            d.call();
        }
    }
}

interface DemoListener{
    public void call();
}