package com.elrabng.testingreceivingapp.interfaces;

import com.elrabng.testingreceivingapp.model.CallerRequest;
import com.elrabng.testingreceivingapp.model.CallerResponse;
import com.elrabng.testingreceivingapp.model.CompletedCallDetailsRequest;
import com.elrabng.testingreceivingapp.model.CompletedCallDetailsResponse;
import com.elrabng.testingreceivingapp.model.LoginRequest;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.elrabng.testingreceivingapp.model.TerminateCallRequest;
import com.elrabng.testingreceivingapp.model.TerminateCallResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    String BASE_URL = "http://www.mobilemedicalaid.com/api/wtf/";

    @POST("caller_info")
    Call<CallerResponse> getCallerInfoMethod(@Body CallerRequest callerRequest);

    @POST("login")
    Call<LoginResponse> loginMethod(@Body LoginRequest loginRequest);

    @POST("caller_info")
    Single<CallerResponse> getCallerInfoBgMethod(@Body CallerRequest callerRequest);

    @POST("call_completed")
    Call<CompletedCallDetailsResponse> sendCallDeatils(@Body CompletedCallDetailsRequest callDetailsRequest);

    @POST("terminate_call")
    Call<TerminateCallResponse> terminateCall(@Body TerminateCallRequest terminateCallRequest);

}
