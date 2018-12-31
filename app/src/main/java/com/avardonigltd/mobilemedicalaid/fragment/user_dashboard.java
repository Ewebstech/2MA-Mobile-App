package com.avardonigltd.mobilemedicalaid.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.model.ContentModel;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class user_dashboard extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String mParam1;
    private String mParam2;
    private Listeners mListener;


    @BindView(R.id.profile_img)
    CircleImageView profileImage;
    @BindView(R.id.full_name)TextView fullNameTV;
    @BindView(R.id.user_type) TextView userTV;
    @BindView(R.id.client_id) TextView clientIdTV;
    @BindView(R.id.status_dashboard) TextView statusTv;
    @BindView(R.id.calls_Tv) TextView callsTv;
    @BindView(R.id.packageTv) TextView packageTv;
    @BindView(R.id. message_for_status) TextView messageForStatusTV;
    String formattedDate;
    @BindView(R.id.day) TextView dateTV;
    @BindView(R.id.month) TextView monthTV;
    private String customerName, user, clientId, firstName, lastName,content,status,calls,
            packageText, messageForStatus,imageUrl;
    private Unbinder unbinder;

    public user_dashboard() {
    }

    public static user_dashboard newInstance(String param1, String param2) {
        user_dashboard fragment = new user_dashboard();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Date c = Calendar.getInstance().getTime();
        Calendar e = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        formattedDate = df.format(c);
        dateTV.setText(formattedDate);
        String actualMonth = String.format(Locale.US,"%tB",e);
        monthTV.setText(actualMonth);
        bindViewToPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners ) {
            mListener = (Listeners ) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    private void bindViewToPreference(){
        RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(AppPreference.setUpDefault(getContext()));
        compositeDisposable.addAll(
                rxSharedPreferences.getString(AppPreference.USER_DATA,"")
                        .asObservable()
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if(TextUtils.isEmpty(s) || TextUtils.equals(s, "null")){
                                    return;
                                }
                                // to convert String to object
                                Gson gson = new GsonBuilder().create();
                                LoginResponse userDataResponse = gson.fromJson(s,LoginResponse.class);

                                clientId = userDataResponse.getData().getClientId();
                                user = userDataResponse.getData().getRole();
                                firstName = userDataResponse.getData().getFirstname();
                                content =  userDataResponse.getData().getContent();
                                lastName = userDataResponse.getData().getLastname();
                                imageUrl = userDataResponse.getData().getAvatar();
                                Log.i("TAG",clientId);
                                Log.i("TAG",user);
                                Log.i("TAG",firstName);

                                // to convert string to object also
                                ContentModel userDataResponse2 = gson.fromJson(content,ContentModel.class);
                                calls = userDataResponse2.getCalls();
                                status = userDataResponse2.getStatus();
                                packageText = userDataResponse2.getPackages();
                                customerName = firstName+" "+lastName;
                                fullNameTV.setText(customerName);
                                userTV.setText(user);
                                clientIdTV.setText(clientId);
                                statusTv.setText(status);
                                callsTv.setText(calls);

                                if (status.equals("Active")){
                                    packageTv.setText(packageText);
                                    statusTv.setTextColor(getResources().getColor(R.color.appyellow));
                                    messageForStatus = "Activated to make calls to Doctors";
                                    messageForStatusTV.setText(messageForStatus);
                                }else {
                                    packageTv.setText(packageText);
                                    statusTv.setTextColor(getResources().getColor(R.color.appRed));
                                    messageForStatus = "Subscription Exhausted";
                                    messageForStatusTV.setText(messageForStatus);
                                }

                                Glide.with(getActivity()).load("http://www.mobilemedicalaid.com/api/wtf" + imageUrl)
                                        .apply(new RequestOptions().error(R.drawable.boy).placeholder(R.drawable.boy).fitCenter())
                                        .into(profileImage);

                            }
                        })
        );

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    @OnClick(R.id.kyc_fb)
//    public void goToKyc(){
//        startActivity(new Intent(getActivity(), KYC.class));
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
        unbinder.unbind();
    }

    @OnClick(R.id.callBtnDashboard)
    public void makeCall(){
        int call = Integer.parseInt(calls);
        if (call == 0){
//            clickedOn(new QuestionToCallFragment());
//            AppPreference.setIsFirstTimeCalled(true);
            Toast.makeText(getActivity(),"You are not eligible to make this call",Toast.LENGTH_LONG).show();
        }
        else {
            AppPreference.setIsFirstTimeCalled(true);
            clickedOn(new QuestionToCallFragment());
        }
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
