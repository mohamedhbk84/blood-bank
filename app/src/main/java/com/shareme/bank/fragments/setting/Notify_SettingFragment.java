package com.shareme.bank.fragments.setting;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.city_model.API_City;
import com.shareme.bank.city_model.CityResponse;
import com.shareme.bank.city_model.Datum_City;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notify_SettingFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btn_save;
    private CheckBox negative_O, positive_O, negative_A, positive_A, negative_AB, positive_AB;
    private Notify_settingAdapter adapter;
    private int city_id;
    private SharedPreferences preferences;
    private String api_token;
    private ArrayList<Integer> IDS;
    private ArrayList<String> blood_types;

    public Notify_SettingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_notify__setting, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");

        negative_O = inflate.findViewById(R.id.O_);
        positive_O = inflate.findViewById(R.id.O);
        negative_A = inflate.findViewById(R.id.A_);
        positive_A = inflate.findViewById(R.id.A);
        negative_AB = inflate.findViewById(R.id.AB_);
        positive_AB = inflate.findViewById(R.id.AB);
        recyclerView = inflate.findViewById(R.id.check_rv);
        btn_save = inflate.findViewById(R.id.btn_setting_save);

        IDS = new ArrayList<>();
        blood_types = new ArrayList<>();

        CheckedChangeListener(negative_O);
        CheckedChangeListener(positive_O);
        CheckedChangeListener(negative_A);
        CheckedChangeListener(positive_A);
        CheckedChangeListener(negative_AB);
        CheckedChangeListener(positive_AB);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IDS = adapter.IDS;
                saveSetting();
                Toast.makeText(getContext(), "" + IDS, Toast.LENGTH_SHORT).show();


            }
        });


        setupRecycle();
        cityResponse();


        return inflate;
    }

    private void CheckedChangeListener(final CheckBox checkBox) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    blood_types.add(checkBox.getText().toString());

                } else
                    for (int i = 0; i < blood_types.size(); i++) {

                        if (blood_types.get(i).equals(checkBox.getText().toString())) {
                            blood_types.remove(i);
                        }

                    }

            }
        });
    }


    private void saveSetting() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_NotifySetting api_notifySetting = retrofit.create(API_NotifySetting.class);
        Call<NotifySettingResponse> call = api_notifySetting.setSetting(api_token, IDS, blood_types);
        call.enqueue(new Callback<NotifySettingResponse>() {
            @Override
            public void onResponse(Call<NotifySettingResponse> call, Response<NotifySettingResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<NotifySettingResponse> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }


    private void setupRecycle() {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Notify_settingAdapter(getActivity());
        recyclerView.setAdapter(adapter);

    }


    private void cityResponse() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_City api_city = retrofit.create(API_City.class);
        Call<CityResponse> call = api_city.getAllCity();
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {

                    CityResponse body = response.body();
                    viewResponse(body);

                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }

    private void viewResponse(CityResponse body) {
        List<Datum_City> data = body.getData();
        adapter.sendDataToAdapter(data);
        adapter.notifyDataSetChanged();
    }


}
