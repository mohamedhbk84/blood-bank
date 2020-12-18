package com.shareme.bank.new_pass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shareme.bank.MainActivity;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewPassActivity extends AppCompatActivity {

    EditText ed_pinCode , ed_newPass , ed_confirmNewPass ;
    private String pinCode , newPass , confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);

        ed_pinCode = findViewById(R.id.pin_code);
        ed_newPass = findViewById(R.id.new_pass);
        ed_confirmNewPass = findViewById(R.id.new_confirm_pass);

    }

    public void confirm_new_pass(View view) {
        checkValidate();
    }

    private void checkValidate() {

         pinCode = ed_pinCode.getText().toString();
         newPass = ed_newPass.getText().toString();
         confirmPass = ed_confirmNewPass.getText().toString();

         if (TextUtils.isEmpty(pinCode)){
             ed_pinCode.setError("Enter pin code");
         }else if (TextUtils.isEmpty(newPass)){
             ed_newPass.setError("Enter new pass");
         }else if (TextUtils.isEmpty(confirmPass)){
             ed_confirmNewPass.setError("Enter confirm pass");
         }else
             newPassResponse();

    }

    private void newPassResponse() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_NewPass api_newPass = retrofit.create(API_NewPass.class);
        Call<NewPasssResponse> call = api_newPass.setNewPass(newPass, confirmPass, pinCode);
        call.enqueue(new Callback<NewPasssResponse>() {
            @Override
            public void onResponse(Call<NewPasssResponse> call, Response<NewPasssResponse> response) {

                startActivity(new Intent(NewPassActivity.this , LoginActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<NewPasssResponse> call, Throwable t) {

            }
        });

    }
}
