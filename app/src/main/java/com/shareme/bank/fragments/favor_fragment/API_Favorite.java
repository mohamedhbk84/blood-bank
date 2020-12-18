package com.shareme.bank.fragments.favor_fragment;

import com.shareme.bank.fragments.home_fragments.ask_donate.DonationRequestResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Favorite {

    @FormUrlEncoded
    @POST("post-toggle-favourite")
    Call<FavoriteTogResponse>getToggleFavouritesResponse(
            @Field("post_id") int id,
            @Field("api_token") String api_token
    );

    @GET("{sort}")
    Call<FavoriteResponse> getFavorite(@Path("sort") String sort , @Query("api_token") String api_token);
}