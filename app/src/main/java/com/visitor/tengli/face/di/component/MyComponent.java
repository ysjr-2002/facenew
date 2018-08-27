package com.visitor.tengli.face.di.component;

import com.visitor.tengli.face.CenterActivity;
import com.visitor.tengli.face.InitActivity;
import com.visitor.tengli.face.RtspActivity;
import com.visitor.tengli.face.SettingActivity;
import com.visitor.tengli.face.di.module.MyModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */
@Singleton
@Component(modules = MyModule.class)
public interface MyComponent {

    void inject(InitActivity activity);

    void inject(SettingActivity activity);

    void inject(RtspActivity activity);

    void inject(CenterActivity activity);
}
