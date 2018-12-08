package com.avardonigltd.mobilemedicalaid.adapters;

import android.content.SharedPreferences;

import com.avardonigltd.mobilemedicalaid.interfaces.Adapter;
import com.google.gson.Gson;

    public class GsonPreferenceAdapter<T> implements Adapter<T> {
        final Gson gson;
        private Class<T> clazz;

        // Constructor and exception handling omitted for brevity.
        public GsonPreferenceAdapter(Gson gson, Class<T> clazz){
            this.gson = gson;
            this.clazz = clazz;
        }

        @Override
        public T get(String key, SharedPreferences preferences) {
            return gson.fromJson(preferences.getString(key, ""), clazz);
        }

        @Override
        public void set(String key, T value, SharedPreferences.Editor editor) {
            editor.putString(key, gson.toJson(value)).apply();
        }
    }

