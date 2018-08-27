package com.visitor.tengli.face;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.visitor.tengli.face.core.FaceApplication;
import com.visitor.tengli.face.core.IView;
import com.visitor.tengli.face.di.component.DaggerMyComponent;
import com.visitor.tengli.face.di.component.MyComponent;
import com.visitor.tengli.face.di.module.MyModule;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initInject();
        create();
    }

    abstract int getLayout();

    abstract void create();

    protected abstract void initInject();


    protected MyComponent getActivityComponent() {
//        return DaggerMyComponent.builder().app
//                .myModule(getActivityModule())
//                .build();

        return FaceApplication.getMyComponent();
    }

//    private MyModule getActivityModule() {
//        return new MyModule(getApplication());
//}
}
