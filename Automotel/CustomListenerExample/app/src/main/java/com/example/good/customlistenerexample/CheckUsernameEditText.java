package com.example.good.customlistenerexample;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by GOOD on 25/02/2016.
 */

 interface OnUserNameAvailableListener {
        public abstract void onAvailableChecked(String Username,Boolean available);
}
public class CheckUsernameEditText extends EditText implements View.OnKeyListener {
    final private static String[] registeredUsers = new String[] {
            // This is just a fixed List for tutorial purposes
            // in a real application you'd check this server sided or inside the database
            "tseng",
            "admin",
            "root",
            "joedoe",
            "john"
    };
    OnUserNameAvailableListener userNameAvailableListener = null;

    public CheckUsernameEditText(Context context) {
        super(context);
    }

    public void setUserNameAvailableListener(OnUserNameAvailableListener listener) {
        userNameAvailableListener = listener;
    }

    private void OnUserchecked(String user, boolean available) {
        if (userNameAvailableListener != null) {
            if (!TextUtils.isEmpty(user)) {
                userNameAvailableListener.onAvailableChecked(user, available);
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if (KeyEvent.getAction() == KeyEvent.ACTION_DOWN)
//            return false;
//        boolean available = true;
//        String username = getText().toString();
//        if (!TextUtils.isEmpty(username)) {
//            for (int i = 0; i < registeredUsers.length; i++) {
//                if (registeredUsers[i].equals(username)) {
//                    available = false;
//                    // Finish the loop, as the name is already taken
//                    break;
//                }
//            }
//                OnUserchecked(username, available);
//                return false;
//
//            }
//
//        return available;
        return  false;
    }
}