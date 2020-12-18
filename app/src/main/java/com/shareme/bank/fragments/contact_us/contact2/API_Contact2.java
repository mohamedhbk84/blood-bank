package com.shareme.bank.fragments.contact_us.contact2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_Contact2 {

    @FormUrlEncoded
    @POST("contact")
    Call<ContactUs2Response> setContact(
            @Field("api_token") String api_token,
            @Field("title") String title,
            @Field("message") String message
    );

}
