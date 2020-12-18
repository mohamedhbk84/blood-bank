package com.shareme.bank.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.contact_us.API_Contactus;
import com.shareme.bank.fragments.contact_us.ContactUsResponse;
import com.shareme.bank.fragments.contact_us.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutApp extends Fragment {

    private TextView tx_about_app;
    private SharedPreferences preferences;
    private String api_token;

    public AboutApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_about_app, container, false);

        tx_about_app = inflate.findViewById(R.id.about_app);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");


        response();

        return inflate;
    }


    private void response() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Contactus api_contactus = retrofit.create(API_Contactus.class);
        Call<ContactUsResponse> call = api_contactus.getContact(api_token);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {

                if (response.body().getStatus() == 1) {

                    Data data = response.body().getData();

                    String aboutApp = data.getAboutApp();
                    if (TextUtils.isEmpty(aboutApp)) {
                        tx_about_app.setText("لا تتوفر معلومات حتى الآن");
                    } else
                        tx_about_app.setText(aboutApp);

                }

            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                Log.i("failecontact", t.getMessage());
            }
        });
    }


}
