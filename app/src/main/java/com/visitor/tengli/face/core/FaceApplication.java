package com.visitor.tengli.face.core;

import android.app.Application;

import com.visitor.tengli.face.di.component.DaggerMyComponent;
import com.visitor.tengli.face.di.component.MyComponent;
import com.visitor.tengli.face.di.module.MyModule;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */
public class FaceApplication extends Application {

    private static FaceApplication instance;
    private MyModule myModule;

    public static synchronized FaceApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        myModule = new MyModule(this);
    }

    public static MyComponent getMyComponent() {

        return DaggerMyComponent.builder().myModule(FaceApplication.getInstance().myModule).build();
    }
}
