package com.avardonigltd.mobilemedicalaid.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.activities.ForgotPassword;
import com.avardonigltd.mobilemedicalaid.activities.KYC;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class user_dashboard extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String mParam1;
    private String mParam2;
    private Listeners mListener;

    @BindView(R.id.full_name)TextView fullNameTV;
    @BindView(R.id.user_type) TextView userTV;
    @BindView(R.id.client_id) TextView clientIdTV;

    private String customerName, user, clientId, firstName, lastName;
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
                                Gson gson = new GsonBuilder().create();
                                LoginResponse userDataResponse = gson.fromJson(s,LoginResponse.class);

                                clientId = userDataResponse.getData().getClientId();
                                user = userDataResponse.getData().getRole();
                                firstName = userDataResponse.getData().getFirstname();
                                lastName = userDataResponse.getData().getLastname();
                                Log.i("TAG",clientId);
                                Log.i("TAG",user);
                                Log.i("TAG",firstName);

                                customerName = firstName+" "+lastName;
                                fullNameTV.setText(customerName);
                                userTV.setText(user);
                                clientIdTV.setText(clientId);
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
}
