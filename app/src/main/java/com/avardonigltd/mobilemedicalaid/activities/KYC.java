package com.avardonigltd.mobilemedicalaid.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.MobileMedicalAidService;
import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.ContentModel;
import com.avardonigltd.mobilemedicalaid.model.KYCRequest;
import com.avardonigltd.mobilemedicalaid.model.KYCResponse;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.kycDataModel;
import com.avardonigltd.mobilemedicalaid.utilities.NetworkChecker;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
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
import butterknife.OnTextChanged;
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

    @BindView(R.id.Update_kyc_btn)
    Button updateKYCBtn;


    private String email, phone, oldNominateName1, oldNominateName2, oldNominateNumber1, oldNominateNumber2,
            oldCity, oldPostalCode, oldCountry, role, oldHmoInfo, oldMedInfo, oldTreatment, oldContact, oldHmo, oldMedStatus, content;
    ProgressDialog progressDialog;
    private static String oldDob;
    private Unbinder unbinder;
    private API api;
    private Call<KYCResponse> call;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String client_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        unbinder = ButterKnife.bind(this);
        api = RetrofitService.initializer();
        Log.i("TAG", "On create method");
       // assignment();
        // bindDetailsFromSharedPreference();
        // bindViewToPreference();
        //checkFields();
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse != null) {
            client_id = loginResponse.getData().getClientId();
        }



