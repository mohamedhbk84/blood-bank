package com.shareme.bank.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shareme.bank.MainActivity;
import com.shareme.bank.R;
import com.shareme.bank.RetrofitClient;
import com.shareme.bank.city_model.API_City;
import com.shareme.bank.city_model.CityResponse;
import com.shareme.bank.city_model.Datum_City;
import com.shareme.bank.governorate_model.API_Governorate;
import com.shareme.bank.governorate_model.Datum_Governorate;
import com.shareme.bank.governorate_model.GovernorateResponse;
import com.shareme.bank.storage.SharedPrefMngr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText ed_name, ed_email, ed_blode_type, ed_phone, ed_pass, ed_pass2;
    private String name, email, birth_date, blode_type, phone, password, password2, last_donate;
    private TextView ed_last_donate, ed_birth_date;
    private long city_id;
    private Spinner spin_city, spin_Governorate;
    private String api_token;
    public int governrate_id;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_name = findViewById(R.id.register_name);
        ed_email = findViewById(R.id.register_email);
        ed_birth_date = findViewById(R.id.register_birthdate);
        ed_blode_type = findViewById(R.id.register_blodkind);
        ed_phone = findViewById(R.id.register_phone);
        ed_pass = findViewById(R.id.register_pass);
        ed_pass2 = findViewById(R.id.register_pass2);
        ed_last_donate = findViewById(R.id.register_donate_date);

        //governorate_spinner
        spin_Governorate = (Spinner) findViewById(R.id.spinner);
        spin_Governorate.setOnItemSelectedListener(this);

        //city_spinner
        spin_city = (Spinner) findViewById(R.id.spinner_city);
        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city_id = spin_city.getSelectedItemPosition() + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                city_id = spin_city.getSelectedItemPosition();
            }
        });


        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener last_donate_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getLastDonationDate();
            }

        };


        ed_last_donate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, last_donate_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        final DatePickerDialog.OnDateSetListener birth_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getBirthDate();
            }

        };


        ed_birth_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, birth_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        viewgovernorates();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        governrate_id = spin_Governorate.getSelectedItemPosition() + 1;
        Log.i("governrate_id", String.valueOf(governrate_id));
        viewCity();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        governrate_id = spin_Governorate.getSelectedItemPosition() + 1;
        viewCity();
    }


    private void getLastDonationDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed_last_donate.setText(sdf.format(myCalendar.getTime()));
    }


    private void getBirthDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed_birth_date.setText(sdf.format(myCalendar.getTime()));
    }


    private void viewCity() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_City api_city = retrofit.create(API_City.class);
        Call<CityResponse> call = api_city.getCity(governrate_id);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.body().getStatus() == 1) {

                    CityResponse body = response.body();
                    viewCityResponse(body);
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });

    }

    private void viewCityResponse(CityResponse body) {

        List<Datum_City> data = body.getData();
        ArrayAdapter cty = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        cty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_city.setAdapter(cty);

    }


    private void viewgovernorates() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Governorate api_governorate = retrofit.create(API_Governorate.class);
        Call<GovernorateResponse> call = api_governorate.getGovernorate();
        call.enqueue(new Callback<GovernorateResponse>() {
            @Override
            public void onResponse(Call<GovernorateResponse> call, Response<GovernorateResponse> response) {

                if (response.body().getStatus() == 1) {

                    GovernorateResponse body = response.body();
                    viewGovernoratesResponse(body);

                }

            }

            @Override
            public void onFailure(Call<GovernorateResponse> call, Throwable t) {

            }
        });

    }

    private void viewGovernoratesResponse(GovernorateResponse body) {

        List<Datum_Governorate> data = body.getData();
        ArrayAdapter gonv = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        gonv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Governorate.setAdapter(gonv);


    }


    //register button
    public void register_register(View view) {
        checkValidate();
    }


    private void checkValidate() {
        name = ed_name.getText().toString();
        email = ed_email.getText().toString();
        birth_date = ed_birth_date.getText().toString();
        blode_type = ed_blode_type.getText().toString();
        phone = ed_phone.getText().toString();
        password = ed_pass.getText().toString();
        password2 = ed_pass2.getText().toString();
        last_donate = ed_last_donate.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ed_name.setError("Enter your name");
        } else if (TextUtils.isEmpty(email)) {
            ed_email.setError("Enter your e-mail");
        } else if (TextUtils.isEmpty(birth_date)) {
            ed_birth_date.setError("Enter your birth date");
        } else if (TextUtils.isEmpty(blode_type)) {
            ed_blode_type.setError("Enter your blode type");
        } else if (TextUtils.isEmpty(phone)) {
            ed_phone.setError("Enter your phone");
        } else if (TextUtils.isEmpty(password)) {
            ed_pass.setError("Enter your password");
        } else if (!TextUtils.equals(password, password2)) {
            ed_pass2.setError("password doesn't match");
            ed_pass2.setText("");
        } else {
            setUserData();
        }


    }

    private void setUserData() {

        Retrofit retrofit = RetrofitClient.getInstance();
        API_Register api_register = retrofit.create(API_Register.class);
        Call<RegisterResponse> call = api_register.setRegister(name, email, birth_date, "1"
                , phone, password, password2, last_donate, blode_type);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {


                if (response.body().getStatus() == 1) {

                    RegisterResponse body = response.body();
                    body.getData().getApiToken();

                    api_token = response.body().getData().getApiToken();
                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor.putString("api_token", api_token);
                    editor.apply();

                    SharedPrefMngr.getInstance(RegisterActivity.this)
                            .saveUser(body.getData().getClient());


                    String msg = response.body().getMsg();
                    Toast.makeText(RegisterActivity.this, name, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else if (response.body().getStatus() == 0) {
                    String msg = response.body().getMsg();
                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });

    }

}
