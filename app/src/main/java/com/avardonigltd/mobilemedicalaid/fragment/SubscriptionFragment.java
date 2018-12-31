package com.avardonigltd.mobilemedicalaid.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avardonigltd.mobilemedicalaid.MobileMedicalAidService;
import com.avardonigltd.mobilemedicalaid.R;
import com.avardonigltd.mobilemedicalaid.activities.Login;
import com.avardonigltd.mobilemedicalaid.adapters.PackageAdapter;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.Listeners;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.GetPackageSelecetedResponse;
import com.avardonigltd.mobilemedicalaid.model.GetPackageSelectedRequest;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.Packages;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionFragment extends Fragment implements PackageAdapter.OnClickButtonListenerPB  {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Listeners mListener;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog progressDialog;
    String clientId;

    private Unbinder unbinder;
    private API api;
    private RecyclerView rv;
    List<Packages.DataOfPackage> info;
    PackageAdapter PackagesAvailableAdapter;

    Call<GetPackageSelecetedResponse> call;

    public SubscriptionFragment() {
    }

    public static SubscriptionFragment newInstance(String param1, String param2) {
        SubscriptionFragment fragment = new SubscriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
        unbinder = ButterKnife.bind(this,view);
        // to get AppPreference data individually
        LoginResponse loginResponse = AppPreference.getUserData();

        if (loginResponse!=null){
            loginResponse.getData().getClientId();
        }
        bindViewToPreference();
        Log.i("TAG",clientId);
        return view;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = RetrofitService.initializer();
        PackagesAvailableAdapter = new PackageAdapter(this);
        rv = view.findViewById(R.id.packageRv);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(layoutManager1);
        rv.setAdapter(PackagesAvailableAdapter);
        //String userId = AppPreference.getUserDetailsId();
        getListOfPolicies();
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

    @Override
    public void onClickToView(Packages.DataOfPackage info,String ClientID) {
       // String client_id = loginResponse.getData().getClientId();
        ClientID = clientId;
        String title = info.getTitle();
        selectPacakge(title,clientId);
        //Toast.makeText(getActivity(), "The card is clicked"+ "with"+title+ClientID, Toast.LENGTH_SHORT).show();
       // Toast.makeText(getActivity(), client_id + " "+ title, Toast.LENGTH_SHORT).show();
      //  Log.i("TAG",client_id + " "+ title);
    }

    public void selectPacakge(String Title, String clientID){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        call = api.selcetPackageMethod(new GetPackageSelectedRequest(Title,clientID));
        call.enqueue(new Callback<GetPackageSelecetedResponse>() {
            @Override
            public void onResponse(Call<GetPackageSelecetedResponse> call, Response<GetPackageSelecetedResponse> response) {
                Log.i("TAG","Package Selected");
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (mListener != null) {
                        mListener.refresh(MobileMedicalAidService.ACTION_GET_USER_DATA);
                    }
                    Log.i("TAG","Package Selected succesfully");
                    String message = response.body().getMessage();
                    Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();


                }else if (response.code() == 401){
                    Log.i("TAG","you are trying to select the same package");
                    Gson gson = new GsonBuilder().create();
                    try {
                        Log.i("TAG", "It has error1 400");
                        GetPackageSelecetedResponse error = gson.fromJson(response.errorBody().string(), GetPackageSelecetedResponse.class);
                        Log.i("TAG", "It has error2 400 " + error.getMessage());
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                else if (response.code() == 500) {
                    Toast.makeText(getActivity(), "Error 500", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPackageSelecetedResponse> call, Throwable t) {
                Log.i("TAG","Package Selected failed");
                progressDialog.dismiss();
                if (call.isCanceled()) {
                    Log.e("TAG", "request was cancelled");
                } else {
                    Log.e("TAG", t.getMessage());
                    Toast.makeText(getActivity(), "Kindly Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                Log.i("TAG","It is not reaching the endpoint");
            }
        });

    }

    public void getListOfPolicies(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(R.style.ProgressBarTheme);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        api.getPackages().enqueue(new Callback<Packages>() {
            @Override
            public void onResponse(Call<Packages> call, Response<Packages> response) {
                progressDialog.dismiss();
                Log.i("TAG","It is reaching the endpoint");
                info = new ArrayList<>();
                Packages policies = response.body();
                for (int i = 0; i < policies.getData().size(); i++){
                    String title = policies.getData().get(i).getTitle();
                    String price = policies.getData().get(i).getPrice();
                    if (!TextUtils.isEmpty(title)|| !TextUtils.isEmpty(title)||
                            !TextUtils.isEmpty(title)){
                        switch (title){
                            case "Silver":
                                info.add(new Packages.DataOfPackage(title,price,R.drawable.ic_userdashboard));
                                break;

                            case "Gold":
                                info.add(new Packages.DataOfPackage(title,price,R.drawable.ic_medicalhistory));
                                break;

                            case "Titanium":
                                info.add(new Packages.DataOfPackage(title,price,R.drawable.ic_subscription));
                                break;

                            case "Diamond":
                                info.add(new Packages.DataOfPackage(title,price,R.drawable.ic_support));
                                break;

                            default:
                                info.add(new Packages.DataOfPackage(title,price,R.drawable.ic_subscription));
                                break;

                        }
                    }
                    else {
                        title = "";
                        price = "";
                        info.add(new Packages.DataOfPackage(title,price));
                    }

//                    if (!TextUtils.isEmpty(title)|| !TextUtils.isEmpty(title)||
//                            !TextUtils.isEmpty(title)){
//                        info.add(new Packages.DataOfPackage(title,price));
//                    }
//                    else {
//                        title = "";
//                        price = "";
//                        info.add(new Packages.DataOfPackage(title,price));
//                    }

                }
                PackagesAvailableAdapter.swapItem(info);
            }

            @Override
            public void onFailure(Call<Packages> call, Throwable t) {
                progressDialog.dismiss();
                if (call.isCanceled()) {

                    Log.e("TAG", "request was cancelled");
                } else {
                    Log.e("TAG", "other larger issue, i.e. no network connection?");
                    Log.e("TAG", t.getMessage());
                    Toast.makeText(getActivity(), "Kindly Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                Log.i("TAG","It is not reaching the endpoint");
            }
        });
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
                            }
                        })
        );

    }
}
