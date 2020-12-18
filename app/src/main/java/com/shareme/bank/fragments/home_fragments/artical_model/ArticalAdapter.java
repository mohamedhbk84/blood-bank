package com.shareme.bank.fragments.home_fragments.artical_model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shareme.bank.MainActivity;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.favor_fragment.API_Favorite;
import com.shareme.bank.fragments.favor_fragment.FavoriteFragment;
import com.shareme.bank.fragments.favor_fragment.FavoriteResponse;
import com.shareme.bank.fragments.favor_fragment.FavoriteTogResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ArticalAdapter extends RecyclerView.Adapter<ArticalAdapter.articalViewHolder> {


    private final Context context;
    private List<Datum> articalList;


    onItemClickListner onItemClickListner;
    private SharedPreferences preferences;

    public void setOnItemClickListner(ArticalAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    public interface onItemClickListner {
        void onClick(Datum articalModel);//pass your object types.
    }


    public ArticalAdapter(Context context) {
        this.context = context;
    }


    public void sendDataToAdapter(List<Datum> articalList) {
        this.articalList = articalList;
    }


    @NonNull
    @Override
    public articalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_artical, viewGroup, false);
        return new articalViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final articalViewHolder holder, final int i) {

        final Datum articalModel = articalList.get(i);

        holder.artical_title.setText(articalModel.getTitle());
        Picasso.get().load("http://ipda3.com/blood-bank" + articalModel.getThumbnail()).into(holder.artical_img);

        holder.artical_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datum datum = articalList.get(i);
                onItemClickListner.onClick(datum);
            }
        });


//        if (articalModel.getFavourite()) {
//            holder.artical_love.setChecked(true);
//        } else {
//            holder.artical_love.setChecked(false);
//        }

//        holder.artical_love.setChecked(articalModel.getFavourite());

        holder.artical_love.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (holder.artical_love.isChecked()) {
                    boolean b = addToFavourite(articalList.get(i).getId());
                    if (b) {
                        articalList.get(i).setFavourite(true);
                        notifyDataSetChanged();

                    }
                } else {
                    boolean b = addToFavourite(articalList.get(i).getId());
                    if (b) {
                        articalList.get(i).setFavourite(false);
                        notifyDataSetChanged();
                    }
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return articalList != null ? articalList.size() : 0;
    }


    class articalViewHolder extends RecyclerView.ViewHolder {

        TextView artical_title;
        ImageView artical_img;
        CheckBox artical_love;


        public articalViewHolder(@NonNull View itemView) {
            super(itemView);

            artical_title = itemView.findViewById(R.id.artical_title);
            artical_img = itemView.findViewById(R.id.artical_img);
            artical_love = itemView.findViewById(R.id.artical_love);


            final Datum articalModel = articalList.get(ArticalAdapter.this.getItemViewType(getAdapterPosition()));


//            artical_love.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    if (artical_love.isChecked()) {
//                        artical_love.setChecked(true);
//
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("articalModel", (Parcelable) articalModel);
//                        Fragment fragment = new FavoriteFragment();
//                        fragment.setArguments(bundle);
//
//                    } else {
//                        artical_love.setChecked(false);
//                    }
//                }
//
//            });
        }
    }


    public Boolean addToFavourite(final int id) {
        final boolean[] isChecked = {false};

        preferences = context.getSharedPreferences("user", MODE_PRIVATE);

        String api_token = preferences.getString("api_token", "default value");

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Favorite favourites_api = retrofit.create(API_Favorite.class);

        Call<FavoriteTogResponse> call = favourites_api.getToggleFavouritesResponse(id, api_token);
        call.enqueue(new Callback<FavoriteTogResponse>() {
            @Override
            public void onResponse(Call<FavoriteTogResponse> call, Response<FavoriteTogResponse> response) {


                if (response.body().getStatus() == 1) {
                    isChecked[0] = true;
                } else {
                    isChecked[0] = false;
                }
            }

            @Override
            public void onFailure(Call<FavoriteTogResponse> call, Throwable t) {
                isChecked[0] = false;
            }
        });

        return isChecked[0];
    }

}





