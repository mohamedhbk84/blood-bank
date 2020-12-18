package com.shareme.bank.fragments.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.shareme.bank.R;
import com.shareme.bank.city_model.Datum_City;

import java.util.ArrayList;
import java.util.List;

public class Notify_settingAdapter extends RecyclerView.Adapter<Notify_settingAdapter.notify_settingViewHolder> {


    private final Context context;
    private List<Datum_City> cityList;
    onItemClickListner onItemClickListner;
    public ArrayList<Integer> IDS;


    public void setOnItemClickListner(Notify_settingAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    public interface onItemClickListner {
        void onClick(Datum_City datum_city1);

        ArrayList<Integer> IDS = new ArrayList<>();
    }


    public Notify_settingAdapter(Context context) {
        this.context = context;
        IDS = new ArrayList<>();
    }

    public void sendDataToAdapter(List<Datum_City> cityList) {
        this.cityList = cityList;
    }


    @NonNull
    @Override
    public notify_settingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rv_check, viewGroup, false);
        return new notify_settingViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull notify_settingViewHolder holder, final int position) {
        Datum_City datum_city = cityList.get(position);
        holder.tx_city.setText(datum_city.getName());

        holder.tx_city.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    IDS.add(cityList.get(position).getId());

                } else
                    for (int i = 0; i < onItemClickListner.IDS.size(); i++) {
                        if (IDS.get(i).equals(cityList.get(position).getId())) {
                            IDS.remove(position);
                        }
                    }
            }
        });


    }


    @Override
    public int getItemCount() {
        return cityList != null ? cityList.size() : 0;
    }


    class notify_settingViewHolder extends RecyclerView.ViewHolder {

        private CheckBox tx_city;

        public notify_settingViewHolder(@NonNull View itemView) {
            super(itemView);

            tx_city = itemView.findViewById(R.id.check_city);

        }
    }

}
