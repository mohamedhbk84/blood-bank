package com.shareme.bank.fragments.post_details;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.home_fragments.artical_model.Datum;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details_Artical_Fragment extends Fragment {


    ImageView img;
    TextView title , details ;
    private String api_token;
    private SharedPreferences preferences;
    private int id;


    public Details_Artical_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_details__artical_, container, false);

        img = inflate.findViewById(R.id.details_artical_img);
        title = inflate.findViewById(R.id.details_artical_title);
        details = inflate.findViewById(R.id.details_artical_details);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token =preferences.getString("api_token","");


        Bundle arguments = getArguments();

        if (arguments != null ){
            Datum articalModel = arguments.getParcelable("articalModel");

            if (articalModel.getContent() != null){
                id = articalModel.getId();
            }

        }

        detailsResponse();

        return inflate;
    }

    private void detailsResponse() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_PostsDetails api_postsDetails = retrofit.create(API_PostsDetails.class);
        Call<PostsDetailResponse> call = api_postsDetails.getPostsDetails(api_token, id);
        call.enqueue(new Callback<PostsDetailResponse>() {
            @Override
            public void onResponse(Call<PostsDetailResponse> call, Response<PostsDetailResponse> response) {

                if (response.body().getStatus() == 1 ){
                    Data data = response.body().getData();

                    title.setText(data.getTitle());
                    details.setText(data.getContent());
                    Picasso.get().load(data.getThumbnailFullPath()).into(img);


                }
            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

    }

}
