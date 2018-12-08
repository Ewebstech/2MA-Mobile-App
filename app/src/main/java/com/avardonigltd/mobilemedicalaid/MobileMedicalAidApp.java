package com.avardonigltd.mobilemedicalaid;

import android.app.Application;

import com.avardonigltd.mobilemedicalaid.utility.AppPreference;

public class MobileMedicalAidApp extends Application {
    private static MobileMedicalAidApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppPreference.setUpDefault(this);
        instance = this;

//        Fabric.with(this, new Crashlytics());
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        //TODO remove for debugging only
//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)  // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric);

//        enableSessionManager(this);
    }
}
