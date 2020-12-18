package com.shareme.bank.register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_Register {

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> setRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("birth_date") String birth_date,
            @Field("city_id") String city_id,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("donation_last_date") String donation_last_date,
            @Field("blood_type") String blood_type);


}
