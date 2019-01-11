package com.elrabng.testingreceivingapp.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.elrabng.testingreceivingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForgotPassword extends AppCompatActivity {

    @BindView(R.id.emailFPEt)
    TextInputEditText emailET;
    @BindView(R.id.forgotPasswordBtn)
    Button forgotPasswordBtn;

    private String email;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        unbinder = ButterKnife.bind(this);
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ForgotPassword.this,LoginActivity.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Module not available yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void assignment(){
        email = emailET.getText().toString().trim();
    }

    public boolean validated(){
        assignment();
        if (TextUtils.isEmpty(email)){
            emailET.setError("The field is required");
            emailET.requestFocus();
            return  false;
        }
        return  true;
    }

    @OnClick(R.id.backBtnFP)
    public void goBack(){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
