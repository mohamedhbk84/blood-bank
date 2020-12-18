package com.shareme.bank.fragments.contact_us;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.fragments.contact_us.contact2.API_Contact2;
import com.shareme.bank.fragments.contact_us.contact2.ContactUs2Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_usFragment extends Fragment {


    private SharedPreferences preferences;
    private String api_token;
    private TextView tx_phone, tx_email;
    private EditText ed_msgTitle, ed_msg;
    private ImageView im_twitter, im_face, im_google, im_youtube, im_insta, im_whats;
    private String phone, email, facebookUrl, twitterUrl, googleUrl, instagramUrl, whatsapp, youtubeUrl, title, msg;
    private Button btn_call, btn_send;
    private String[] permissions;


    public Contact_usFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_contact_us, container, false);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        api_token = preferences.getString("api_token", "");

        tx_phone = inflate.findViewById(R.id.contact_num);
        tx_email = inflate.findViewById(R.id.contact_email);
        im_face = inflate.findViewById(R.id.contact_face);
        im_google = inflate.findViewById(R.id.contact_google);
        im_insta = inflate.findViewById(R.id.contact_insta);
        im_twitter = inflate.findViewById(R.id.contact_twitter);
        im_whats = inflate.findViewById(R.id.contact_whats);
        im_youtube = inflate.findViewById(R.id.contact_youtube);
        btn_call = inflate.findViewById(R.id.contact_call);
        ed_msgTitle = inflate.findViewById(R.id.contact_msgTitle);
        ed_msg = inflate.findViewById(R.id.contact_msg);
        btn_send = inflate.findViewById(R.id.btn_send);

        im_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
                startActivity(browserIntent);
            }
        });

        im_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
                startActivity(browserIntent);
            }
        });

        im_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
                startActivity(browserIntent);
            }
        });

        im_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
                startActivity(browserIntent);
            }
        });

        im_whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pm = getContext().getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");

                    PackageInfo info = pm.getPackageInfo("com.whatsapp",
                            PackageManager.GET_META_DATA);

                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, "تطبيق بنك الدم");
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        im_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl));
                startActivity(browserIntent);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    call_action();
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        permissions = new String[]{Manifest.permission.CALL_PHONE};

        response();

        return inflate;

    }


    private boolean isPermissionGranted() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }


    public void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }


    private void response() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Contactus api_contactus = retrofit.create(API_Contactus.class);
        Call<ContactUsResponse> call = api_contactus.getContact(api_token);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {

                if (response.body().getStatus() == 1) {

                    Data data = response.body().getData();

                    phone = data.getPhone();
                    email = data.getEmail();
                    facebookUrl = data.getFacebookUrl();
                    googleUrl = data.getGoogleUrl();
                    instagramUrl = data.getInstagramUrl();
                    twitterUrl = data.getTwitterUrl();
                    youtubeUrl = data.getYoutubeUrl();
                    whatsapp = data.getWhatsapp();

                    tx_email.setText(email);
                    tx_phone.setText(phone);

                }

            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                Log.i("failecontact", t.getMessage());
            }
        });
    }


    private void send() {

        msg = ed_msg.getText().toString();
        title = ed_msgTitle.getText().toString();

        if (TextUtils.isEmpty(title)) {
            ed_msgTitle.setError("Enter your msg title");
        } else if (TextUtils.isEmpty(this.msg)) {
            ed_msg.setError("Enter your msg");
        } else
            sendMsgResponse();

    }


    private void sendMsgResponse() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Contact2 api_contact2 = retrofit.create(API_Contact2.class);
        Call<ContactUs2Response> call = api_contact2.setContact(api_token, title, msg);
        call.enqueue(new Callback<ContactUs2Response>() {
            @Override
            public void onResponse(Call<ContactUs2Response> call, Response<ContactUs2Response> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Done...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ContactUs2Response> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
