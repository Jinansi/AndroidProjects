package com.example.good.automotellogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.good.automotellogin.Activity.RestaurantList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by GOOD on 16/02/2016.
 */
public class Loginactivity  extends AppCompatActivity{

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STATUS = "status";
    private static final String TAG_DATA = "data";
    private JSONArray data;
    ProgressDialog pDialog;
    private int regState;
    private String rspMessage;
    private InputStream is = null;
    private String line = null;
    private String result = null;

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private Button Btnregister;
    private String Username;
    private String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Btnregister = (Button) findViewById(R.id.btnRegister);

        final JSONObject  POST = new JSONObject();
        try {

            POST.put("username",Username);
            POST.put("password",Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Btnregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Register.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if ((!inputEmail.getText().toString().equals("")) && (!inputPassword.getText().toString().equals(""))) {
                           Username=inputEmail.getText().toString();
                            Password=inputPassword.getText().toString();
                    LoginData login=new LoginData();
                        login.execute(POST);
                } else if ((!inputEmail.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!inputPassword.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class LoginData extends AsyncTask<JSONObject,Void,String>{

        @Override
        protected String doInBackground(JSONObject... params) {
            HttpURLConnection urlConnection = null;
            Log.e(getClass().getSimpleName(),"Login data class..");
            try {
                URL url = new URL("http://192.168.89.50/Android_Detail/login.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                System.setProperty("http.keepAlive", "false");
                // urlConnection.setConnectTimeout(30000);
                //urlConnection.setReadTimeout(30000);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestMethod("POST");
                // urlConnection.setDoInput(true);
//                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                Log.e(getClass().getSimpleName(),"Connection established..");

//                String postParameters = "name=" + u_Name + "username=" + u_Username + "password=" +u_Password;
                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("username", Username);
                builder.appendQueryParameter("password", Password);
                    Log.e(getClass().getSimpleName(),"After connection..");
                String postParameters = builder.build().getEncodedQuery();
                urlConnection.setFixedLengthStreamingMode(
                        postParameters.getBytes().length);
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                Log.e(getClass().getSimpleName(),"Fetching data...");
                out.print(postParameters);
//                out.close();

                //Reading the Response....

                int statusCode = urlConnection.getResponseCode();
                Log.e(getClass().getSimpleName(),"Status...");
                if (statusCode >= 200 && statusCode < 400) {
                    // Create an InputStream in order to extract the response object
                    is = new BufferedInputStream(urlConnection.getInputStream());
                }
                else {
                    is = urlConnection.getErrorStream();
                }

                Log.d("WEBSERVICE", " the data whiile sending it " + is + " :: ");

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                Log.d("WEBSERVICE", " result is after sending the data  ... " + result + " :: ");

            } catch (Exception e) {
                Log.d("WEBSERVICE", " trying to send data  " + e.getMessage() + " ::");
                result = e.getMessage();
                    e.printStackTrace();
            } finally {

                urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(result !=null) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    data = jsonObject.getJSONArray(TAG_DATA);

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject d = data.getJSONObject(i);
                        regState = d.getInt(TAG_STATUS);
                        rspMessage = d.getString(TAG_MESSAGE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Log.d("register", "Result has no data");
            }

            if(regState == 200){
                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Loginactivity.this,RestaurantList.class);
                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(),"error in login",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
