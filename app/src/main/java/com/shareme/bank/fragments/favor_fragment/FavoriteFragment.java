package com.shareme.bank.fragments.favor_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.favor_fragment.favor_model.FavorAdapter;
import com.shareme.bank.fragments.home_fragments.artical_model.ArticalAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    private RecyclerView recyclerView;
    private FavorAdapter adapter;
    private ImageView imageView;
    private TextView title,details;
    private String api_token;
    private SharedPreferences preferences;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_favorite, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");
        Log.i("api_token",api_token);

        imageView = inflate.findViewById(R.id.details_artical_img);
        title = inflate.findViewById(R.id.details_artical_title);
        details = inflate.findViewById(R.id.details_artical_details);

        Bundle bundle = getArguments();
        if (bundle != null) {

            Datum articalsModel = bundle.getParcelable("favouritesModel");
            Picasso.get().load(articalsModel.getThumbnail()).into(imageView);
            title.setText(articalsModel.getTitle());

            if (articalsModel.getContent() != null) {
                details.setText(articalsModel.getContent());
            }
        }


        recyclerView = inflate.findViewById(R.id.favor_rv);
        setupRecycle();
        viewData();

        return inflate;
    }



    private void setupRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavorAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }


    private void viewData() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Favorite api_favorite = retrofit.create(API_Favorite.class);
        Call<FavoriteResponse> call = api_favorite.getFavorite("my-favourites", api_token);
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {

                if (response.body().getStatus() == 1){
                    FavoriteResponse body = response.body();
                    viewResponse(body);
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {

            }
        });

    }

    private void viewResponse(FavoriteResponse body) {

        List<Datum> data = body.getData().getData();
        adapter.sendDataToAdapter(data);
        adapter.notifyDataSetChanged();

    }








}
