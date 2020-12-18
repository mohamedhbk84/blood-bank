package com.shareme.bank.fragments.home_fragments.ask_donate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API_askDonate {

//    @GET("donation-requests")
//    Call<DonationRequestResponse> getDonations(
//              @Query("api_token") String api_token ,
//              @Query("blood_type") String bt,
//              @Query("city_id") String ci);

    @GET("donation-requests")
    Call<DonationRequestResponse> getDonations(
            @Query("api_token") String api_token
//            @Query("blood_type") String blood_type ,
//            @Query("city_id") String city_id
    );

}
