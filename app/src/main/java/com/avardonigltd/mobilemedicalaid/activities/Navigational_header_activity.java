package com.avardonigltd.mobilemedicalaid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class Navigational_header_activity extends AppCompatActivity {

    @BindView(R.id.full_name_nav)
    TextView fullnameTv;
    @BindView(R.id.user_type_nav)
    TextView userTypeTv;
    @BindView(R.id.profile_img)
    CircleImageView profileImage;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;
    String fullname, usertype, imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigational_header);
         unbinder = ButterKnife.bind(this);
         bindViewToPreference();
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

                                fullname = userDataResponse.getData().getLastname();
                                usertype = userDataResponse.getData().getRole();
                                imageUrl = userDataResponse.getData().getAvatar();

                                Log.i("TAG",fullname);
                                Log.i("TAG",usertype);

                                fullnameTv.setText(fullname);
                                userTypeTv.setText(fullname);

                                Glide.with(getBaseContext()).load("http://www.mobilemedicalaid.com/api/wtf" + imageUrl)
                                        .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                                        .into(profileImage);
                            }
                        })
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
