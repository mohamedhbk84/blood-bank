package com.shareme.bank.fragments.home_fragments.create_donation_request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_Create_donation {

    //@FormUrlEncoded
    @POST("donation-request/create")
    Call<CreateRequestResponse> setDonationRequest(
            @Body CreateRequestModel createRequestModel
//            @Field("api_token") String api_token,
//            @Field("patient_name") String patient_name,
//            @Field("patient_age") String patient_age,
//            @Field("blood_type") String blood_type,
//            @Field("bags_num") String bags_num,
//            @Field("hospital_name") String hospital_name,
//            @Field("hospital_address") String hospital_address,
//            @Field("city_id") String city_id,
//            @Field("phone") String phone,
//            @Field("notes") String notes,
//            @Field("latitude") String latitude,
//            @Field("logitude") String logitude
////            @Body object ocr
    );

}
