package com.shareme.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit getInstance() {

        return new Retrofit.Builder()
                .baseUrl("http://ipda3.com/blood-bank/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
