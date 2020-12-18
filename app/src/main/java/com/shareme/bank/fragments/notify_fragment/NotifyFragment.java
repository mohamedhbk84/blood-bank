package com.shareme.bank.fragments.notify_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class NotifyFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotifyAdapter adapter;
    private SharedPreferences preferences;
    private String api_token;


    public NotifyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_notify, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");

        recyclerView = inflate.findViewById(R.id.notify_rv);
        setupRecycle();
        viewData();

        return inflate;
    }


    private void setupRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotifyAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }


    private void viewData() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_NotifyList api_notifyList = retrofit.create(API_NotifyList.class);
        Call<NotifyListResponse> call = api_notifyList.getNotifications(api_token);
        call.enqueue(new Callback<NotifyListResponse>() {
            @Override
            public void onResponse(Call<NotifyListResponse> call, Response<NotifyListResponse> response) {
                if (response.body().getStatus() == 1){
                    NotifyListResponse body = response.body();
                    viewResponse(body);
                }
            }
            @Override
            public void onFailure(Call<NotifyListResponse> call, Throwable t) {

            }
        });
    }

    private void viewResponse(NotifyListResponse body) {
        List<Datum> data = body.getData().getData();
        adapter.sendDataToAdapter(data);
        adapter.notifyDataSetChanged();
    }



}
