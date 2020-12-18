package com.shareme.bank.fragments.setting;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_NotifySetting {

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<NotifySettingResponse> setSetting(
            @Field("api_token") String api_token,
            @Field("cities[]") ArrayList<Integer> cities,
            @Field("blood_types[]") ArrayList<String> blood_types
    );
}
