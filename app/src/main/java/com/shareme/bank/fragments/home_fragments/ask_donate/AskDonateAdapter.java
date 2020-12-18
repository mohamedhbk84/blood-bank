package com.shareme.bank.fragments.home_fragments.ask_donate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.fragments.donation_details.Details_Ask_donate_Fragmet;

import java.util.List;

public class AskDonateAdapter extends RecyclerView.Adapter<AskDonateAdapter.askDonateViewHolder> {


    private final Context context;
    private List<Datum> askDonateList;
    private onItemClickListner onItemClickedListner;


    public interface onItemClickListner{
        void onClick(Datum articalModel);//pass your object types.
    }

    public void onItemClickedListner(AskDonateAdapter.onItemClickListner onItemClickListner){
        this.onItemClickedListner = onItemClickListner;
    }



    public AskDonateAdapter(Context context) {
        this.context = context;
    }


    public void sendDataToAdapter(List<Datum> askDonateList) {
        this.askDonateList = askDonateList;
    }


    @NonNull
    @Override
    public askDonateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_donate, viewGroup, false);
        return new askDonateViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull askDonateViewHolder holder, final int position) {

        Datum askDonateModel = askDonateList.get(position);
        holder.blode.setText(askDonateModel.getBloodType());
        holder.name.setText(askDonateModel.getPatientName());
        holder.hospital.setText(askDonateModel.getHospitalName());
       // holder.city.setText(askDonateModel.getCity().getName());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Datum datum = askDonateList.get(position);
                onItemClickedListner.onClick(datum);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Datum datum = askDonateList.get(position);
                String phone = datum.getPhone();

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return askDonateList != null ? askDonateList.size() : 0;
    }



    class askDonateViewHolder extends RecyclerView.ViewHolder {

        private final String[] permissions;
        TextView name , blode , hospital , city;
        Button details , call;

        public askDonateViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.donate_name);
            blode = itemView.findViewById(R.id.donate_blode_type);
            hospital = itemView.findViewById(R.id.donate_hospital);
            city = itemView.findViewById(R.id.donate_city);
            details = itemView.findViewById(R.id.donate_btn_details);
            call = itemView.findViewById(R.id.donate_btn_call);

            permissions = new String[]{Manifest.permission.CALL_PHONE};

//
//            details.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Datum askDonateModel = askDonateList.get(AskDonateAdapter.this.getItemViewType(getAdapterPosition()));
//
//                    onItemClickedListner.onClick(askDonateModel);
//
//                }
//            });

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }


    }


}
