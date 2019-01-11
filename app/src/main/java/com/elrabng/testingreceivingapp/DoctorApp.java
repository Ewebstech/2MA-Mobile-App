package com.elrabng.testingreceivingapp;

import android.app.Application;

import com.elrabng.testingreceivingapp.utilities.AppPreference;

public class DoctorApp extends Application {
        private static DoctorApp instance;

        @Override
        public void onCreate() {
            super.onCreate();
            AppPreference.setUpDefault(this);
            instance = this;
        }

        public static DoctorApp getInstance(){
            return instance;
        }
}
