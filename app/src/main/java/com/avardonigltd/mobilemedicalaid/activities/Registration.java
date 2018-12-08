package com.avardonigltd.mobilemedicalaid.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.RegistrationErrorResponse;
import com.avardonigltd.mobilemedicalaid.model.RegistrationRequest;
import com.avardonigltd.mobilemedicalaid.model.RegistrationResponse;
import com.avardonigltd.mobilemedicalaid.utilities.NetworkChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    @BindView(R.id.first_name_login)
    EditText firstNameET;
    @BindView(R.id.last_name_reg)
    EditText lastNameET;
    @BindView(R.id.gender_spinner)
    Spinner genderSP;
    @BindView(R.id.phone_reg)
    EditText phoneET;
    @BindView(R.id.email_reg)
    EditText emailET;
    @BindView(R.id.password_et_reg)
    EditText passwordET;
    @BindView(R.id.user_spinner_reg)
    Spinner  userRoleSp;

    ProgressDialog progressDialog;
    private Call<RegistrationResponse> call;
    private String firstName,lastName,email,phone,password, gender, role;
    private Unbinder unbinder;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();
    }
   // This is overriden to perform desired action wen the app closes completely
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
//
    @Override
    protected void onPause() {
        if (call!=null){
            call.cancel();
            call = null;
        }
        super.onPause();
    }

//    //This method is to assign the text on the Edittext to a string variable
    public void initComponents(){
        firstName = firstNameET.getText().toString().trim();
        lastName = lastNameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        phone = phoneET.getText().toString().trim();
        password = passwordET.getText().toString().trim();

    }

//    // This is the method to check if the forms are valid
    public boolean isNumberValid(String code,String phoneNumber){
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(code));
        boolean isValid = false;
        try{
              PhoneNumber phonenumberToPass =  phoneNumberUtil.parse(phoneNumber,isoCode);
              isValid = phoneNumberUtil.isValidNumber(phonenumberToPass);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return isValid;
    }
//
    public boolean valid(){
        initComponents();
        if (TextUtils.isEmpty(firstName)){
            firstNameET.setError("The field cannot not be empty");
            firstNameET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lastName)){
            lastNameET.setError("The field cannot not be empty");
            lastNameET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Invalid Email Address");
            emailET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone)|| !Patterns.PHONE.matcher(phone).matches()|| !isNumberValid("234",phone)){
            phoneET.setError("Invalid Phone Number");
            phoneET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)){
           // passwordET.setError("The field cannot not be empty");
            Toast.makeText(getApplicationContext(),"The field cannot not be empty",Toast.LENGTH_SHORT).show();
            passwordET.requestFocus();
            return false;
        }

        if (password.length()<8){
           // passwordET.setError("Password is less than 8 characters");
            Toast.makeText(getApplicationContext(),"Password is less than 8 characters",Toast.LENGTH_SHORT).show();
            passwordET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(gender)){
            Toast.makeText(getApplicationContext(), "Gender is not picked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @OnClick(R.id.regBtn_reg)
    public void register(){
        if (!valid()){return;}
        if (!NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "No Internet Connection",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        sendDetails(firstName,lastName,email,phone,password,gender,role);
    }

    @OnClick(R.id.backBtnKYC)
    public void goBack(){
        startActivity(new Intent(this, Login.class));
    }

    @OnItemSelected(R.id.user_spinner_reg)
    public void userMethod(){
        role = userRoleSp.getSelectedItem().toString();
    }
    @OnItemSelected(R.id.gender_spinner)
    public void genderMethod(){
        gender = genderSP.getSelectedItem().toString();
    }


    public void sendDetails(String firstName, String lastname, String email, String phone,
                            String password,String gender,String role) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("Registering...");
        progressDialog.show();
       call = api.registrationMethod(new RegistrationRequest(firstName, lastname, email, phone, password, gender,role));
                call.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                       progressDialog.dismiss();
                        Log.i("TAG", "It is reaching the endpoint");
                        if (response.isSuccessful()) {
                            Log.i("TAG", "It is succesfully reaching the endpoint");
                            Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Registration.this, Login.class));
                        }
                        else if (response.code()== 400) {
                            Gson gson = new GsonBuilder().create();
                            try{
                               RegistrationErrorResponse error = gson.fromJson(response.errorBody().string(), RegistrationErrorResponse.class);
                               for(String errorMessage : error.getMessage()){
                                   Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                               }
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }

                            Log.i("TAG", "It is not succesfully reaching the endpoint");
                        }
                        else if (response.code()==401){
                            Gson gson = new GsonBuilder().create();
                            try{
                                RegistrationErrorResponse error = gson.fromJson(response.errorBody().string(),RegistrationErrorResponse.class);
                                for(String errorMessage : error.getMessage()){
                                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else if (response.code()==422){
                            Gson gson = new GsonBuilder().create();
                            try{
                                RegistrationErrorResponse error = gson.fromJson(response.errorBody().string(),RegistrationErrorResponse.class);
                                for(String errorMessage : error.getMessage()){
                                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Unknown Error",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        if (call.isCanceled()) {
                            Log.e("TAG", "request was cancelled");
                        } else {
                            Log.e("TAG", t.getMessage());
                            Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
