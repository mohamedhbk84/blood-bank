package com.shareme.bank.fragments.report_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    private EditText ed_report;
    private SharedPreferences preferences;
    private String api_token , report_text;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_report, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");


        ed_report  =inflate.findViewById(R.id.report_text);



        return inflate;
    }

    public void send(View v){

        report_text = ed_report.getText().toString();
        if (report_text.equals("") || report_text == null){
            ed_report.setError("Enter your report");
        }else {
            sendData();
        }

    }

    private void sendData() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Report api_report = retrofit.create(API_Report.class);
        Call<ReportResponse> call = api_report.setReport(api_token, report_text);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {

                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Done...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
