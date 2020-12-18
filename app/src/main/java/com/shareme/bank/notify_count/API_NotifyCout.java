package com.shareme.bank.notify_count;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_NotifyCout {

    @GET("notifications-count")
    Call<NotifyCountResponse> getNotifyCount(
            @Query("api_token") String api_token
    );



}
