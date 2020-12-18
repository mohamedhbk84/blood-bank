package com.shareme.bank.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.shareme.bank.register.Client;

public class SharedPrefMngr {

    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefMngr   mInstance;
    private Context mCtx;

    private SharedPrefMngr(Context mCtx){
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefMngr getInstance(Context mCtx){
        if (mInstance == null){
            mInstance= new SharedPrefMngr(mCtx);
        }
        return mInstance;
    }

    public void saveUser(Client user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id",user.getId());
        editor.putString("name",user.getName());
        editor.putString("city_id", user.getCityId());
        editor.putString("blode_type",user.getBloodType());
        editor.putString("email",user.getEmail());
        editor.putString("last_donate",user.getDonationLastDate());
        editor.putString("birth_date",user.getBirthDate());
        editor.putString("phone",user.getPhone());
       // editor.putString("api_token",user.getApi_token());

        editor.apply();

    }

    public boolean isLoggedIn(){
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);

        return  sharedPreferences.getInt("id",-1) != -1;
           // return  sharedPreferences.getBoolean("login",false) != false;
    }



    public Client getUser(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("login", true);

        return new Client(

                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("city_id",null),
                sharedPreferences.getString("blode_type",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("last_donate",null),
                sharedPreferences.getString("phoneOpebirth_date",null),
                sharedPreferences.getString("phone",null)
                //sharedPreferences.getString("api_token",null)

        );

    }



    public void clear(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("login",false);
        editor.clear();
        editor.apply();

    }



}
