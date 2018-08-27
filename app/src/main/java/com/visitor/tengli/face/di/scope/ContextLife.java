package com.visitor.tengli.face.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * com.megvii.alibaba.door.ui.activity
 * ali-door
 * @author rick
 * 2017/9/14.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ContextLife {
    String value() default "Application";
}
