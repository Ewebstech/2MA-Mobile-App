package com.avardonigltd.mobilemedicalaid.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.airbnb.lottie.L;
import com.avardonigltd.mobilemedicalaid.BuildConfig;
import com.avardonigltd.mobilemedicalaid.adapters.GsonPreferenceAdapter;
import com.avardonigltd.mobilemedicalaid.model.GetPackageSelecetedResponse;
import com.avardonigltd.mobilemedicalaid.model.LoginResponse;
import com.avardonigltd.mobilemedicalaid.model.RegistrationResponse.Datum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppPreference {
    private static SharedPreferences sharedPreferences;
    public static final String IS_FIRST_TIME_LAUNCHED = "is_first_time_launched";
    public static final String IS_FIRST_TIME_TO_KYC = "is_first_time_to_kyc";
    public static final String IS_LOGGED_IN= "is_logged_in";
    public static final String TOKEN = "token";
    public static final String USER_DETAILS_EMAIL = "user_details_email";
    public static final String USER_DETAILS_PHONE = "user_details_phone";
    public static final String USER_DETAILS_LASTNAME = "user_details_lastname";
    public static final String USER_DETAILS_FIRSTNAME = "user_details_firstname";
    public static final String USER_DETAILS_ROLE = "user_details_role";
    public static final String USER_DETAILS_PASSWORD = "user_details_password";
    public static final String USER_DETAILS_CLIENT_ID = "user_details_client_id";
    public static final String USER_DATA= "USER_DATA";
    public static final String USER_KYC= "USER_KYC";
 //   public static final String USER_DETAILS_TOKEN = "user_details_token";



    public static SharedPreferences setUpDefault(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            // context.getSharedPreferences(Constants.APPPREFERENCE, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void setIsFirstTimeLaunched(boolean value){
        if (sharedPreferences!=null){
            sharedPreferences.edit().putBoolean(IS_FIRST_TIME_LAUNCHED,value).apply();
        }
    }

    public static boolean isFirstTimeLaunched(){
        return (sharedPreferences!=null) && sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCHED,true);
    }

    public void setIsLoggedIn(boolean value){
        if (sharedPreferences!=null){
            sharedPreferences.edit().putBoolean(IS_LOGGED_IN,value).apply();
        }
    }

    public static boolean isLogged(){
        return (sharedPreferences!=null) && sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }
    public static void setIsFirstTimeToKyc(boolean status){
        if (sharedPreferences!=null){
            sharedPreferences.edit().putBoolean(IS_FIRST_TIME_TO_KYC,status).apply();
        }
    }

    public static String getToken(){
        if (sharedPreferences!=null){
            return sharedPreferences.getString(TOKEN,BuildConfig.FLAVOR);
        }
        return BuildConfig.FLAVOR;
    }

    public static void setToken(String token){
        if (sharedPreferences!=null){
            sharedPreferences.edit().putString(TOKEN,token).apply();
        }
    }

    public static boolean isFirstTimeToKYC(){
        return (sharedPreferences!=null && sharedPreferences.getBoolean(IS_FIRST_TIME_TO_KYC,true));
    }


    public static Datum getUserDetails(){
        if(sharedPreferences != null){
        Datum userDetail = new Datum();
        userDetail.setFirstname(sharedPreferences.getString(USER_DETAILS_FIRSTNAME, BuildConfig.FLAVOR));
        userDetail.setLastname(sharedPreferences.getString(USER_DETAILS_LASTNAME, BuildConfig.FLAVOR));
        userDetail.setRole(sharedPreferences.getString(USER_DETAILS_ROLE, BuildConfig.FLAVOR));
        userDetail.setPhoneNumber(sharedPreferences.getString(USER_DETAILS_PHONE, BuildConfig.FLAVOR));
        userDetail.setEmail(sharedPreferences.getString(USER_DETAILS_EMAIL, BuildConfig.FLAVOR));
        userDetail.setPassword(sharedPreferences.getString(USER_DETAILS_PASSWORD, BuildConfig.FLAVOR));
        userDetail.setClientId(sharedPreferences.getString(USER_DETAILS_CLIENT_ID, BuildConfig.FLAVOR));
        return userDetail;
    }
        return null;
}

    public static void setUserDetails(Datum userDetails){
        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_DETAILS_FIRSTNAME, userDetails.getFirstname());
            editor.putString(USER_DETAILS_LASTNAME, userDetails.getLastname());
            editor.putString(USER_DETAILS_CLIENT_ID, userDetails.getClientId());
            editor.putString(USER_DETAILS_EMAIL, userDetails.getEmail());
            editor.putString(USER_DETAILS_PHONE, userDetails.getPhoneNumber());
            editor.putString(USER_DETAILS_PASSWORD, userDetails.getPassword());
            editor.putString(USER_DETAILS_ROLE, userDetails.getRole());
            editor.apply();
        }
    }


    public static void setUserDetailsClientId(String account_number) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(USER_DETAILS_CLIENT_ID, account_number).apply();
        }
    }

    public static String getUserDetailsClientId() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(USER_DETAILS_CLIENT_ID, BuildConfig.FLAVOR);
        }
        return BuildConfig.FLAVOR;
    }

    public static void setUserData(LoginResponse loginResponse) {
        Gson gson = new GsonBuilder().create();
        GsonPreferenceAdapter<LoginResponse> adapter = new GsonPreferenceAdapter<>(gson, LoginResponse.class);
        adapter.set(USER_DATA, loginResponse, sharedPreferences.edit());
    }

    public static LoginResponse getUserData() {
        Gson gson = new GsonBuilder().create();
        GsonPreferenceAdapter<LoginResponse> adapter = new GsonPreferenceAdapter<>(gson, LoginResponse.class);
        LoginResponse loginResponse = adapter.get(USER_DATA, sharedPreferences);
        return loginResponse;
    }

//    public static GetPackageSelecetedResponse getUserDataInPackage() {
//        Gson gson = new GsonBuilder().create();
//        GsonPreferenceAdapter<GetPackageSelecetedResponse> adapter = new GsonPreferenceAdapter<>(gson, GetPackageSelecetedResponse.class);
//        GetPackageSelecetedResponse getPackageSelecetedResponse = adapter.get(USER_DATA, sharedPreferences);
//        return getPackageSelecetedResponse;
//    }

}
