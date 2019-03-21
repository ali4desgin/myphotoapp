package com.app.libandroidapp.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    public static SharedPreferences sharedPreferences ;
    public  static  void  saveString(Context context , String  key, String value){
        sharedPreferences = context.getSharedPreferences("local",Context.MODE_PRIVATE);

        sharedPreferences.edit().putString(key,value).apply();
    }



    public  static  String  getString(Context context , String  key){
        sharedPreferences = context.getSharedPreferences("local",Context.MODE_PRIVATE);

        return sharedPreferences.getString(key,"");


    }



    public  static  void   removeAll(Context context ){
        sharedPreferences = context.getSharedPreferences("local",Context.MODE_PRIVATE);

        sharedPreferences.edit().clear().apply();


    }
}
