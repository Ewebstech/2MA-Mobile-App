package com.elrabng.testingreceivingapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elrabng.testingreceivingapp.R;
import com.elrabng.testingreceivingapp.interfaces.API;
import com.elrabng.testingreceivingapp.model.ContentModel;
import com.elrabng.testingreceivingapp.model.LoginRequest;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.network.RetrofitService;
import com.elrabng.testingreceivingapp.utilities.AppPreference;
import com.elrabng.testingreceivingapp.utilities.NetworkChecker;
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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_param)
    EditText emailET;
    @BindView(R.id.password_login)
    EditText passwordET;
    @BindView(R.id.forgot_login)
    TextView forgotPassword;

    private Unbinder unbinder;
    private API api;
    private String email, password;
    ProgressDialog progressDialog;
    Call<LoginResponse> call;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }else{
            // do nothing
        }
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(LoginActivity.this,
                            Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"permission Granted",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"permission not Granted",Toast.LENGTH_SHORT).show();
                }
                return;
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
//
//    @OnClick(R.id.signup_login)
//    public void toReg(){
//        startActivity(new Intent(this, Registration.class));
//    }


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
        progressDialog = new ProgressDialog(LoginActivity.this);
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
                    LoginResponse.Datum userDeatils = response.body().getData();
                    String role = userDeatils.getRole();
                    if (role.equals("doctor")){
                        // not needed just a explanation on how to convert string to json
                        Gson gson = new GsonBuilder().create();
                        ContentModel userDataResponse = gson.fromJson(userDeatils.getContent(),ContentModel.class);
                        userDataResponse.getCalls();
                        //  String token = response.body().getToken();
                        AppPreference.setIsLoggedIn(true);
                        AppPreference.setUserData(response.body());
                        // AppPreference.setToken(token);
                        //startActivity(new Intent(Login.this, NavigationalDrawer.class));
                        startActivity(new Intent(LoginActivity.this, Dashboard.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "You are not authorized to use this app",
                                Toast.LENGTH_SHORT).show();
                    }

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