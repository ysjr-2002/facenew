package com.visitor.tengli.face.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.visitor.tengli.face.di.scope.ContextLife;

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
    public static final String WELCOME = "welcome";

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

    public String getStringValue(String tag, String defaultValue) {
        return mPref.getString(tag, defaultValue);
    }

}