package com.visitor.tengli.face.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.visitor.tengli.face.di.scope.ContextLife;

import java.net.PortUnreachableException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ysj on 2018/2/27.
 */

@Singleton
public class SharedPreferencesHelper {

    private SharedPreferences mPref;
    private Context context;

    public static final String KOALA_IP = "koala";
    public static final String CAMERA_IP = "camera";
    public static final String GREEN_DELAY = "green";
    public static final String RED_DELAY = "red";
    public static final String CLOSEGATE_DELAY = "close";
    public static final String STRANGER = "stranger";

    @Inject
    public SharedPreferencesHelper(@ContextLife Context context) {
        this.context = context;
        mPref = context.getSharedPreferences("dalu", Context.MODE_PRIVATE);
    }

    public void setStringValue(String tag, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public void setBooleanValue(String tag, boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(tag, value);
        editor.commit();
    }

    public String getStringValue(String tag, String defaultValue) {
        return mPref.getString(tag, defaultValue);
    }

    public boolean getBooleanValue(String tag, boolean defaultValue) {
        return mPref.getBoolean(tag, defaultValue);
    }
}
