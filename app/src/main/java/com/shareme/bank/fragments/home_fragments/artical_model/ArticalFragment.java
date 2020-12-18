package com.shareme.bank.fragments.home_fragments.artical_model;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.shareme.bank.OnEndless;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.post_details.Details_Artical_Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticalFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticalAdapter adapter;
    private EditText search;
    private OnEndless onEndlesslistener;
    private int maxPage;
    private SharedPreferences preferences;
    private String api_token;
    private List<Datum> articalData;

    public ArticalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_artical, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");

        recyclerView = inflate.findViewById(R.id.artical_rv);

        setupRecycle();
        viewData(1);
        onTouchAdapter();


        return inflate;
    }

    public void bu_create_request(View view) {

    }

    private void setupRecycle() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticalAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        onEndlesslistener = new OnEndless((LinearLayoutManager) layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...

                if (current_page < maxPage) {
                    // /Your Requst
                    viewData(current_page);
                } else {
                    // Toast.makeText(getActivity(), "nothing", Toast.LENGTH_SHORT).show();
                }
            }
        };

        recyclerView.addOnScrollListener(onEndlesslistener);
    }

    private void viewData(final int page) {

        String urlWithPag = api_token + "&page=" + page;

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Articals api_articals = retrofit.create(API_Articals.class);
        Call<ArticalResponse> call = api_articals.getArticals(api_token);
        call.enqueue(new Callback<ArticalResponse>() {
            @Override
            public void onResponse(Call<ArticalResponse> call, Response<ArticalResponse> response) {
                if (response.body().getStatus() == 1) {

                    ArticalResponse body = response.body();
                    viewResponse(body, page);

                    maxPage = response.body().getArticalData().getLastPage();

                }

            }

            @Override
            public void onFailure(Call<ArticalResponse> call, Throwable t) {

            }
        });
    }


    private void viewResponse(ArticalResponse body, int page) {

        if (page == 1) {
            this.articalData = new ArrayList<>();
            this.articalData = body.getArticalData().getData();
            adapter.sendDataToAdapter(this.articalData);
        } else {
            this.articalData.addAll(body.getArticalData().getData());
        }

        adapter.notifyDataSetChanged();

    }


    private void onTouchAdapter() {
        adapter.setOnItemClickListner(new ArticalAdapter.onItemClickListner() {
            @Override
            public void onClick(Datum articalModel) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("articalModel", articalModel);

                Fragment fragment = new Details_Artical_Fragment();
                fragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null).commit();

            }
        });


    }

}
