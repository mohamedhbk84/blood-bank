package com.shareme.bank.fragments.donation_details;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details_Ask_donate_Fragmet extends Fragment {


    private Button call;
    private TextView tx_name , tx_age , tx_bloodType , tx_bagsNu , tx_hospitalName , tx_hospitalAddress , tx_phone , tx_notes;
    private SharedPreferences preferences;
    private String api_token;
    private int id;
    private String[] permissions;

    public Details_Ask_donate_Fragmet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_details__ask_donate__fragmet, container, false);

        tx_name = inflate.findViewById(R.id.details_donate_name);
        tx_age = inflate.findViewById(R.id.details_donate_age);
        tx_bloodType = inflate.findViewById(R.id.details_donate_blodeType);
        tx_bagsNu = inflate.findViewById(R.id.details_donate_bags_numbers);
        tx_hospitalName = inflate.findViewById(R.id.hospital_name);
        tx_hospitalAddress = inflate.findViewById(R.id.hospital_address);
        tx_phone = inflate.findViewById(R.id.details_donate_phone);
        tx_notes = inflate.findViewById(R.id.details_donate_notes);
        call = inflate.findViewById(R.id.ask_donate_call_btn);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");

        Bundle arguments = getArguments();
        if (arguments != null){
            id = arguments.getInt("askDonationDetails_ID");
        }

        viewData();

        permissions = new String[]{ Manifest.permission.CALL_PHONE };
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()){
                    call_action();
                }
            }
        });

        return inflate;
    }



    private boolean isPermissionGranted() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }


    public void call_action(){
        String phone = tx_phone.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }



    private void viewData() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_DonationDetails api_donationDetails = retrofit.create(API_DonationDetails.class);
        Call<DonationDetailsResponse> call = api_donationDetails.getDonationDetails(api_token , id);
        Log.i("my_id" , String.valueOf(id));
        call.enqueue(new Callback<DonationDetailsResponse>() {
            @Override
            public void onResponse(Call<DonationDetailsResponse> call, Response<DonationDetailsResponse> response) {
                if (response.body().getStatus() == 1){

                        Data data = response.body().getData();
                        String bagsNum = data.getBagsNum();
                        String bloodType = data.getBloodType();
                        String hospitalName = data.getHospitalName();
                        String hospitalAddress = data.getHospitalAddress();
                        String notes = data.getNotes();
                        String patientName = data.getPatientName();
                        String patientAge = data.getPatientAge();
                        String phone = data.getPhone();

                        tx_name.setText(patientName);
                        tx_age.setText(patientAge);
                        tx_bagsNu.setText(bagsNum);
                        tx_bloodType.setText(bloodType);
                        tx_hospitalName.setText(hospitalName);
                        tx_hospitalAddress.setText(hospitalAddress);
                        tx_phone.setText(phone);
                        tx_notes.setText(notes);

                }else
                    Log.i("detailsRes" , String.valueOf(response.body().getStatus()));
            }

            @Override
            public void onFailure(Call<DonationDetailsResponse> call, Throwable t) {
                Log.i("detailsRes" , t.getMessage());

            }
        });

    }

}
