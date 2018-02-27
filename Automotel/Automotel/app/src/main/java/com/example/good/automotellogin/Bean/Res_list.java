package com.example.good.automotellogin.Bean;

/**
 * Created by GOOD on 10/02/2016.
 */
 public class Res_list {
    public long res_id;
    public int unlikedImage;
    public int likedImage;
    private boolean flag;


    public String title;
    public String location;
    public Res_list(long res_id,String title, String location, int unlikedImage, boolean flag){
        this.res_id=res_id;
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
    public Res_list(String displayfavdata) {

    }
}
