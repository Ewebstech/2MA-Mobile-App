package com.avardonigltd.mobilemedicalaid.fragment;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.activities.Login;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.CallRequest;
import com.avardonigltd.mobilemedicalaid.model.CallResponseInit;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionToCallFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CALL = 1;
    private int callIndeifer = 0;
    String phoneNumberStr,clientPhone,clientId;
    String medicalNeedStr,severeStr,takenTreatmentStr,allergiesStr,allergyFamily,otherInfoStr;
    private Call<CallResponseInit> call;
    private API api;
    private Unbinder unbinder;
    ProgressDialog progressDialog;

    @BindView(R.id.call_btn)
    ImageView callBtn;
    @BindView(R.id.submit_btn)
    Button submitData;

    @BindView(R.id.medical_need_spinner)
    Spinner medicalNeedSP;
    @BindView(R.id.severe_spinner)
    Spinner severeSP;
    @BindView(R.id.medical_condition_in_family_spinner)
    Spinner allergiesFamilySP;

    @BindView(R.id.treatment_taken)
    EditText takenTreatment;
    @BindView(R.id.any_allergies)
    EditText allergies;
    @BindView(R.id.other_info)
    EditText otherInfo;


    private String mParam1;
    private String mParam2;

    private Listeners mListener;

    public QuestionToCallFragment() {
    }

    public static QuestionToCallFragment newInstance(String param1, String param2) {
        QuestionToCallFragment fragment = new QuestionToCallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_to_call, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = RetrofitService.initializer();
        phoneNumberStr = "08149906511";
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
           clientId = loginResponse.getData().getClientId();
           clientPhone = loginResponse.getData().getPhoneNumber();
        }


    }

    public void assignment(){
        takenTreatmentStr = takenTreatment.getText().toString().trim();
        allergiesStr = allergies.getText().toString().trim();
        otherInfoStr = otherInfo.getText().toString().trim();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners) {
            mListener = (Listeners) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void makeCall(){
        if (phoneNumberStr.trim().length()>0){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{

                switch(callIndeifer){
                    case 0:
                        String dial = "tel:" + phoneNumberStr;
                        callIndeifer++;
                        AppPreference.setIsFirstTimeCalled(false);
                        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
                        break;

                    case 1:
                        Toast.makeText(getActivity(),"You have already made the call once",Toast.LENGTH_LONG).show();
                        AppPreference.setIsFirstTimeCalled(false);
                        clickedOn(new user_dashboard());
                        break;

                        default:
                            AppPreference.setIsFirstTimeCalled(false);
                            clickedOn(new user_dashboard());
                            break;

                }
//                if (AppPreference.isFirstTimeCalled()){
//                    String dial = "tel:" + phoneNumberStr;
//                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
//                    AppPreference.setIsFirstTimeCalled(false);
//                }
//
//                if(!AppPreference.isFirstTimeCalled()){
//                    Toast.makeText(getActivity(),"You have already made the call once",Toast.LENGTH_LONG).show();
//                }
            }

        }else{
            Toast.makeText(getContext(),"No phone number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
        }
        else{
            Toast.makeText(getContext(),"No phone number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        if (call != null) {
            call.cancel();
            call = null;
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.call_btn)
        public void callDoctor(){
            makeCall();
        }

    @OnClick(R.id.submit_btn)
    public void sendMethod(){
        assignment();

       sendDataToCall(clientId,clientPhone,medicalNeedStr,severeStr,allergyFamily,takenTreatmentStr,
               allergiesStr,otherInfoStr);
    }

        public void sendDataToCall(String Id, String phonenumber,String medicalNeed,String severe,
                                   String allergyFamily,String takenTreatment, String allergies,String otherInfor){

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setProgressStyle(R.style.ProgressBarTheme);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
            call = api.makecallMethod(new CallRequest(Id,phonenumber,medicalNeed,severe,
                    allergyFamily,takenTreatment,allergies,otherInfor));
            call.enqueue(new Callback<CallResponseInit>() {
                @Override
                public void onResponse(Call<CallResponseInit> call, Response<CallResponseInit> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()){
                        Log.i("TAG","It is Successful");
                        CallResponseInit dataToUse = response.body();
                        Toast.makeText(getActivity(), "You can go ahead with your call", Toast.LENGTH_SHORT).show();
                        callBtn.setVisibility(View.VISIBLE);
                        submitData.setVisibility(View.GONE);
                    }
                    else if (response.code() == 400){
                            Gson gson = new GsonBuilder().create();
                            try{
                                CallResponseInit error = gson.fromJson( response.errorBody().string(),CallResponseInit.class);
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                    }
                    else{
                        Log.i("TAG","Unknown Error");
                    }
                }

                @Override
                public void onFailure(Call<CallResponseInit> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.i("TAG","It is entering on failure");
                    if (call.isCanceled()) {
                        Log.e("TAG", "request was cancelled");
                    } else {
                        Log.e("TAG", "other larger issue, i.e. no network connection?");
                        Log.e("TAG", t.getMessage());
                        Toast.makeText(getActivity(), "Server issue", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    @OnItemSelected({R.id.medical_need_spinner, R.id.severe_spinner, R.id.medical_condition_in_family_spinner})
    public void spinnerMethod() {
        medicalNeedStr = medicalNeedSP.getSelectedItem().toString().trim();
        severeStr = severeSP.getSelectedItem().toString().trim();
        allergyFamily = allergiesFamilySP.getSelectedItem().toString().trim();
    }

    private void clickedOn(@NonNull Fragment fragment) {
        final String tag = fragment.getClass().toString();
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_drawer_frame_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}
