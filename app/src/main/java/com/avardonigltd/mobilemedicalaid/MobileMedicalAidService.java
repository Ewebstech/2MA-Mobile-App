package com.avardonigltd.mobilemedicalaid;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import com.avardonigltd.mobilemedicalaid.interfaces.API;
import com.avardonigltd.mobilemedicalaid.interfaces.RetrofitService;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.RefreshDataRequest;
import com.avardonigltd.mobilemedicalaid.model.RefreshdataResponse;
import com.avardonigltd.mobilemedicalaid.utility.AppPreference;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;

public class MobileMedicalAidService extends IntentService {
    public static final String ACTION_GET_USER_DATA = "GET_USER_DATA";
    public static final String ACTION_TO_PERFORM = "com.avardonigltd.mobilemedicalaid.action";

    public MobileMedicalAidService() {
        super("test-intent-service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String clientId= intent.getStringExtra("client_id");     // Extract additional values from the bundle
        String ACTION = intent.getStringExtra(ACTION_TO_PERFORM);

        switch (ACTION) {
            case ACTION_GET_USER_DATA:
                getUserData(clientId);
                break;

            default:
                break;
        }
    }

    private void getUserData(String clientId) {
        API api = RetrofitService.initializer();
        DisposableSingleObserver<LoginResponse> userData = getUserDataResponse();
        api.getProfile(new RefreshDataRequest(clientId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(userData);
    }

    private DisposableSingleObserver<LoginResponse> getUserDataResponse() {
        return new DisposableSingleObserver<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse value) {
                AppPreference.setUserData(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "UserDataResponse Failed");
            }
        };
    }
}
