package com.visitor.tengli.face.util;

import android.hardware.Sensor;

/**
 * created by yangshaojie  on 2018/8/27
 * email: ysjr-2002@163.com
 */
public class SensorTypeName {

    private static String[] itsNames;

    static {
        itsNames = new String[20];
        itsNames[0] = "未知";
        itsNames[Sensor.TYPE_ACCELEROMETER] = "加速度";
        itsNames[Sensor.TYPE_MAGNETIC_FIELD] = "磁力";
        itsNames[Sensor.TYPE_ORIENTATION] = "方向";
        itsNames[Sensor.TYPE_GYROSCOPE] = "陀螺仪";
        itsNames[Sensor.TYPE_LIGHT] = "光线感应";
        itsNames[Sensor.TYPE_PRESSURE] = "压力";
        itsNames[Sensor.TYPE_TEMPERATURE] = "温度";
        itsNames[Sensor.TYPE_PROXIMITY] = "接近,距离传感器";
        itsNames[Sensor.TYPE_GRAVITY] = "重力";
        itsNames[Sensor.TYPE_LINEAR_ACCELERATION] = "线性加速度";
        itsNames[Sensor.TYPE_ROTATION_VECTOR] = "旋转矢量";
        itsNames[Sensor.TYPE_RELATIVE_HUMIDITY] = "TYPE_RELATIVE_HUMIDITY";
        itsNames[Sensor.TYPE_AMBIENT_TEMPERATURE] = "TYPE_AMBIENT_TEMPERATURE";
        itsNames[13] = "TYPE_AMBIENT_TEMPERATURE";
        itsNames[14] = "TYPE_MAGNETIC_FIELD_UNCALIBRATED";
        //itsNames[Sensor.TYPE_GAME_ROTATION_VECTOR] = "TYPE_GAME_ROTATION_VECTOR";
    }

    public static String getSensorTypeName(int type) {
        if (type > 0 && type < itsNames.length) {
            return itsNames[type];
        }
        return "未知";
    }
}
