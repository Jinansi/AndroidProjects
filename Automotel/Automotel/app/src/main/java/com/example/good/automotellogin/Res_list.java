package com.example.good.automotellogin;

import java.util.List;

/**
 * Created by GOOD on 10/02/2016.
 */
public class  Res_list {

    private int unlikedImage;
    private int likedImage;
    private boolean flag;


    public String title;
    public String location;

    public Res_list(String title, String location, int unlikedImage, boolean flag){
        this.unlikedImage=unlikedImage;
        this.title=title;
        this.location=location;
        this.setFlag(flag);

    }




    public void setFlag(boolean flag) {
        this.flag=flag;
    }

    public boolean isFlag() {

        return flag;
    }
}
