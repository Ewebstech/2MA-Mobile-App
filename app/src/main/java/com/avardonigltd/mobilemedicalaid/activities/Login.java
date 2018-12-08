package com.avardonigltd.mobilemedicalaid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.LoginRequest;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.RegistrationResponse;
import com.avardonigltd.mobilemedicalaid.utilities.NetworkChecker;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

        @BindView(R.id.login_param)
        EditText emailET;
        @BindView(R.id.password_login)
        EditText passwordET;
        @BindView(R.id.forgot_login)
        TextView forgotPassword;
        @BindView(R.id.signup_login)
        TextView signUp;

        private Unbinder unbinder;
        private API api;
        private String email, password;
        ProgressDialog progressDialog;
        Call<LoginResponse> call;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();

        if (AppPreference.isFirstTimeLaunched()) {
            AppPreference.setIsFirstTimeToKyc(true);
        }
    }

        @Override
        protected void onPause () {
        if (call != null) {
            call.cancel();
            call = null;
        }
        super.onPause();
    }

        @Override
        protected void onDestroy () {
        super.onDestroy();
        unbinder.unbind();
    }

        public void assignment () {
        email = emailET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
    }

        public boolean validated () {
        assignment();
        if (TextUtils.isEmpty(email)) {
            emailET.setError("Field is required");
            emailET.requestFocus();
            Log.i("TAG","Invalid Email");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
           // passwordET.setError("Field cannot be empty");
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_SHORT).show();
            Log.i("TAG","Field cannot be empty");
            passwordET.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick(R.id.forgot_login)
    public void toForgotPassword(){
        startActivity(new Intent(this, ForgotPassword.class));
    }

    @OnClick(R.id.signup_login)
    public void toReg(){
        startActivity(new Intent(this, Registration.class));
    }


    @OnClick(R.id.login_btn)
        public void loginInMethod () {
            Log.i("TAG","Clicked");
        if (!validated()) {
            Log.i("TAG","valid field");
            return;
        }
        if (!NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "No internet connection",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        sendDetails(email, password);
    }
    public boolean isNumberValid(String code,String phoneNumber){
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(code));
        boolean isValid = false;
        try{
            Phonenumber.PhoneNumber phonenumberToPass =  phoneNumberUtil.parse(phoneNumber,isoCode);
            isValid = phoneNumberUtil.isValidNumber(phonenumberToPass);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return isValid;
    }

        public void sendDetails (String email, String password){
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setProgressStyle(R.style.ProgressBarTheme);
            progressDialog.setMessage("Signing....");
            progressDialog.show();
             call = api.loginMethod(new LoginRequest(email, password));
            call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.i("TAG", "It is reaching here");
                    Log.i("TAG", "It is successful");
                    RegistrationResponse.Datum userDeatils = response.body().getData();
                    String token = response.body().getToken();
                    AppPreference.setUserData(response.body());
                    AppPreference.setToken(token);
                    startActivity(new Intent(Login.this, NavigationalDrawer.class));
                } else if (response.code() == 400) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        Log.i("TAG", "It has error1 400");
                        LoginResponse error = gson.fromJson(response.errorBody().string(), LoginResponse.class);
                        Log.i("TAG", "It has error2 400 " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    Gson gson = new GsonBuilder().create();
                    try {
                        Log.i("TAG", "It has error unknown");
                        LoginResponse error = gson.fromJson(response.errorBody().string(), LoginResponse.class);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("TAG", "It is reaching here");
                progressDialog.dismiss();
                if (call.isCanceled()) {

                    Log.e("TAG", "request was cancelled");
                } else {
                    Log.e("TAG", "other larger issue, i.e. no network connection?");
                    Log.e("TAG", t.getMessage());
                    Toast.makeText(getBaseContext(), "Server issue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

