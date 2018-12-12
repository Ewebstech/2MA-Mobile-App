package com.avardonigltd.mobilemedicalaid.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.KYCRequest;
import com.avardonigltd.mobilemedicalaid.model.KYCResponse;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utilities.NetworkChecker;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.avardonigltd.mobilemedicalaid.utility.RxAppPreference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.avardonigltd.mobilemedicalaid.utilities.UIComponentandDebug.isNull;

public class KYC extends AppCompatActivity {
    @BindView(R.id.hmo_details_question)
    TextView hmoquestion;
    @BindView(R.id.hmo_details_kyc)
    EditText hmoDetailsET;

    @BindView(R.id.email_et_kyc)
    EditText emailET;
    @BindView(R.id.phone_et_kyc)
    EditText phoneET;

    @BindView(R.id.nominate_one_tl_kyc)
    TextInputLayout nominateOneTL;
    @BindView(R.id.nominate_one_et_kyc)
    EditText nominateOneET;
    @BindView(R.id.nominate_one_num_tl_kyc)
    TextInputLayout nominateOneNumTL;
    @BindView(R.id.nominate_one_num_et_kyc)
    EditText nominateOneNumET;

    @BindView(R.id.nominate_two_tl_kyc)
    TextInputLayout nominateTwoTL;
    @BindView(R.id.nominate_two_et_kyc)
    EditText nominateTwoET;
    @BindView(R.id.nominate_two_num_tl_kyc)
    TextInputLayout nominateTwoNumTL;
    @BindView(R.id.nominate_two_num_et_kyc)
    EditText nominateTwoNumET;

    @BindView(R.id.dob_et)
    EditText dobET;

//    @BindView(R.id.contac_tl_kyc)
//    TextInputLayout contactTL;
    @BindView(R.id.contact_et_kyc)
    EditText contactET;
    @BindView(R.id.postal_tl_kyc)
    TextInputLayout postalTL;
    @BindView(R.id.postal_et_kyc)
    EditText postalCodeET;
    @BindView(R.id.city_tl_kyc)
    TextInputLayout cityTL;
    @BindView(R.id.city_et_kyc)
    EditText cityET;
    @BindView(R.id.role_et_kyc)
    EditText roleET;

//    @BindView(R.id.medical_status_tl_kyc)
//    TextInputLayout medicalStatusBox;
    @BindView(R.id.medical_status_et_kyc)
    EditText medicalStatusBoxET;

    @BindView(R.id.treament_spinner)
    Spinner treatmentSP;
    @BindView(R.id.hmo_reg_kyc)
    Spinner hmoSP;
    @BindView(R.id.medical_status_kyc)
    Spinner medStatusSP;
    @BindView(R.id.country_spinner)
    Spinner countrySP;


    @BindView(R.id.checkbox_kyc)
    CheckBox checkBox;

