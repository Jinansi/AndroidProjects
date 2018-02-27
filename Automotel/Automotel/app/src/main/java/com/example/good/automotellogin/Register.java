package com.example.good.automotellogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Register extends Activity {


    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STATUS = "status";
    private static final String TAG_DATA = "data";
    private JSONArray data;

    private InputStream is = null;
    private String line = null;
    private String result = null;


    private EditText edtUsername;
    private EditText edtPasword;
    private Button btnRegister;
    private Button btnLogin;
    private EditText inputname;

    private String uUsername;
    private String uPassword;
    private String name;
    private int regState;
    private String rspMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        findViews();

    }

    private void findViews() {
        inputname= (EditText) findViewById(R.id.Rname);
        edtUsername = (EditText)findViewById( R.id.Remail );
        edtPasword = (EditText)findViewById( R.id.Rpassword );
        btnRegister = (Button)findViewById( R.id.Register );
        btnLogin = (Button)findViewById( R.id.btnLinkToLoginScreen );

        final JSONObject  POST = new JSONObject();
        try {
            POST.put("name",name);
            POST.put("username",uUsername);
            POST.put("password",uPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!inputname.getText().toString().equals("")) && (!edtUsername.getText().toString().equals("")) && (!edtPasword.getText().toString().equals(" "))) {
                    name = inputname.getText().toString();
                    uUsername = edtUsername.getText().toString();
                    uPassword = edtPasword.getText().toString();

                    SaveData saveData = new SaveData();
                    saveData.execute(POST);
                }
                else if ((!inputname.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!edtPasword.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                }
                else if ((!edtUsername.getText().toString().equals(""))){
                    Toast.makeText(getApplicationContext(),"Empty E-mail ID field",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Loginactivity.class));
            }
        });
    }




    private class SaveData extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected String doInBackground(JSONObject... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://192.168.65.50/Android_Detail/register.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                System.setProperty("http.keepAlive", "false");
                // urlConnection.setConnectTimeout(30000);
                //urlConnection.setReadTimeout(30000);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestMethod("POST");
                // urlConnection.setDoInput(true);
//                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);

//                String postParameters = "name=" + u_Name + "username=" + u_Username + "password=" +u_Password;
                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("name",name);
                builder.appendQueryParameter("username", uUsername);
                builder.appendQueryParameter("password", uPassword);

                String postParameters = builder.build().getEncodedQuery();
                urlConnection.setFixedLengthStreamingMode(
                        postParameters.getBytes().length);
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(postParameters);
                out.close();

                //Reading the Response....

                int statusCode = urlConnection.getResponseCode();

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
        protected void onPostExecute(String result) {

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
                finally {
                    Intent i = new Intent(Register.this,RestaurantList.class);
                    startActivity(i);
                }
            }else {
                Log.d("register", "Result has no data");
            }

            if(regState == 200){
                Toast.makeText(getApplicationContext(),"you are successfully registered",Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Register.this,RestaurantList.class);
//                startActivity(i);
            }
            else {
                Toast.makeText(getApplicationContext(),"you r already registerd",Toast.LENGTH_LONG).show();
//                Intent i = new Intent(Register.this,RestaurantList.class);
//                startActivity(i);
            }


        }
    }

}
