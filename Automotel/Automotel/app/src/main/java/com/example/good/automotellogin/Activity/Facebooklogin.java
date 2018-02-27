package com.example.good.automotellogin.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.good.automotellogin.Bean.fbuser;
import com.example.good.automotellogin.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GOOD on 04/04/2016.
 */
public class Facebooklogin extends AppCompatActivity {
    private TextView info;
    private Button Signup;
    private LoginButton loginButton;
  //  private Button SignUp;
    private Button skip;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.facebook_login);
//        SignUp = (Button) findViewById(R.id.signup);
//        SignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Facebooklogin.this,RegisterActivity.class);
//                startActivity(i);
//            }
//        });
        info = (TextView)findViewById(R.id.info);
        Signup= (Button) findViewById(R.id.signup);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        skip = (Button) findViewById(R.id.skip);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Facebooklogin.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Facebooklogin.this,NavDrawer.class);
                startActivity(intent);
            }
        });
        if(PrefUtils.getCurrentUser(Facebooklogin.this) != null){

            Intent homeIntent = new Intent(Facebooklogin.this, NavDrawer.class);

            startActivity(homeIntent);

            finish();
        }
        loginButton.setReadPermissions("public_profile", "email","user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                info.setText(
//                        "User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken()
//                );
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        Log.i(getClass().getSimpleName(),"graphresponse :" + graphResponse);


                            try {
                                fbuser user = new fbuser();
                                user.email= jsonObject.getString("email").toString();
                                user.facebookID = jsonObject.getString("id").toString();
                                user.name= jsonObject.getString("name").toString();
                                user.gender = jsonObject.getString("gender");
                                PrefUtils.setCurrentUser(user,Facebooklogin.this);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                    }
                });
                Toast.makeText(Facebooklogin.this, "Login Successfull!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Facebooklogin.this,NavDrawer.class);
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,gender");
                startActivity(i);
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
