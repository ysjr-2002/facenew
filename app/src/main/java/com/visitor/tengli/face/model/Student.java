package com.visitor.tengli.face.model;

import javax.inject.Inject;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */
public class Student {

    @Inject
    public Student() {
        this.name = "ysj";
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
