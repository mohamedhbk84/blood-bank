package com.shareme.bank.fragments.post_details;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_PostsDetails {

    @GET("post")
    Call<PostsDetailResponse> getPostsDetails(
            @Query("api_token") String api_token,
            @Query("post_id") int post_id
    );
}
