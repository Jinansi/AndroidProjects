package com.example.good.automotellogin.Activity;

import android.content.Context;

import com.example.good.automotellogin.Bean.fbuser;

/**
 * Created by GOOD on 04/04/2016.
 */
public class PrefUtils {
    public static void setCurrentUser(fbuser currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }
    public static fbuser getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        fbuser currentUser = complexPreferences.getObject("current_user_value", fbuser.class);
        return currentUser;
    }
    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

}
