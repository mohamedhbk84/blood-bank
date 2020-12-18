package com.shareme.bank.city_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_City {

    @GET("cities")
    Call<CityResponse> getCity(@Query("governorate_id") int governorate_id);

    @GET("cities")
    Call<CityResponse> getAllCity();

}