//       // String oldTreatment = isNull(kycDataResponse.getData().getTreatment_status()) ? "" : kycDataResponse.getData().getTreatment_status();
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
        bindViewToPreference2();
    }


    @Override
    protected void onPause() {
        if (call != null) {
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
        //assignment();

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

//    public void assignment() {
//        email = emailET.getText().toString().trim();
//        phone = phoneET.getText().toString().trim();
//        oldNominateNumber1 = nominateOneNumET.getText().toString().trim();
//        oldNominateNumber2 = nominateTwoNumET.getText().toString().trim();
//        oldNominateName1 = nominateOneET.getText().toString().trim();
//        oldNominateNumber2 = nominateTwoET.getText().toString().trim();
//        oldCity = cityET.getText().toString().trim();
//        oldPostalCode = postalCodeET.getText().toString().trim();
//        role = roleET.getText().toString().trim();
//        oldHmoInfo = hmoDetailsET.getText().toString().trim();
//        oldMedInfo = medicalStatusBoxET.getText().toString().trim();
//        oldContact = contactET.getText().toString().trim();
//    }

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
        oldTreatment = treatmentSP.getSelectedItem().toString().trim();
        oldHmo = hmoSP.getSelectedItem().toString().trim();
        oldMedStatus = medStatusSP.getSelectedItem().toString().trim();
        oldCountry = countrySP.getSelectedItem().toString().trim();
        Log.i("TAG", "spinner");
        medicalIssue();
        hmoAnswered();
    }

    public void hmoAnswered() {
        if (oldHmo.equals("Yes")) {
            hmoDetailsET.setVisibility(View.VISIBLE);
            hmoquestion.setVisibility(View.VISIBLE);

        } else {
            hmoDetailsET.setVisibility(View.GONE);
            hmoquestion.setVisibility(View.GONE);
        }
    }

    public void medicalIssue() {
        if (oldMedStatus.equals("Yes")) {
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
            oldDob = new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).toString();
            ((EditText) getActivity().findViewById(R.id.dob_et)).setText(oldDob);
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
    public void sendDeatils() {
        Log.i("TAG", "kyc button clicked");
        if (!NetworkChecker.isNetworkAvailable(this)) {
            Toast.makeText(this, "No network connection!", Toast.LENGTH_LONG).show();
            return;
        }

        Log.i("TAG", "KYC button clicked");

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

        progressDialog = new ProgressDialog(KYC.this);
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        call = api.editKycMethod(update);
        call.enqueue(new Callback<KYCResponse>() {
            @Override
            public void onResponse(Call<KYCResponse> call, Response<KYCResponse> response) {
                Log.i("TAG", "it is reaching the end point");
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.i("TAG", "it is about to refresh");

                    Intent i = new Intent(KYC.this, MobileMedicalAidService.class);
                    i.putExtra("client_id", client_id);
                    i.putExtra(MobileMedicalAidService.ACTION_TO_PERFORM, MobileMedicalAidService.ACTION_GET_USER_DATA);
                    startService(i);

                    Toast.makeText(getApplicationContext(),"KYC Updated",Toast.LENGTH_LONG).show();

                    LoginResponse loginResponse = AppPreference.getUserData();
                    if (loginResponse != null) {
                        Log.i("TAG", "it is refreshed");
                        loginResponse.getData().getContent();
                        Gson gson = new GsonBuilder().create();
                        ContentModel df = gson.fromJson(loginResponse.getData().getContent(), ContentModel.class);
                        Log.i("TAG", df.getKyc().getContact_address());
                    }


                    KYCResponse kycBody = response.body();
                    String data = kycBody.getData();
                    Gson gson = new GsonBuilder().create();

                    kycDataModel kycData = gson.fromJson(data, kycDataModel.class);
                    String treatment_status = kycData.getKyc().getTreatment_status();


//                        KycModel kycData2 = gson.fromJson(kyc,KycModel.class);
//                        String treatment_status = kycData2.getTreatment_status();

                    Log.i("TAG", "it is successful");
                } else {
                    Log.i("TAG", "it is not successful");
                }
            }

            @Override
            public void onFailure(Call<KYCResponse> call, Throwable t) {
                Log.i("TAG", "it is not reaching the end point");
                progressDialog.dismiss();
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

    private void bindViewToPreference2() {
        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(AppPreference.setUpDefault(getBaseContext()));
        compositeDisposable.addAll(
                rxSharedPreferences.getString(AppPreference.USER_DATA, "")
                        .asObservable()
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if (TextUtils.isEmpty(s) || TextUtils.equals(s, "null")) {
                                    return;
                                }
                                Gson gson = new GsonBuilder().create();
                                LoginResponse userDataResponse = gson.fromJson(s, LoginResponse.class);

                                email = userDataResponse.getData().getEmail();
                                phone = userDataResponse.getData().getPhoneNumber();
                                role = userDataResponse.getData().getRole();
                                content = userDataResponse.getData().getContent();

                                ContentModel userDataResponse2 = gson.fromJson(content, ContentModel.class);
                                //userDataResponse2.
                                Log.i("TAG", email);
                                Log.i("TAG", phone);
                                Log.i("TAG", role);

                                roleET.setText(role);
                                roleET.setEnabled(false);
                                phoneET.setText(phone);
                                phoneET.setEnabled(false);
                                emailET.setText(email);
                                emailET.setEnabled(false);

//                                if(userDataResponse2.getKyc() == null){
//
//                                }

                              //  oldNominateName1 = isNull(userDataResponse2.getKyc().getEme_contact_name_1()) ? "" : userDataResponse2.getKyc().getEme_contact_name_1();
//                                oldNominateName2 = isNull(userDataResponse2.getKyc().getEme_contact_name_2()) ? "" : userDataResponse2.getKyc().getEme_contact_name_2();
//                                oldNominateNumber1 = isNull(userDataResponse2.getKyc().getEme_contact_num_1()) ? "" : userDataResponse2.getKyc().getEme_contact_num_1();
//                                oldNominateNumber2 = isNull(userDataResponse2.getKyc().getEme_contact_num_2()) ? "" : userDataResponse2.getKyc().getEme_contact_num_2();
//                                oldHmo = isNull(userDataResponse2.getKyc().getHmo_status()) ? "" : userDataResponse2.getKyc().getHmo_status();
//                                oldHmoInfo = isNull(userDataResponse2.getKyc().getHmo_info()) ? "" : userDataResponse2.getKyc().getHmo_info();
//                                oldMedInfo = isNull(userDataResponse2.getKyc().getMedical_condition_details()) ? "" : userDataResponse2.getKyc().getMedical_condition_details();
//                                oldContact = isNull(userDataResponse2.getKyc().getContact_address()) ? "" : userDataResponse2.getKyc().getContact_address();
//                                oldCity = isNull(userDataResponse2.getKyc().getCity()) ? "" : userDataResponse2.getKyc().getCity();
//                                oldPostalCode = isNull(userDataResponse2.getKyc().getPostal_code()) ? "" : userDataResponse2.getKyc().getPostal_code();
//                                oldCountry = isNull(userDataResponse2.getKyc().getCountry()) ? "" : userDataResponse2.getKyc().getCountry();
//                                 oldMedStatus = isNull(userDataResponse2.getKyc().getMedical_condition()) ? "" : userDataResponse2.getKyc().getMedical_condition();
//                                oldDob = isNull(userDataResponse2.getKyc().getDob()) ? "" : userDataResponse2.getKyc().getDob();
//                                oldTreatment = isNull(userDataResponse2.getKyc().getTreatment_status()) ? "" : userDataResponse2.getKyc().getTreatment_status();
//                                Log.i("TAG", "This is the bind view sector" + oldContact);
//
//                                nominateOneET.setText(oldNominateName1);
//                                nominateOneNumET.setText(oldNominateNumber1);
//                                nominateTwoET.setText(oldNominateName2);
//                                nominateTwoNumET.setText(oldNominateNumber2);
//                                hmoDetailsET.setText(oldHmoInfo);
//                                medicalStatusBoxET.setText(oldMedInfo);
//                                contactET.setText(oldContact);
//                                cityET.setText(oldCity);
//                                postalCodeET.setText(oldPostalCode);
//                                countrySP.setSelection(((ArrayAdapter<String>) countrySP.getAdapter()).getPosition(oldCountry));
//                               //treatmentSP.setSelection(yes.getPosition(oldTreatment));
//                                if (!TextUtils.isEmpty(oldTreatment)) {
//                                    ArrayAdapter<String> array_spinner = (ArrayAdapter<String>) treatmentSP.getAdapter();
//                                    treatmentSP.setSelection(array_spinner.getPosition(oldTreatment));
//                                }
//                                hmoSP.setSelection(((ArrayAdapter<String>) hmoSP.getAdapter()).getPosition(oldHmo));
//                                medStatusSP.setSelection(((ArrayAdapter<String>) medStatusSP.getAdapter()).getPosition(oldMedStatus));
//                                medicalStatusBoxET.setText(oldMedInfo);
//                                dobET.setText(oldDob);

                            }
                        })
        );
    }

}
