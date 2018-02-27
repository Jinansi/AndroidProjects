package com.example.good.automotellogin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.good.automotellogin.R;
import com.example.good.automotellogin.Bean.Regresponse;

import java.util.HashMap;

/**
 * Created by GOOD on 25/02/2016.
 */
public class RegisterActivity  extends AppCompatActivity {
    private EditText edtUsername;
    Activity context;
    private EditText edtPasword;
    private Button btnRegister;
    private Button btnLogin;
    private EditText inputname;

    private String uUsername;
    private String uPassword;
    private String name;
    Regresponse response;
    String message,username;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        context = this;
        findViews();

    }


    private void findViews() {
        inputname = (EditText) findViewById(R.id.Rname);
        edtUsername = (EditText) findViewById(R.id.Remail);
        edtPasword = (EditText) findViewById(R.id.Rpassword);
        btnRegister = (Button) findViewById(R.id.Register);
        btnLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
//        btnRegister.setOnClickListener(this);
//        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!inputname.getText().toString().equals("")) && (!edtUsername.getText().toString().equals("")) && (!edtPasword.getText().toString().equals(" "))) {
                    name = inputname.getText().toString();
                    uUsername = edtUsername.getText().toString();
                    uPassword = edtPasword.getText().toString();
                    AsyRegistration asyRegistration = new AsyRegistration();
                    asyRegistration.execute();

//                    SaveData saveData = new SaveData();
//                    saveData.execute(POST);
                } else if ((!inputname.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password field empty", Toast.LENGTH_SHORT).show();
                } else if ((!edtPasword.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Email field empty", Toast.LENGTH_SHORT).show();
                } else if ((!edtUsername.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Empty E-mail ID field", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity2.class));

            }
        });

    }


    class AsyRegistration extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
            //        ProgressDialog.getInstance(context).showProgress("Signing in");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, String> kvp = new HashMap<String, String>();
                kvp.put("name", name);
                kvp.put("username", uUsername);
                kvp.put("password", uPassword);
                response = (Regresponse) Common.getJson(context, "registerjj1.php",kvp , Regresponse.class);

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            Log.e(getClass().getSimpleName(), "Registering");
            message = response.data.message;
            Log.e(getClass().getSimpleName(), "Fetching data..");
            username = response.data.username;
            Log.e(getClass().getSimpleName(), "Fetching data..");
            status = response.data.status;
            name = response.data.name;
                Log.e(getClass().getSimpleName(), "After connection establishment.." + status);

            if(status == 200){
//                Common.SavePref(context, "Username ", username);
//                Common.SavePref(context,"name" , name);
//                Common.SavePref(context,"password",uPassword);
                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"You r already registered",Toast.LENGTH_LONG).show();
            }

        }



    }
}
