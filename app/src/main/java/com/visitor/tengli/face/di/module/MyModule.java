package com.visitor.tengli.face.di.module;

import android.app.Application;
import android.content.Context;

import com.visitor.tengli.face.core.FaceApplication;
import com.visitor.tengli.face.core.LightHelper;
import com.visitor.tengli.face.helpers.SharedPreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */
@Module
public class MyModule {

    private final FaceApplication context;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public MyModule(FaceApplication context) {

        this.context = context;
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    @Singleton
    @Provides
    public SharedPreferencesHelper provideSharePreferenceHelper() {
        return mSharedPreferencesHelper;
    }

    @Singleton
    @Provides
    public LightHelper provideLigthHelper() {
        return new LightHelper();
    }
}
