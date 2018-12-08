package com.avardonigltd.mobilemedicalaid.interfaces;

import com.avardonigltd.mobilemedicalaid.model.KYCRequest;
import com.avardonigltd.mobilemedicalaid.model.KYCResponse;
import com.avardonigltd.mobilemedicalaid.model.LoginRequest;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.Packages;
import com.avardonigltd.mobilemedicalaid.model.RegistrationRequest;
import com.avardonigltd.mobilemedicalaid.model.RegistrationResponse;

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
    Call<Packages> getPackages(@Body KYCRequest kycRequest);
}
