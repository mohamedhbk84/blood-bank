package com.shareme.bank.fragments.home_fragments.artical_model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API_Articals {

    @GET("posts")
    Call<ArticalResponse> getArticals(
            @Query("api_token") String api_token
    );

}
