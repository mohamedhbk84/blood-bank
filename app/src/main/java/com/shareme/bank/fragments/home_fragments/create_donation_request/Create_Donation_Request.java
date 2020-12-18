package com.shareme.bank.fragments.home_fragments.create_donation_request;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.city_model.API_City;
import com.shareme.bank.city_model.CityResponse;
import com.shareme.bank.city_model.Datum_City;
import com.shareme.bank.fragments.home_fragments.HomeFragment;
import com.shareme.bank.governorate_model.API_Governorate;
import com.shareme.bank.governorate_model.Datum_Governorate;
import com.shareme.bank.governorate_model.GovernorateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Create_Donation_Request extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText ed_name, ed_age, ed_blodeType, ed_bags_num,
            ed_hospital_name, ed_hospital_address, ed_phone, ed_notes;
    private String name, age, blode_type, bags_num, hospital_name, hospital_address, phone, notes;
    private Button bu_createRequest;
    private String api_token;
    private SharedPreferences preferences;
    private Spinner spin_Governorate, spin_city;
    private int governrate_id, city_id = 1;


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_create_donation_request, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");

        ed_name = inflate.findViewById(R.id.ask_for_donate_name);
        ed_age = inflate.findViewById(R.id.ask_for_donate_age);
        ed_blodeType = inflate.findViewById(R.id.ask_for_donate_blodeType);
        ed_bags_num = inflate.findViewById(R.id.bags_numbers);
        ed_hospital_name = inflate.findViewById(R.id.hospital_name);
        ed_hospital_address = inflate.findViewById(R.id.hospital_address);
        ed_phone = inflate.findViewById(R.id.ask_for_donate_phone);
        ed_notes = inflate.findViewById(R.id.ask_for_donate_notes);

        bu_createRequest = inflate.findViewById(R.id.bu_create_request);
        bu_createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });


        spin_Governorate = (Spinner) inflate.findViewById(R.id.ask_for_donate_spinner_town);
        spin_Governorate.setOnItemSelectedListener(this);

        spin_city = (Spinner) inflate.findViewById(R.id.ask_for_donate_spinner_city);
        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city_id = spin_city.getSelectedItemPosition() + 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city_id = spin_city.getSelectedItemPosition() + 1;
            }
        });

        viewgovernorates();

        return inflate;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        governrate_id = spin_Governorate.getSelectedItemPosition() + 1;
        viewCity();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void checkValidate() {

        name = ed_name.getText().toString();
        age = ed_age.getText().toString();
        blode_type = ed_blodeType.getText().toString();
        bags_num = ed_bags_num.getText().toString();
        hospital_name = ed_hospital_name.getText().toString();
        hospital_address = ed_hospital_address.getText().toString();
        phone = ed_phone.getText().toString();
        notes = ed_notes.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ed_name.setError("Enter your name");
        } else if (TextUtils.isEmpty(age)) {
            ed_age.setError("Enter your e-mail");
        } else if (TextUtils.isEmpty(bags_num)) {
            ed_bags_num.setError("Enter your number of bags");
        } else if (TextUtils.isEmpty(blode_type)) {
            ed_blodeType.setError("Enter your blode type");
        } else if (TextUtils.isEmpty(phone)) {
            ed_phone.setError("Enter your phone");
        } else if (TextUtils.isEmpty(notes)) {
            ed_notes.setError("Enter your notes");
        } else if (TextUtils.isEmpty(hospital_name)) {
            ed_hospital_name.setError("Enter hospital name");

        } else if (TextUtils.isEmpty(hospital_address)) {
            ed_hospital_address.setError("Enter hospital adddress");
        } else if (city_id == 0) {
            Toast.makeText(getContext(), "Please choose city", Toast.LENGTH_SHORT).show();
        }
        createRequest();

    }


    private void viewgovernorates() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Governorate api_governorate = retrofit.create(API_Governorate.class);
        Call<GovernorateResponse> call = api_governorate.getGovernorate();
        call.enqueue(new Callback<GovernorateResponse>() {
            @Override
            public void onResponse(Call<GovernorateResponse> call, Response<GovernorateResponse> response) {

                if (response.body().getStatus() == 1) {

                    GovernorateResponse body = response.body();
                    viewGovernoratesResponse(body);

                }

            }

            @Override
            public void onFailure(Call<GovernorateResponse> call, Throwable t) {

            }
        });

    }

    private void viewGovernoratesResponse(GovernorateResponse body) {

        List<Datum_Governorate> data = body.getData();
        ArrayAdapter gonv = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, data);
        gonv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Governorate.setAdapter(gonv);


    }


    private void viewCity() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_City api_city = retrofit.create(API_City.class);
        Call<CityResponse> call = api_city.getCity(governrate_id);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.body().getStatus() == 1) {

                    CityResponse body = response.body();
                    viewCityResponse(body);
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });

    }

    private void viewCityResponse(CityResponse body) {

        List<Datum_City> data = body.getData();
        ArrayAdapter cty = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, data);
        cty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_city.setAdapter(cty);

    }


    private void createRequest() {

        CreateRequestModel requestModel = new CreateRequestModel();
        requestModel.setApi_token(api_token);
        requestModel.setPatient_name(name);
        requestModel.setPatient_age(age);
        requestModel.setBlood_type(blode_type);
        requestModel.setBags_num(bags_num);
        requestModel.setHospital_name(hospital_name);
        requestModel.setHospital_address(hospital_address);
        requestModel.setCity_id(city_id);
        requestModel.setPhone(phone);
        requestModel.setNotes(notes);
        requestModel.setLatitude("31.7655");
        requestModel.setLogitude("30.7541");

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Create_donation api_create_donation = retrofit.create(API_Create_donation.class);
        Call<CreateRequestResponse> call = api_create_donation.setDonationRequest(requestModel);
        call.enqueue(new Callback<CreateRequestResponse>() {
            @Override
            public void onResponse(Call<CreateRequestResponse> call, Response<CreateRequestResponse> response) {

                CreateRequestResponse body = response.body();
                Log.e("reespon", "" + body.getStatus());
                if (body.getStatus() == 1) {

                    Toast.makeText(getContext(), "Done...", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();

                } else if (body.getStatus() == 0) {
                    Toast.makeText(getContext(), body.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CreateRequestResponse> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("reespon", "" + t.getMessage());


            }
        });
    }


}
