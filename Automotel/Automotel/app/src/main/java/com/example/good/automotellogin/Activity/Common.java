package com.example.good.automotellogin.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GOOD on 25/02/2016.
 */
public class Common  {
    private static String baseUrl = "http://192.168.137.1:80/Android_Detail/";
    static SharedPreferences sharedPreferences;

    public static Object getJson(Activity context, String methodName, HashMap<String, String> params, Class<?> classOfT) {

        HttpURLConnection conn = null;

        Object obj = null;

        try {

            URL url = new URL(baseUrl + methodName);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.appendQueryParameter(key, value);
            }

            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Log.e("Response " + classOfT.getSimpleName(), response.toString());

//               CommonResp commonResp = (CommonResp) new GsonBuilder().create().fromJson(
//                        response.substring(response.indexOf("{")), CommonResp.class);
//
//                if (commonResp.status == 2) {
//                    Intent iSignout = new Intent(context, LoginActivity.class);
//                    Common.SavePref(context, "RememberMe", null);
//                    iSignout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(iSignout);
//                    context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                    context.finish();
//                    return null;
//                }
//
                obj = new GsonBuilder().create().fromJson(response.substring(response.indexOf("{")), classOfT);

            } else {
                Log.e("Service", "POST request not worked");
            }

            conn.disconnect();

        } catch (Exception e) {
            if (conn != null)
                conn.disconnect();
            e.printStackTrace();
        }
        return obj;
    }
    public static Object getJson(Activity context,String methodName,Class<?> classOfT){

        return null;
    }

//    public static Object getJson(Activity context, String methodName, HashMap<String, String> params, File file,
//                                 Class<?> classOfT) {
//
//        Object obj = null;
//
//        try {
//            String charset = "UTF-8";
//            String requestURL = baseUrl + methodName;
//
//            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
//
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                multipart.addFormField(entry.getKey(), entry.getValue());
//            }
//
//            multipart.addFilePart("profile_pic", file);
//
//            List<String> responseList = multipart.finish();
//            StringBuffer response = new StringBuffer();
//
//            for (String string : responseList) {
//
//                response.append(string);
//            }
//
//            Log.e("Responce " + classOfT.getSimpleName(), response.toString());
//
//            CommonResp commonResp = (CommonResp) new GsonBuilder().create().fromJson(
//                    response.substring(response.indexOf("{") < 0 ? 0 : response.indexOf("{")), CommonResp.class);
//
//            if (commonResp.status == 2) {
//                Intent iSignout = new Intent(context, LoginActivity.class);
//                Common.SavePref(context, "RememberMe", null);
//                iSignout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(iSignout);
//                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                context.finish();
//                return null;
//            }
//
//            obj = new GsonBuilder().create().fromJson(response.substring(response.indexOf("{")), classOfT);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return obj;
//    }

 //   public static void SavePref(Activity cntx, String key, String value) {



//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cntx);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(key, value);
//        editor.commit();

        // } else {
        //
        // // No explanation needed, we can request the permission.
        //
        // ActivityCompat.requestPermissions(cntx, new String[] {
        // Manifest.permission.WRITE_EXTERNAL_STORAGE },
        // 111);
        //
        // // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // // app-defined int constant. The callback method gets the
        // // result of the request.
        // }
        // }

 //   }

//    public static String LoadPref(Context cntx, String key) {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(cntx);
//        return sharedPreferences.getString(key, null);
//    }


}
