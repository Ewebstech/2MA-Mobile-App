package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class WelcomeMessage extends AppCompatActivity {

    @BindView(R.id.firstname_tv_welcome)TextView firstNameTV;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String firstName;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_message);
        unbinder = ButterKnife.bind(this);
        bindViewToPreference();

        Button kycBtn = findViewById(R.id.toKYC);
        kycBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeMessage.this,NavigationalDrawer.class));
            }
        });
    }

    private void bindViewToPreference(){
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

                                firstName = userDataResponse.getData().getFirstname().toUpperCase();
                                Log.i("TAG",firstName);
                                firstNameTV.setText(firstName);
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
        unbinder.unbind();
    }
}
