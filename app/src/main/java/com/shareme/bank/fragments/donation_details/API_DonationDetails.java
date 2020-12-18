package com.shareme.bank.fragments.donation_details;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_DonationDetails {

    @GET("donation-request")
    Call<DonationDetailsResponse> getDonationDetails(
            @Query("api_token") String api_token,
            @Query("donation_id") int donation_id);
}
