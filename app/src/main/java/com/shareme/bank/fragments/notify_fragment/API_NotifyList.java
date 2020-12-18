package com.shareme.bank.fragments.notify_fragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_NotifyList {

    @GET("notifications")
    Call<NotifyListResponse> getNotifications(@Query("api_token") String api_token);

}
