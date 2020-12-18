package com.shareme.bank.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.shareme.bank.R;
import com.shareme.bank.register.Client;
import com.shareme.bank.storage.SharedPrefMngr;


public class InfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private TextView tx_name , tx_email , tx_birthDate , tx_bloodType , tx_phone , tx_lastDonate  ;


    public InfoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_info, container, false);

        tx_name = inflate.findViewById(R.id.info_name);
        tx_email = inflate.findViewById(R.id.info_email);
        tx_birthDate = inflate.findViewById(R.id.info_birthDate);
        tx_bloodType = inflate.findViewById(R.id.info_blodkind);
        tx_phone = inflate.findViewById(R.id.info_phone);
        tx_lastDonate = inflate.findViewById(R.id.info_donate_date);

        Client user = SharedPrefMngr.getInstance(getActivity()).getUser();
        tx_name.setText(user.getName());
        tx_email.setText(user.getEmail());
        tx_phone.setText(user.getPhone());
        tx_bloodType.setText(user.getBloodType());
        tx_birthDate.setText(user.getBirthDate());
        tx_lastDonate.setText(user.getDonationLastDate());

        String cityId = user.getCityId();

        return inflate;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}