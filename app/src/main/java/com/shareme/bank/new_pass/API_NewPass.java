package com.shareme.bank.new_pass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_NewPass {

    @FormUrlEncoded
    @POST("new-password")
    Call<NewPasssResponse> setNewPass(
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("pin_code") String pin_code
    );
}
