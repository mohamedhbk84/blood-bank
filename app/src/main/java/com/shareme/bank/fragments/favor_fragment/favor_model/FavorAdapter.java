package com.shareme.bank.fragments.favor_fragment.favor_model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.fragments.favor_fragment.Datum;
import com.shareme.bank.fragments.favor_fragment.FavoriteFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavorAdapter extends RecyclerView.Adapter<FavorAdapter.favorViewHolder> {


    private final Context context;
    private List<Datum> dataList;

    public FavorAdapter(Context context) {
        this.context = context;
    }

    public void sendDataToAdapter(List<Datum> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public favorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_artical, viewGroup, false);
        return new favorViewHolder(inflate);
    }



    @Override
    public void onBindViewHolder(@NonNull favorViewHolder holder, int i) {

        Datum datum = dataList.get(i);
        holder.artical_title.setText(datum.getTitle());
        Picasso.get().load(datum.getThumbnailFullPath()).into(holder.artical_img);

    }



    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }



    class favorViewHolder extends RecyclerView.ViewHolder {

        TextView artical_title;
        ImageView artical_img ;
        CheckBox artical_love;

        public favorViewHolder(@NonNull View itemView) {

            super(itemView);

            artical_title = itemView.findViewById(R.id.artical_title);
            artical_img = itemView.findViewById(R.id.artical_img);
            artical_love = itemView.findViewById(R.id.artical_love);



            final Datum favouritesArticals = dataList.get(FavorAdapter.this.getItemViewType(getAdapterPosition()));

            if (artical_love.isChecked()){

                Intent intent = new Intent(context,FavoriteFragment.class);
                Bundle b = new Bundle();
                b.putParcelable("favouritesModel",  favouritesArticals);
                intent.putExtras(b);
                context.startActivity(intent);
            }

        }
    }


}
