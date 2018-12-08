package com.avardonigltd.mobilemedicalaid.interfaces;

import android.content.SharedPreferences;

    public interface Adapter<T> {
        T get(String key, SharedPreferences preferences);
        void set(String key, T value, SharedPreferences.Editor editor);
    }


