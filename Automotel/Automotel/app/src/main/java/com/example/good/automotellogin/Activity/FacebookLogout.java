package com.example.good.automotellogin.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.good.automotellogin.Bean.fbuser;
import com.example.good.automotellogin.R;
import com.facebook.login.LoginManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GOOD on 04/04/2016.
 */
public class FacebookLogout extends AppCompatActivity {
    private TextView btnLogout;
    private fbuser user;
    private ImageView profileImage;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb_logout);
        user = PrefUtils.getCurrentUser(FacebookLogout.this);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        btnLogout = (TextView) findViewById(R.id.btnLogout);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + "991363644274405" + "/picture?type=large");
                    bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                profileImage.setImageBitmap(bitmap);
            }
        }.execute();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(FacebookLogout.this);
                LoginManager.getInstance().logOut();
                Intent i = new Intent(FacebookLogout.this,Facebooklogin.class);
                startActivity(i);
                finish();
            }
        });
    }
}
