package com.visitor.tengli.face.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visitor.tengli.face.R;
import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.SensorTypeName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by yangshaojie  on 2018/8/28
 * email: ysjr-2002@163.com
 */
public class SensorFragment extends Fragment {

    //    @BindView(R.id.lv_sensor)
//    ListView lvSensor;
    Unbinder unbinder;

    Context context;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    //23以上版本执行
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        unbinder = ButterKnife.bind(this, view);
        load();
        return view;
    }

    SensorManager sm = null;
    List<Sensor> list = null;


    Sensor sensorTemperature = null;

    private void load() {

        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        list = sm.getSensorList(Sensor.TYPE_ALL);
        List<String> items = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Sensor s : list
                ) {
//            Log.d(Config.tag, s.getType() + " kk=" + s.getStringType());
            String typeName = SensorTypeName.getSensorTypeName(s.getType()) + " " + s.getStringType();
            sb.append(String.format("\t类型:%s\n", typeName));
            sb.append(String.format("\t名称:%s\n", s.getName()));
            sb.append(String.format("\t版本:%s\n", s.getVersion()));
            sb.append(String.format("\t供应商:%s\n", s.getVendor()));
            sb.append("\t--------------------------------\n");
            items.add(sb.toString());
        }

        tvContent.setText(sb.toString());

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, items);
//        lvSensor.setAdapter(adapter);

        sensorTemperature = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (sensorTemperature != null) {
            sm.registerListener(mSensorEventListener, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private final SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                float temperature = event.values[0];
                tvTemperature.setText("温度:" + temperature);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        sm.unregisterListener(mSensorEventListener);
        unbinder.unbind();
    }
}
