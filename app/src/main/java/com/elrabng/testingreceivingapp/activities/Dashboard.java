package com.elrabng.testingreceivingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.elrabng.testingreceivingapp.R;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.utilities.AppPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Dashboard extends AppCompatActivity {
    private String doctorId,firstName,lastName,phoneNumber,role,fullName;

    @BindView(R.id.full_name_dashboard)
    TextView fullNameTv;
    @BindView(R.id.roleTv)
    TextView roleTv;
    @BindView(R.id.doctorIdTV)
    TextView doctorIdTv;

    String formattedDate;
    @BindView(R.id.day) TextView dateTV;
    @BindView(R.id.month) TextView monthTV;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        unbinder = ButterKnife.bind(this);

        Date c = Calendar.getInstance().getTime();
        Calendar e = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        formattedDate = df.format(c);
        dateTV.setText(formattedDate);
        String actualMonth = String.format(Locale.US,"%tB",e);
        monthTV.setText(actualMonth);

        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
            doctorId= loginResponse.getData().getClientId();
            Log.i("TAG",doctorId);
            firstName= loginResponse.getData().getFirstname();
            lastName= loginResponse.getData().getLastname();
            phoneNumber= loginResponse.getData().getPhoneNumber();
            role= loginResponse.getData().getRole();
            fullName = lastName+" "+firstName;
        }
        bindView();
    }

    public void bindView(){
        fullNameTv.setText(fullName);
        roleTv.setText(role);
        doctorIdTv.setText(doctorId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        AppPreference.setIsLoggedIn(true);
    }

    @OnClick(R.id.logoutDB)
    public void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        AppPreference.setIsLoggedIn(false);
        startActivity(intent);
    }

}
