package com.shareme.bank.fragments.report_fragment;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_Report {

    @FormUrlEncoded
    @POST("report")
    Call<ReportResponse> setReport(
            @Field("api_token") String api_token,
            @Field("message") String message
    );
}
