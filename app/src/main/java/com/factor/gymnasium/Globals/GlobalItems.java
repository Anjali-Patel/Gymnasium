package com.factor.gymnasium.Globals;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class GlobalItems {
    public static String DB_TABLE = "PTM";
    @SuppressLint("StaticFieldLeak")
    public static Context global_ctx;

    public static String MEMBER_BASE_URL="http://pacecon.net/gymapp/api/member/";
    public static boolean isInternetAvailable(Context context)
       {
        NetworkInfo info = (NetworkInfo)((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null)
        {
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                return true;
            }
            else
            {
                return true;
            }

        }
    }


    public static void svPr(String key, String value) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String ldPr(String key) {
        SharedPreferences sharedPreferences = global_ctx.getSharedPreferences(
                DB_TABLE, Context.MODE_PRIVATE);
        String val;
        try {
            if (key.length() > 0) key = key.trim();
            val = sharedPreferences.getString(key, "");
        } catch (Exception e) {
            val = "";
        }
        return val;
    }
    public static void openAct(Class c, String... code) {
        try {
            Intent intent = new Intent(global_ctx, c);
            if (code.length > 0) {
                for (int i = 0; i < code.length; i++) {
                    intent.putExtra(code[i], code[++i]);
                }
            }
            global_ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
