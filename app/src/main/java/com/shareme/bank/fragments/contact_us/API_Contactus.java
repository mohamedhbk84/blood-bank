package com.shareme.bank.fragments.contact_us;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Contactus {

    @GET("settings")
    Call<ContactUsResponse> getContact(
            @Query("api_token") String api_token
    );
}
