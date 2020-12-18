package com.shareme.bank.fragments.home_fragments.ask_donate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shareme.bank.OnEndless;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.donation_details.Details_Ask_donate_Fragmet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AskDonateFragment extends Fragment {

    private RecyclerView recyclerView;
    private AskDonateAdapter adapter;
    private int id;
    private SharedPreferences preferences;
    private String api_token;
    private OnEndless onEndlesslistener;
    private int maxPage;
    private List<Datum> data;


    public AskDonateFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_ask_donate, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");


        recyclerView = inflate.findViewById(R.id.askdonate_rv);

        setupRecycle();

        adapter.onItemClickedListner(new AskDonateAdapter.onItemClickListner() {
            @Override
            public void onClick(Datum donationDetails) {

                Bundle bundle = new Bundle();
                id = donationDetails.getId();
                bundle.putInt("askDonationDetails_ID", id);

                Fragment fragment = new Details_Ask_donate_Fragmet();
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null).commit();


            }
        });


        viewData(1);

        return inflate;
    }


    private void setupRecycle() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AskDonateAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        onEndlesslistener = new OnEndless((LinearLayoutManager) layoutManager, 20) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...

                if (current_page < maxPage) {
                    // /Your Requst
                    viewData(current_page);
                } else {
                    Toast.makeText(getActivity(), "nothing", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);

    }


    private void viewData(final int page) {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_askDonate api_askDonate = retrofit.create(API_askDonate.class);
        Call<DonationRequestResponse> call = api_askDonate.getDonations(api_token);
        call.enqueue(new Callback<DonationRequestResponse>() {
            @Override
            public void onResponse(Call<DonationRequestResponse> call, Response<DonationRequestResponse> response) {
                if (response.body().getStatus() == 1) {

                    DonationRequestResponse body = response.body();
                    viewResponse(body, page);

                    maxPage = response.body().getData().getLastPage();

                }
            }

            @Override
            public void onFailure(Call<DonationRequestResponse> call, Throwable t) {

            }
        });


    }

    private void viewResponse(DonationRequestResponse body, int page) {

        if (page == 1) {
            this.data = new ArrayList<>();
            this.data = body.getData().getData();
            adapter.sendDataToAdapter(this.data);
        } else {
            this.data.addAll(body.getData().getData());
        }
        adapter.notifyDataSetChanged();
    }


}