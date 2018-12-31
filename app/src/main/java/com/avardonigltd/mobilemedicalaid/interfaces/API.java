package com.avardonigltd.mobilemedicalaid.interfaces;

import com.avardonigltd.mobilemedicalaid.model.CallRequest;
import com.avardonigltd.mobilemedicalaid.model.CallResponseInit;
import com.avardonigltd.mobilemedicalaid.model.GetPackageSelecetedResponse;
import com.avardonigltd.mobilemedicalaid.model.GetPackageSelectedRequest;
import com.avardonigltd.mobilemedicalaid.model.KYCRequest;
import com.avardonigltd.mobilemedicalaid.model.KYCResponse;
import com.avardonigltd.mobilemedicalaid.model.LoginRequest;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.MakePaymentRequest;
import com.avardonigltd.mobilemedicalaid.model.MakePaymentResponse;
import com.avardonigltd.mobilemedicalaid.model.Packages;
import com.avardonigltd.mobilemedicalaid.model.RefreshDataRequest;
import com.avardonigltd.mobilemedicalaid.model.RegistrationRequest;
import com.avardonigltd.mobilemedicalaid.model.RegistrationResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    String BASE_URL = "http://www.mobilemedicalaid.com/api/wtf/";

    @POST("register")
    Call<RegistrationResponse> registrationMethod(@Body RegistrationRequest registrationRequest);

    @POST("login")
    Call<LoginResponse> loginMethod(@Body LoginRequest loginRequest);

    @POST("editkyc")
    Call<KYCResponse> editKycMethod(@Body KYCRequest kycRequest);

    @GET("getpackages")
    Call<Packages> getPackages();

    @POST("selectpackage")
    Call<GetPackageSelecetedResponse> selcetPackageMethod(@Body GetPackageSelectedRequest getPackageSelectedRequest);

    @POST("getfreshdata")
    Single<LoginResponse> getProfile(@Body RefreshDataRequest refreshDataRequest);

    @POST("make-payment")
    Call<MakePaymentResponse> makePaymentMethod(@Body MakePaymentRequest makePaymentRequest);

    @POST("call_init")
    Call<CallResponseInit> makecallMethod(@Body CallRequest callRequest);

}
