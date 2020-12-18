package com.shareme.bank.reset_pass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.login.LoginActivity;
import com.shareme.bank.new_pass.NewPassActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPassActivity extends AppCompatActivity {

    private EditText ed_phone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        ed_phone = findViewById(R.id.reset_number);

    }

    public void sendCode(View view) {
        checkValidate();
    }

    private void checkValidate() {
        phone = ed_phone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ed_phone.setError("Please enter your phone");
        }else
            resetPass();
    }


    private void resetPass() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_ResetPass api_resetPass = retrofit.create(API_ResetPass.class);
        Call<ResetPasssResponse> call = api_resetPass.setResetPass(phone);
        call.enqueue(new Callback<ResetPasssResponse>() {
            @Override
            public void onResponse(Call<ResetPasssResponse> call, Response<ResetPasssResponse> response) {

                if (response.body().getStatus() == 1 ){

                    int pinCodeForTest = response.body().getData().getPinCodeForTest();
                    Toast.makeText(ResetPassActivity.this, pinCodeForTest, Toast.LENGTH_LONG).show();
                    Log.i("pinCodeForTest", String.valueOf(pinCodeForTest));
                    startActivity(new Intent(ResetPassActivity.this , NewPassActivity.class));
                }

            }

            @Override
            public void onFailure(Call<ResetPasssResponse> call, Throwable t) {

            }
        });

    }



}
