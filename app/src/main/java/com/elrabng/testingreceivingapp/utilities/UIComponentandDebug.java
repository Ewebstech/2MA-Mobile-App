package com.elrabng.testingreceivingapp.utilities;

import android.text.TextUtils;

public class UIComponentandDebug {
    public static boolean isNull(String string) {
        return TextUtils.isEmpty(string) || TextUtils.equals(string, "null");
    }
}
