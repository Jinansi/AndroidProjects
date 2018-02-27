package com.example.good.customlistenerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnCancel;
    private TextView lblUserStatus;
        ListenerActivity listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        etUsername = (EditText) findViewById(R.id.username);
//        etPassword = (EditText)findViewById(R.id.password);
        btnRegister = (Button)findViewById(R.id.register_button);
        btnCancel = (Button)findViewById(R.id.cancel_button);
 //       lblUserStatus = (TextView)findViewById(R.id.userstatus);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setD();
            }
        });
        listener = new ListenerActivity(new DemoListener() {
            @Override
            public void call() {
                Toast.makeText(getApplicationContext(),"CustomListener",Toast.LENGTH_LONG).show();
            }
        });

//            public void onClick(View v) {
////                public void setUserNameAvailableListener(OnUserNameAvailableListener listener) {
////                    userNameAvailableListener = listener;
////                }
////                private void OnUserchecked(String user, boolean available) {
////                    if (userNameAvailableListener != null) {
////                        if (!TextUtils.isEmpty(user)) {
////                            userNameAvailableListener.onAvailableChecked(user, available);
////                        }
////                    }
////                }
//
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }


    }
    }

