package com.elrabng.testingreceivingapp.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elrabng.testingreceivingapp.BuildConfig;
import com.elrabng.testingreceivingapp.adapter.GsonPreferenceAdapter;
import com.elrabng.testingreceivingapp.model.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppPreference {
    private static SharedPreferences sharedPreferences;
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String CLIENT_NUMBER = "client_number";
    private static final String CASE_ID = "case_id";
    public static final String USER_DATA_RESPONSE = "user_data_response";

    public static SharedPreferences setUpDefault(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sharedPreferences;
    }

    public static void setIsLoggedIn(boolean firstLoggedIn){
        if(sharedPreferences!= null){
            sharedPreferences.edit().putBoolean(IS_LOGGED_IN,firstLoggedIn).apply();
        }
    }

    public static boolean isLogged(){
        return (sharedPreferences!= null) && sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    public static void setUserData(LoginResponse loginResponse) {
        Gson gson = new GsonBuilder().create();
        GsonPreferenceAdapter<LoginResponse> adapter = new GsonPreferenceAdapter<>(gson, LoginResponse.class);
        adapter.set(USER_DATA_RESPONSE, loginResponse, sharedPreferences.edit());
    }

    public static LoginResponse getUserData() {
        Gson gson = new GsonBuilder().create();
        GsonPreferenceAdapter<LoginResponse> adapter = new GsonPreferenceAdapter<>(gson, LoginResponse.class);
        LoginResponse loginResponse = adapter.get(USER_DATA_RESPONSE, sharedPreferences);
        return loginResponse;
    }

    public static void setClientNumber(String clientNumber) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(CLIENT_NUMBER,clientNumber).apply();
        }
    }

    public static String getClientNumber() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(CLIENT_NUMBER, BuildConfig.FLAVOR);
        }
        return BuildConfig.FLAVOR;
    }

    public static void setCaseId(String caseId) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(CASE_ID, caseId).apply();
        }
    }

    public static String getCaseId() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(CASE_ID, BuildConfig.FLAVOR);
        }
        return BuildConfig.FLAVOR;
    }


}
