package com.shareme.bank.reset_pass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_ResetPass {

    @FormUrlEncoded
    @POST("reset-password")
    Call<ResetPasssResponse> setResetPass(
            @Field("phone") String phone
    );

}
