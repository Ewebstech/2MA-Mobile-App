package com.elrabng.testingreceivingapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.elrabng.testingreceivingapp.R;
import com.elrabng.testingreceivingapp.interfaces.API;
import com.elrabng.testingreceivingapp.model.CompletedCallDetailsRequest;
import com.elrabng.testingreceivingapp.model.CompletedCallDetailsResponse;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.model.TerminateCallRequest;
import com.elrabng.testingreceivingapp.model.TerminateCallResponse;
import com.elrabng.testingreceivingapp.network.RetrofitService;
import com.elrabng.testingreceivingapp.utilities.AppPreference;
import com.elrabng.testingreceivingapp.utilities.NetworkChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CallerIdentified extends AppCompatActivity {
    @BindView(R.id.clientNameTV)
    TextView clientNameTV;
    @BindView(R.id.clientNumberTV)
    TextView clientNumberTv;
    @BindView(R.id.subStatusTV)
    TextView subStatusTV;
    @BindView(R.id.medicalNeedCITV)
    TextView medicalTV;
    @BindView(R.id.severeIndexCITV)
    TextView severeTV;
    @BindView(R.id.treatmentTakenCITV)
    TextView treatmentTakenTV;
    @BindView(R.id.allergiesCITV)
    TextView allergiesTV;
    @BindView(R.id.allergiesInfamilyCITV)
    TextView allergiesFamilyTV;
    @BindView(R.id.otherInfoCITV)
    TextView otherInfoTV;
    Context mContext;
    @BindView(R.id.userImage)
    CircleImageView profileImage;
//    @BindView(R.id.sendBtn)
////    Button sendBtn;

//    @BindView(R.id.clientNameTVInactiveCI)
//    TextView clientNameInactiveTV;
//    @BindView(R.id.clientNumberTVInactiveCI)
//    TextView clientNumberInactiveTv;
//    @BindView(R.id.subStatusTVInactiveCI)
//    TextView subStatusInactiveTV;
    @BindView(R.id.messageInactiveTV)
    TextView messageInactiveTV;
    @BindView(R.id.callerInfoPageCI)
    ConstraintLayout activePage;

    @BindView(R.id. newInative)
    LinearLayout newInative;
//    @BindView(R.id.callerInfoPageInactiveCI)
//    ConstraintLayout inActivePage;

    private Unbinder unbinder;
    private API api;
    private Call<CompletedCallDetailsResponse> call;
    private  Call<TerminateCallResponse> call2;
    ProgressDialog progressDialog;
    private String clientName,subStatus,phoneNumber,caseID,clientPhoneNumber,doctorId,imageUrl;
    private String medicalNeed,severe,treatmentTaken,allergies,allergiesInfamily,otherInfo,messageError,
    differential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_identified);
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();
        Bundle bundle = getIntent().getExtras();
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
            doctorId= loginResponse.getData().getClientId();
        }
        caseID = AppPreference.getCaseId();
       clientPhoneNumber = AppPreference.getClientNumber();
       Log.i("TAG","This is the client number"+clientPhoneNumber);

        clientName = bundle.getString("clientName");
        subStatus = bundle.getString("subStatus");
        phoneNumber = bundle.getString("phoneNumber");
        imageUrl = bundle.getString("imageUrl");


        medicalNeed = bundle.getString("medicalNeed");
        severe = bundle.getString("severe");
        treatmentTaken = bundle.getString("takenTreatment");
        allergies = bundle.getString("allergies");
        allergiesInfamily = bundle.getString("allergyFamily");
        otherInfo = bundle.getString("otherInfo");

        messageError = bundle.getString("messageError");
        differential = bundle.getString("diffrential");

        if (differential != null){
            if(differential.equals("1")){
                bindViews();
                activePage.setVisibility(View.VISIBLE);
                newInative.setVisibility(View.INVISIBLE);
            }
            else if (differential.equals("2")){
                activePage.setVisibility(View.INVISIBLE);
                newInative.setVisibility(View.VISIBLE);
                bindSecondViews();
            }
        }

    }

    @Override
    protected void onPause() {
        if(call!= null){
            call.cancel();
            call = null;
        }
        super.onPause();
    }

    public void bindViews(){
        clientNameTV.setText(clientName);
        clientNumberTv.setText(phoneNumber);
        subStatusTV.setText(subStatus);

        Glide.with(getBaseContext()).load("http://www.mobilemedicalaid.com/api/wtf" + imageUrl)
                .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                .into(profileImage);

        medicalTV.setText(medicalNeed);
        severeTV.setText(severe);
        treatmentTakenTV.setText(treatmentTaken);
        allergiesTV.setText(allergies);
        allergiesFamilyTV.setText(allergiesInfamily);
        otherInfoTV.setText(otherInfo);
    }

    public void bindSecondViews(){
//        clientNameInactiveTV.setText(clientName);
//        clientNumberInactiveTv.setText(phoneNumber);
//        subStatusInactiveTV.setText(subStatus);
        messageInactiveTV.setText(messageError);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.sendBtn)
    public void sendBtnMethod(){
        if (!NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "No internet connection",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
       // sendDetailsToServer(doctorId,clientPhoneNumber,caseID);
        sendDetailsToServer(doctorId,phoneNumber,caseID);
    }

@OnClick(R.id.closeBtn)
    public void closeActivity(){
        startActivity(new Intent(CallerIdentified.this,Dashboard.class));
}
    public void sendDetailsToServer(String doctorId,String clientPhoneNumber, String caseId){
        progressDialog = new ProgressDialog(CallerIdentified.this);
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("Sending....");
        progressDialog.show();
        call = api.sendCallDeatils(new CompletedCallDetailsRequest(doctorId,clientPhoneNumber,caseId));
        call.enqueue(new Callback<CompletedCallDetailsResponse>() {
            @Override
            public void onResponse(Call<CompletedCallDetailsResponse> call, Response<CompletedCallDetailsResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    //TODO Add the doctyor name later
                    Toast.makeText(getApplicationContext(),"Call Details Recorded",
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CallerIdentified.this,Dashboard.class));
                    //finishAffinity();
                }else if (response.code() == 422){
                    Log.i("TAG","error code 422");
                    Toast.makeText(getApplicationContext(),"Sorry,  Call ended nothing to send",
                            Toast.LENGTH_LONG).show();
                    finishAffinity();
                }else{
                    Log.i("TAG","error code unknown yet");
                    Toast.makeText(getApplicationContext(),"Something went wrong please try again.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CompletedCallDetailsResponse> call, Throwable t) {
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

    @OnClick(R.id.terminateBtn)
    public void endCall (){
        //Toast.makeText(getApplicationContext(), "Terminated", Toast.LENGTH_LONG).show();
        if (!NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "No internet connection",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        terminateCallMethod(clientPhoneNumber);
        //rejectCall(getApplicationContext());
        Log.i("TAG","This is the client number"+clientPhoneNumber);

    }

    public void terminateCallMethod(String phonenumber){
        progressDialog = new ProgressDialog(CallerIdentified.this);
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("terminating....");
        progressDialog.show();

        call2 = api.terminateCall(new TerminateCallRequest(phonenumber));
        call2.enqueue(new Callback<TerminateCallResponse>() {
            @Override
            public void onResponse(Call<TerminateCallResponse> call, Response<TerminateCallResponse> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    //TODO Add the doctyor name later
                    Toast.makeText(getApplicationContext(),"User data sent to Admin",
                            Toast.LENGTH_LONG).show();
                  //  startActivity(new Intent(CallerIdentified.this,Dashboard.class));
                     finishAffinity();
                }else if (response.code() == 422){
                    Log.i("TAG","error code 422");
                    Toast.makeText(getApplicationContext(),"Sorry, Call ended nothing to send",
                            Toast.LENGTH_LONG).show();
                    finishAffinity();
                }else{
                    Log.i("TAG","error code unknown yet");
                    Toast.makeText(getApplicationContext(),"Something went wrong please try again.",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TerminateCallResponse> call, Throwable t) {
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

//    private void rejectCall(final Context context)
//    {
//        try{
//            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//            telephony.listen(new PhoneStateListener(){
//                @Override
//                public void onCallStateChanged(int state, String incomingNumber) {
//                    super.onCallStateChanged(state, incomingNumber);
//                    Intent buttonDown = new Intent(Intent.ACTION_MEDIA_BUTTON); buttonDown.putExtra(Intent.EXTRA_KEY_EVENT,
//                            new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK));
//                    context.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");
//                    Log.i("TAG","Call Terminated");
//
//                }
//            },PhoneStateListener.LISTEN_CALL_STATE);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }

}
