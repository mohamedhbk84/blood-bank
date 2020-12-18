package com.shareme.bank.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_Login {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> setLogin(
            @Field("phone") String phone,
            @Field("password") String password);

}