    private String email, phone, nominateName1, nominateName2, nominateNumber1, nominateNumber2,
            city, postalCode, country, role, hmoInfo, medInfo, treatment,contact, hmo, medStatus;
    private static String dob;
    private Unbinder unbinder;
    private API api;
    private Call<KYCResponse> call;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();
        Log.i("TAG","On create method");
//        assignment();
       // bindDetailsFromSharedPreference();
       // bindViewToPreference();
         bindViewToPreference2();


//       // String treatment = isNull(kycDataResponse.getData().getTreatment_status()) ? "" : kycDataResponse.getData().getTreatment_status();
//        String usedata = AppPreference.USER_DATA;
//        Gson gson = new GsonBuilder().create();
//        LoginResponse userDataResponse = gson.fromJson(usedata,LoginResponse.class);
//        email = userDataResponse.getData().getEmail();
//
//        String phone = isNull(AppPreference.getUserDetails().getPhoneNumber()) ? "" : AppPreference.getUserDetails().getPhoneNumber();
//        emailET.setText(email);
//        phoneET.setText(phone);
//        roleET.setText(role);
//
//        Log.i("TAG",email);
//        Log.i("TAG",phone);

//        AppPreference.setIsFirstTimeToKyc(false);
//        if (!AppPreference.isFirstTimeToKYC()) {
//            if (!validated()) {
//                showAlertOfEmptyFields();
//            }
//        }
        setYesorNo();
    }

    @Override
    protected void onPause() {
        if(call!=null){
            call.cancel();
            call = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public boolean validated() {
        assignment();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Invalid Email Address");
            emailET.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone) || !Patterns.PHONE.matcher(phone).matches()) {
            phoneET.setError("Field required");
            phoneET.requestFocus();
            return false;
        }

        return true;
    }

    public void assignment() {
        email = emailET.getText().toString().trim();
        phone = phoneET.getText().toString().trim();
        nominateNumber1 = nominateOneNumET.getText().toString().trim();
        nominateNumber2 = nominateTwoNumET.getText().toString().trim();
        nominateName1 = nominateOneET.getText().toString().trim();
        nominateNumber2 = nominateTwoET.getText().toString().trim();
        city = cityET.getText().toString().trim();
        postalCode = postalCodeET.getText().toString().trim();
        role = roleET.getText().toString().trim();
        hmoInfo = hmoDetailsET.getText().toString().trim();
        medInfo = medicalStatusBoxET.getText().toString().trim();
        contact = contactET.getText().toString().trim();
    }

    public void showAlertOfEmptyFields() {
        Alerter.create(this)
                .setTitle("KYC forms status")
                .setText("one or more fields are yet to be filled")
                .setDuration(5000)
                .setBackgroundColorRes(R.color.appBlue)
                .enableSwipeToDismiss()
                .show();
    }

    private void setYesorNo() {
        List<String> array_gender = new ArrayList<>();
        array_gender.add("No");
        array_gender.add("Yes");

        ArrayAdapter<String> yesornoAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array_gender) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setTextColor(Color.BLACK);
                        return textView;
                    }
                };
        treatmentSP.setAdapter(yesornoAdapter);
        hmoSP.setAdapter(yesornoAdapter);
        medStatusSP.setAdapter(yesornoAdapter);
    }

    @OnItemSelected({R.id.treament_spinner, R.id.hmo_reg_kyc, R.id.medical_status_kyc, R.id.country_spinner})
    public void treatmentoption() {
        treatment = treatmentSP.getSelectedItem().toString().trim();
        hmo = hmoSP.getSelectedItem().toString().trim();
        medStatus = medStatusSP.getSelectedItem().toString().trim();
        country = countrySP.getSelectedItem().toString().trim();
        Log.i("TAG","spinner");
        medicalIssue();
        hmoAnswered();
    }

    public void hmoAnswered() {
        if (hmo.equals("Yes")) {
            hmoDetailsET.setVisibility(View.VISIBLE);
            hmoquestion.setVisibility(View.VISIBLE);

        } else {
            hmoDetailsET.setVisibility(View.GONE);
            hmoquestion.setVisibility(View.GONE);
        }
    }

    public void medicalIssue() {
        if (medStatus.equals("Yes")) {
            medicalStatusBoxET.setVisibility(View.VISIBLE);
        } else {
            medicalStatusBoxET.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.dob_et)
    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, 1); //add one day, so has to set maxDate
            c.add(Calendar.YEAR, -10); // age range of user -10 years ago
            long maxDate = c.getTimeInMillis();
            c.add(Calendar.DAY_OF_YEAR, -1); //substrate it
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(maxDate); //set maximum date that is selectable
            dialog.getDatePicker().getTouchables().get(0).performClick(); //to simulate a click on year, so the year spinner is displayed
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
             dob = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).toString();
            ((EditText) getActivity().findViewById(R.id.dob_et)).setText(dob);
        }
    }


    @OnCheckedChanged(R.id.checkbox_kyc)
    public void nominate() {
        boolean isChecked = checkBox.isChecked();
        if (isChecked) {
            Log.i("TAG", "checked");
            nominateOneTL.setVisibility(View.VISIBLE);
            nominateTwoTL.setVisibility(View.VISIBLE);
            nominateOneNumTL.setVisibility(View.VISIBLE);
            nominateTwoNumTL.setVisibility(View.VISIBLE);
        } else {
            Log.i("TAG", "not checked");
            nominateOneTL.setVisibility(View.GONE);
            nominateTwoTL.setVisibility(View.GONE);
            nominateOneNumTL.setVisibility(View.GONE);
            nominateTwoNumTL.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.backBtnKYC)
    public void goBack() {
        startActivity(new Intent(KYC.this, NavigationalDrawer.class));
    }

    @OnClick(R.id.Update_kyc_btn)
    public void sendDeatils(){
        if (!NetworkChecker.isNetworkAvailable(this)) {
            Toast.makeText(this, "No network connection!",Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("TAG","KYC button clicked");

        KYCRequest update = new KYCRequest();
        String treatment = treatmentSP.getSelectedItem().toString().trim();
        String email = emailET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();
        String nominateName1 = nominateOneET.getText().toString().trim();
        String nominateName2 = nominateTwoET.getText().toString().trim();
        String nominateNumber1 = nominateOneNumET.getText().toString().trim();
        String nominateNumber2 = nominateTwoNumET.getText().toString().trim();
        String hmo = hmoSP.getSelectedItem().toString().trim();
        String hmoInfo = hmoDetailsET.getText().toString().trim();
        String medInfo = medicalStatusBoxET.getText().toString().trim();
        String contact = contactET.getText().toString().trim();
        String city = cityET.getText().toString().trim();
        String postalCode = postalCodeET.getText().toString().trim();
        String country = countrySP.getSelectedItem().toString().trim();
        String medStatus = medStatusSP.getSelectedItem().toString().trim();
        String dob = dobET.getText().toString().trim();
        String role = roleET.getText().toString().trim();

        update.setTreatment_status(treatment);
        update.setEmail(email);
        update.setPhoneNumber(phone);
        update.setEmer_contact_name1(nominateName1);
        update.setEmer_contact_name2(nominateName2);
        update.setEmer_contact_num1(nominateNumber1);
        update.setEmer_contact_num2(nominateNumber2);
        update.setHmoRegStatus(hmo);
        update.setHmoInfo(hmoInfo);
        update.setMedicalConditionDetails(medInfo);
        update.setContactAddress(contact);
        update.setCity(city);
        update.setPostalCode(postalCode);
        update.setCountry(country);
        update.setMedicalCondition(medStatus);
        update.setDob(dob);
        update.setRole(role);


            call = api.editKycMethod(update);
            call.enqueue(new Callback<KYCResponse>() {
                @Override
                public void onResponse(Call<KYCResponse> call, Response<KYCResponse> response) {
                    Log.i("TAG","it is reaching the end point");
                    if (response.isSuccessful()){
                        Log.i("TAG","it is successful");
                    }else{
                        Log.i("TAG","it is not successful");
                    }
                }

                @Override
                public void onFailure(Call<KYCResponse> call, Throwable t) {
                    Log.i("TAG","it is not reaching the end point");
                    if (call.isCanceled()) {

                        Log.e("TAG", "request was cancelled");
                    } else {
                        Log.e("TAG", "other larger issue, i.e. no network connection?");
                        Log.e("TAG", t.getMessage());
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }


//    private void bindViewToPreference(){
//        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(AppPreference.setUpDefault(getBaseContext()));
//        compositeDisposable.addAll(
//                rxSharedPreferences.getString(AppPreference.USER_KYC,"")
//                        .asObservable()
//                        .subscribe(new Consumer<String>() {
//                            @Override
//                            public void accept(String s) throws Exception {
//                                if (isNull(s)) return;
//
//                                Gson gson = new GsonBuilder().create();
//                                KYCResponse kycDataResponse = gson.fromJson(s,KYCResponse.class);
//
//                                String treatment = isNull(kycDataResponse.getData().getKycRequest().getTreatment_status()) ? "" : kycDataResponse.getData().getKycRequest().getTreatment_status();
//                              //  String email = isNull(AppPreference.getUserDetails().getEmail()) ? "" : AppPreference.getUserDetails().getEmail();
//                                //String phone = isNull(AppPreference.getUserDetails().getPhoneNumber()) ? "" : AppPreference.getUserDetails().getPhoneNumber();
//                                String nominateName1 =  isNull(kycDataResponse.getData().getKycRequest().getEmer_contact_name1()) ? "" : kycDataResponse.getData().getKycRequest().getEmer_contact_name1();
//                                String nominateName2 = isNull(kycDataResponse.getData().getKycRequest().getEmer_contact_name2()) ? "" : kycDataResponse.getData().getKycRequest().getEmer_contact_name2();
//                                String nominateNumber1 = isNull(kycDataResponse.getData().getKycRequest().getEmer_contact_num1()) ? "" : kycDataResponse.getData().getKycRequest().getEmer_contact_num1();
//                                String nominateNumber2 = isNull(kycDataResponse.getData().getKycRequest().getEmer_contact_num2()) ? "" : kycDataResponse.getData().getKycRequest().getEmer_contact_num2();
//                                String hmo = isNull(kycDataResponse.getData().getKycRequest().getHmoRegStatus()) ? "" : kycDataResponse.getData().getKycRequest().getHmoRegStatus();
//                                String hmoInfo = isNull(kycDataResponse.getData().getKycRequest().getHmoInfo()) ? "" : kycDataResponse.getData().getKycRequest().getHmoInfo();
//                                String medInfo =isNull(kycDataResponse.getData().getKycRequest().getMedicalConditionDetails()) ? "" : kycDataResponse.getData().getKycRequest().getMedicalConditionDetails();
//                                String contact = isNull(kycDataResponse.getData().getKycRequest().getContactAddress()) ? "" : kycDataResponse.getData().getKycRequest().getContactAddress();
//                                String city = isNull(kycDataResponse.getData().getKycRequest().getCity()) ? "" : kycDataResponse.getData().getKycRequest().getCity();
//                                String postalCode = isNull(kycDataResponse.getData().getKycRequest().getPostalCode()) ? "" : kycDataResponse.getData().getKycRequest().getPostalCode();
//                                String country = isNull(kycDataResponse.getData().getKycRequest().getCountry()) ? "" : kycDataResponse.getData().getKycRequest().getCountry();
//                                String medStatus = isNull(kycDataResponse.getData().getKycRequest().getMedicalCondition()) ? "" : kycDataResponse.getData().getKycRequest().getMedicalCondition();
//                                String dob = isNull(kycDataResponse.getData().getKycRequest().getDob()) ? "" : kycDataResponse.getData().getKycRequest().getDob();
//                                //String role = isNull(AppPreference.getUserDetails().getRole()) ? "" : AppPreference.getUserDetails().getRole();
//
//                                Log.i("TAG",nominateName1);
//                                Log.i("TAG",nominateName2);
//                                Log.i("TAG",nominateNumber1);
//                                Log.i("TAG",nominateNumber2);
//
//                                nominateOneET.setText(nominateName1);
//                                nominateTwoET.setText(nominateName2);
//                                nominateOneNumET.setText(nominateNumber1);
//                                nominateTwoNumET.setText(nominateNumber2);
////                                userTV.setText(user);
////                                clientIdTV.setText(clientId);
//                            }
//                        })
//        );
//    }

    private void bindViewToPreference2(){
        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(AppPreference.setUpDefault(getBaseContext()));
        compositeDisposable.addAll(
                rxSharedPreferences.getString(AppPreference.USER_DATA,"")
                        .asObservable()
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if(TextUtils.isEmpty(s) || TextUtils.equals(s, "null")){
                                    return;
                                }
                                Gson gson = new GsonBuilder().create();
                                LoginResponse userDataResponse = gson.fromJson(s,LoginResponse.class);

                                email = userDataResponse.getData().getEmail();
                                phone= userDataResponse.getData().getPhoneNumber();
                                role = userDataResponse.getData().getRole();

                                Log.i("TAG",email);
                                Log.i("TAG",phone);
                                Log.i("TAG",role);

                                roleET.setText(role);
                                phoneET.setText(phone);
                                emailET.setText(email);

                            }
                        })
        );

    }


}
