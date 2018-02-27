package com.example.good.automotellogin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.good.automotellogin.R;

public class Splash extends Activity {
  //  private static int SPLASH_TIME_OUT = 3000;
    Thread splashThread;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

              StartAnimations();
//        splashThread = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    sleep(SPLASH_TIME_OUT);
//                    Intent i = new Intent(getBaseContext(),NavDrawer.class);
//                    startActivity(i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    finish();
//                }
//            }
//
//
//        };
//
//        splashThread.start();
    }





    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.linear);
        l.clearAnimation();
        l.startAnimation(anim);
        anim=AnimationUtils.loadAnimation(this,R.anim.translate);
        anim.reset();
        ImageView iv= (ImageView) findViewById(R.id.icon);
        iv.clearAnimation();
        iv.startAnimation(anim);
        splashThread = new Thread() {


            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splash.this,
                            Facebooklogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                    finally{
                        Splash.this.finish();
                    }
            }
        };
        splashThread.start();
    }
}


