package com.avardonigltd.mobilemedicalaid.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.RxSharedPreferences;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxAppPreference {

    private static RxSharedPreferences rxSharedPreferences;

    public static void setUpDefault(Context context) {
        SharedPreferences sharedPreferences = AppPreference.setUpDefault(context);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }

    public static boolean isNull(String string){
        return TextUtils.isEmpty(string) || TextUtils.equals(string, "null");
    }

    public static Disposable getRxUserRole(final EditText editText) {
        if(rxSharedPreferences != null) {
            return rxSharedPreferences.getString(AppPreference.USER_DETAILS_ROLE, "")
                    .asObservable()
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            editText.setText(isNull(s)? "" : s);
                        }
                    });
        }
        return null;
    }

    public static Disposable getRxUserNumber(final EditText editText) {
        if(rxSharedPreferences != null) {
            return rxSharedPreferences.getString(AppPreference.USER_DETAILS_PHONE, "")
                    .asObservable()
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            editText.setText(isNull(s)? "" : s);
                        }
                    });
        }
        return null;
    }

    public static Disposable getRxUserEmail(final EditText editText) {
        if(rxSharedPreferences != null) {
            return rxSharedPreferences.getString(AppPreference.USER_DETAILS_EMAIL, "")
                    .asObservable()
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            editText.setText(isNull(s)? "" : s);
                        }
                    });
        }
        return null;
    }

}

