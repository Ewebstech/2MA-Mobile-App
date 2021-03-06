package com.avardonigltd.mobilemedicalaid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.MobileMedicalAidService;
import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.fragment.SettingsFragment;
import com.avardonigltd.mobilemedicalaid.fragment.SubscriptionFragment;
import com.avardonigltd.mobilemedicalaid.fragment.Terms;
import com.avardonigltd.mobilemedicalaid.fragment.UpdateUserProfileFragment;
import com.avardonigltd.mobilemedicalaid.fragment.user_dashboard;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.ContentModel;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.MakePaymentRequest;
import com.avardonigltd.mobilemedicalaid.model.MakePaymentResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationalDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Listeners{
    private SharedPreferences sharedPreferences;
    private NavigationView nvDrawer;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private Fragment selectedFragment;
    String fullname, usertype;
    private API api;

    Call<MakePaymentResponse> call;

//    @BindView(R.id.full_name_nav)
//    TextView fullnameTv;
//    @BindView(R.id.user_type_nav)
//    TextView userTypeTv;
      private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private Unbinder unbinder;
    TextView fullnameTv;
    TextView userTypeTv;
    private String packageString,clientID,amount,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        api = RetrofitService.initializer();

        LoginResponse loginResponse = AppPreference.getUserData();
        String content = loginResponse.getData().getContent();
        Gson gson = new GsonBuilder().create();
        ContentModel contentModel = gson.fromJson(content,ContentModel.class);
        amount = contentModel.getAmount();
        packageString = contentModel.getPackages();
        clientID = contentModel.getClient_id();
        email = contentModel.getEmail();

        Log.i("TAG",packageString + amount + clientID + email);
       // View headerView = navigationView.getHeaderView(0);
       //  fullnameTv = (TextView) headerView.findViewById(R.id.full_name_nav);
         //userTypeTv = (TextView) headerView.findViewById(R.id.user_type_nav);
       // unbinder = ButterKnife.bind(this);


        mDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        selectedFragment = new user_dashboard();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_drawer_frame_layout,selectedFragment)
                .commit();

        nvDrawer = findViewById(R.id.nvView);
        View headerView = nvDrawer.getHeaderView(0);
          fullnameTv = (TextView) headerView.findViewById(R.id.full_name_nav);
        userTypeTv = (TextView) headerView.findViewById(R.id.user_type_nav);
        nvDrawer.setNavigationItemSelectedListener(this);
        bindViewToPreference();
    }

    public void fragmentSelected(@NonNull final Fragment fragment){
        final FragmentManager manager =  getSupportFragmentManager();
        manager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_drawer_frame_layout,fragment)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        Toolbar toolbar = findViewById(R.id.toolbar);
        switch(item.getItemId()){
            case R.id.dashboard:
                selectedFragment = new user_dashboard();
                toolbar.setTitle("DASHBOARD");
                break;

            case R.id.kyc:
               // intent = new Intent(this, KYC.class);
               // startActivity(intent);
                Toast.makeText(getBaseContext(),"Work in progress",Toast.LENGTH_LONG).show();
                break;

            case R.id.terms:
                selectedFragment = new Terms();
                toolbar.setTitle("TERMS & Conditions");
                break;

            case R.id.subscription:
                selectedFragment = new SubscriptionFragment();
                toolbar.setTitle("SUBSCRIPTION");
                break;

            case R.id.update_profile:
                selectedFragment = new UpdateUserProfileFragment();
                toolbar.setTitle("PROFILE");
                break;

            case R.id.add_debit:
                Toast.makeText(getBaseContext(),"Not Available at the moment",Toast.LENGTH_LONG).show();
                break;

            case R.id.transaction:
                Toast.makeText(getBaseContext(),"Work in progress",Toast.LENGTH_LONG).show();
                break;

            case R.id.schedule_appointments:
                Toast.makeText(getBaseContext(),"Work in progress",Toast.LENGTH_LONG).show();
                break;


            case R.id.payment:
                makePaymentToPayStack(packageString,clientID,amount,email);
                Toast.makeText(getBaseContext(),"Work in progress",Toast.LENGTH_LONG).show();
                break;


            case R.id.settings:
                selectedFragment = new SettingsFragment();
                toolbar.setTitle("SETTINGS");
                break;


            case R.id.logout:
                intent = new Intent(this, Login.class);
                AppPreference.setIsLoggedIn(false);
                startActivity(intent);
                break;
        }


        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.setDrawerElevation(0);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //Set your new fragment here
                if (selectedFragment != null) {
                    fragmentSelected(selectedFragment);
                }
            }
        });
        return true;

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == 234){
            Log.i("TAG","it is successful");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
}

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    protected void onStartService(String action) {

      //  String token = TokenConstructApi.constructToken(AppPreference.getUserToken());
        LoginResponse loginResponse = AppPreference.getUserData();
        if (loginResponse!=null){
            String clientId = loginResponse.getData().getClientId();
            Intent i = new Intent(this, MobileMedicalAidService.class);
            i.putExtra("client_id", clientId);
            i.putExtra(MobileMedicalAidService.ACTION_TO_PERFORM, action);
            //i.putExtra("receiver", receiverForTest);
            startService(i);
        }
    }

    @Override
    public void refresh(String action) {
        onStartService(action);
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

                                fullname = userDataResponse.getData().getFirstname();
                                usertype = userDataResponse.getData().getRole();

                                Log.i("TAG",fullname);
                                Log.i("TAG",usertype);


                                fullnameTv.setText(fullname);
                                userTypeTv.setText(usertype);
                            }
                        })
        );

    }


    public void makePaymentToPayStack(String packageStr, String clientId,String amount, String email){
        call = api.makePaymentMethod(new MakePaymentRequest(packageStr,clientId,amount,email,new MakePaymentRequest.MetaData(packageStr,clientId)));
        call.enqueue(new Callback<MakePaymentResponse>() {
            @Override
            public void onResponse(Call<MakePaymentResponse> call, Response<MakePaymentResponse> response) {
                String url = response.body().getData().getUrl();
                Log.i("TAG","It enters onResponse block");
                if (response.isSuccessful()){
                    Log.i("TAG","It is successful");
                   Intent intent = new Intent(NavigationalDrawer.this,WebActivity.class);
                    //intent.putExtra(WebActivity.EXTRA_URL,"https://checkout.paystack.com/9u8g63h6xambcod");
                    //intent.putExtra(WebActivity.EXTRA_URL,"http://mobilemedicalaid.com/login0");
                    intent.putExtra(WebActivity.EXTRA_URL,url);
                    startActivityForResult(intent,234);
                }else{
                    Log.i("TAG","It enters error block");
                }
            }

            @Override
            public void onFailure(Call<MakePaymentResponse> call, Throwable t) {
                Log.i("TAG","It enters on failure block");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppPreference.setIsLoggedIn(true);
    }

    //
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }
}
