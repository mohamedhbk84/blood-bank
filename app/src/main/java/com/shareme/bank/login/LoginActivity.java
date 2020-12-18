package com.shareme.bank.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shareme.bank.MainActivity;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.register.Client;
import com.shareme.bank.register.RegisterActivity;
import com.shareme.bank.reset_pass.API_ResetPass;
import com.shareme.bank.reset_pass.ResetPassActivity;
import com.shareme.bank.storage.SharedPrefMngr;

import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText ed_phone , ed_password ;
    private String phone , password;
    private CheckBox loginRemember;
    private TextView tx_forgetPass;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_phone  = findViewById(R.id.login_phone);
        ed_password = findViewById(R.id.login_pass);
        loginRemember = findViewById(R.id.login_remember);
        tx_forgetPass = findViewById(R.id.forgetPass);

        tx_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this , ResetPassActivity.class));

            }
        });

        if (loginRemember.isChecked()){

            preferences = LoginActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putBoolean("loggedIn", true).apply();

        }


    }




    public void sinIn(View view) {
        checkValidate();
    }

    private void checkValidate() {
         phone = ed_phone.getText().toString();
         password = ed_password.getText().toString();
         if (TextUtils.isEmpty(phone)){
             ed_phone.setError("Enter your phone");
         }else if (TextUtils.isEmpty(password)){
             ed_password.setError("Enter your password");
         }else {
             checkLogin(phone,password);
         }
    }

    private void checkLogin(String phone, String password) {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Login api_login = retrofit.create(API_Login.class);
        Call<LoginResponse> call = api_login.setLogin(phone, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.body().getStatus() == 1){

                    if (loginRemember.isChecked()) {
                        Client user = SharedPrefMngr.getInstance(LoginActivity.this).getUser();
                        String email = user.getEmail();
                        Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }




                }else {

                    String msg = response.body().getMsg();
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void register(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}
