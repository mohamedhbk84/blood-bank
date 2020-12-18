package com.shareme.bank.governorate_model;


import retrofit2.Call;
import retrofit2.http.GET;

public interface API_Governorate {

    @GET("governorates")
    Call<GovernorateResponse> getGovernorate();

}
