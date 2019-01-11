package com.elrabng.testingreceivingapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.elrabng.testingreceivingapp.R;
import com.elrabng.testingreceivingapp.interfaces.API;
import com.elrabng.testingreceivingapp.model.CallerErrorResponse;
import com.elrabng.testingreceivingapp.model.CallerRequest;
import com.elrabng.testingreceivingapp.model.CallerResponse;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.network.RetrofitService;
import com.elrabng.testingreceivingapp.utilities.AppPreference;
import com.elrabng.testingreceivingapp.utilities.NetworkChecker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallerStatus extends AppCompatActivity {

    @BindView(R.id.clientNameTV)
    TextView clientNameTv;
    @BindView(R.id.clientNumberTV)
    TextView clientNumberTv;
    @BindView(R.id.subStatusTV)
    TextView subStatusTv;
    @BindView(R.id.callerInfoPage)
    ConstraintLayout callerInfoPage;

    @BindView(R.id.clientNameTVInactive)
    TextView clientNameInactiveTv;
    @BindView(R.id.clientNumberTVInactive)
    TextView clientNumberInactiveTV;
    @BindView(R.id.subStatusTVInactive)
    TextView subStatusInactiveTv;
    @BindView(R.id.callerInfoPageInactive)
    ConstraintLayout callerInfoPageInactive;

    private API api;
    private Call<CallerResponse> call;
    private  ProgressDialog progressDialog;
    private String clientName,subStatus ,phoneNumber,caseID;
    String clientPhoneNumber;
    String doctorId;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        setContentView(R.layout.activity_caller_status);

        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        clientPhoneNumber = intent.getStringExtra("phonenumber");
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
            doctorId= loginResponse.getData().getClientId();
        }
        Log.i("TAG","number received"+ clientPhoneNumber);
        api = RetrofitService.initializer();//
         onStartService(clientPhoneNumber);
        //Handler handler = new Handler();
      //  getUserStatus(doctorId,"08023119233");
    }

    public void getUserStatus(String doctorId, String clientPhoneNumber){
        if (!NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "Please check your internet connection",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(CallerStatus.this);
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        call = api.getCallerInfoMethod(new CallerRequest(doctorId,clientPhoneNumber));
        call.enqueue(new Callback<CallerResponse>() {
            @Override
            public void onResponse(Call<CallerResponse> call, Response<CallerResponse> response) {
                progressDialog.dismiss();
                Log.i("TAG","It is succesful");
                if(response.isSuccessful()){
                    Log.i("TAG","It is succesful block");
                    boolean status = response.body().isStatus();
                    Log.i("TAG",response.body().isStatus()+"");
                    if(status){
                        Log.i("TAG","boolean success");
                        CallerResponse.Data detailsReceived = response.body().getData();
                        if(detailsReceived!=null){
                            clientName = detailsReceived.getClientName();
                            subStatus = detailsReceived.getSubStatus();
                            phoneNumber = detailsReceived.getClientPhoneNumber();
                            caseID = detailsReceived.getCaseId();

                            if (subStatus.equals("Active")){
                                clientNameTv.setText(clientName);
                                subStatusTv.setText(subStatus);
                                clientNumberTv.setText(phoneNumber);
                                callerInfoPage.setVisibility(View.VISIBLE);
                                // TODO after this action is done a button can be enabled to send the
                                // TODO information about a call
                            }else if(subStatus.equals("InActive")){
                                clientNameInactiveTv.setText(clientName);
                                clientNumberInactiveTV.setText(phoneNumber);
                                subStatusInactiveTv.setText(subStatus);
                                callerInfoPageInactive.setVisibility(View.VISIBLE);
                                // TODO The call will need to be terminated here.
                            }
                        }
                        else {
                            Log.i("TAG","data is null");
                        }}
                    }else {
                       Log.i("TAG","error block");
                        if(response.code() == 400){
                            // not selcted a package or something
                            Log.i("TAG","error 400");
                            Gson gson = new GsonBuilder().create();
                            try{
                                CallerResponse error = gson.fromJson(response.errorBody().string(),CallerResponse.class);
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
//                        Toast.makeText(getApplicationContext(), "It is not a 2MA patient",
//                                Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        }
                        else if (response.code()== 422){
                            // empty field error
                            Log.i("TAG","error 422");
                            Gson gson = new GsonBuilder().create();
                            try{
                                CallerErrorResponse error = gson.fromJson(response.errorBody().string(),CallerErrorResponse.class);
                                List<String> errorObtained = error.getMessage();
                                for(String errorMessage:errorObtained){
                                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();

                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                            finishAffinity();
                        }
                        else if (response.code()== 404){
                            // empty field error
                            Log.i("TAG","error 404");
                         Toast.makeText(getApplicationContext(),"This is not a 2MA client",
                                 Toast.LENGTH_LONG).show();
                           finishAffinity();
                        }
                        else{
                            Log.i("TAG","error unknown");
                        }
                    }
                }


            @Override
            public void onFailure(Call<CallerResponse> call, Throwable t) {
                progressDialog.dismiss();
                // This is to Handle when a call to server fail.
                if (call.isCanceled()) {
                    Log.e("TAG", "request was cancelled");
                } else {
                    Log.e("TAG", "other larger issue, i.e. no network connection?");
                    Log.e("TAG", t.getMessage());
                    Toast.makeText(getBaseContext(), "Unable to get caller info due to poor network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG","I am here");
    }

    protected void onStartService(String phoneNumber) {
        Intent i = new Intent(this, Doctor2MAIntentService.class);
        i.putExtra("phonenumber",  phoneNumber);
        i.putExtra(Doctor2MAIntentService.ACTION_TO_PERFORM, Doctor2MAIntentService.GET_CALLER);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
