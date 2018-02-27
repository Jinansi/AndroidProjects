package com.example.good.automotellogin.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.good.automotellogin.Bean.Loginres;
import com.example.good.automotellogin.R;

import java.io.InputStream;
import java.util.HashMap;


public class LoginActivity2 extends AppCompatActivity {

    ProgressDialog pDialog;
    private InputStream is = null;

 Activity context;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private Button Btnregister;
    private String Username;
    private String Password;
    Loginres loginresp;
    LoginData login;

        private SharedPreferences loginPrefs;
    private SharedPreferences.Editor loginEditor;
    private final static String USERNAME_KEY = "username";
    private final static  String PASSWORD_KEY = "password";
    private final static String SAVED_KEY = "saved";
    String message,username;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Btnregister = (Button) findViewById(R.id.btnRegister);


        Btnregister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(LoginActivity2.this, RegisterActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnLogin.setOnClickListener(new OnClickListener() {


            public void onClick(View view) {

                if ((!inputEmail.getText().toString().equals("")) && (!inputPassword.getText().toString().equals(""))) {
                    Username=inputEmail.getText().toString();
                    Password=inputPassword.getText().toString();

                     login=new LoginData();
                    login.execute();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds menu_items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


     class LoginData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, String> kvp = new HashMap<String, String>();

                kvp.put("username", Username);
                kvp.put("password", Password);

                loginresp = (Loginres) Common.getJson(context, "login.php", kvp, Loginres.class);
                Log.e(getClass().getSimpleName(),"loginresp" + loginresp);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            Log.e(getClass().getSimpleName(), "Registering");
            message = loginresp.data.message;
            Log.e(getClass().getSimpleName(), "Fetching data..");
            username = loginresp.data.username;
            Log.e(getClass().getSimpleName(), "Fetching data..");
           status = loginresp.data.status;
            Log.e(getClass().getSimpleName(), "After connection establishment.." + status);
//            Common.SavePref(context, "Username ", username);
//            Common.SavePref(context, "password", Password);
            if(status == 200){

                Toast.makeText(getApplicationContext(),"Login  Successfull",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Error in login",Toast.LENGTH_LONG).show();
            }
        }
    }
}
