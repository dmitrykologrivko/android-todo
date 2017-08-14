package com.dmitrykologrivkogmail.todolist.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public final class DeviceUtil {

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
