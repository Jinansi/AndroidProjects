package com.example.good.automotellogin.Bean;


import java.util.List;

/**
 * Created by GOOD on 18/03/2016.
 */
public class MenuResponse {
    public String res_id;

    public List<Menudata> menu1;

    public class Menudata {
        public String tag_name;
        public List<Item> items;
        public class Item{
            public String price;
            public String item_name;
        }

    }
}
