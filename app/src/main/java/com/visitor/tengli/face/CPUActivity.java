package com.visitor.tengli.face;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.visitor.tengli.face.fragment.PathFragment;
import com.visitor.tengli.face.fragment.SensorFragment;

import java.lang.annotation.ElementType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CPUActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rb_sensor)
    RadioButton rbSensor;
    @BindView(R.id.rb_path)
    RadioButton rbPath;
    @BindView(R.id.rg_root)
    RadioGroup rgRoot;
    @BindView(R.id.flroot)
    FrameLayout flroot;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
        ButterKnife.bind(this);

        fm = getFragmentManager();
        rgRoot.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.rb_sensor) {

            FragmentTransaction ft = fm.beginTransaction();
            SensorFragment a = new SensorFragment();
            //传递参数
            Bundle bundle  =new Bundle();
            bundle.putString("key", "ysj");
            a.setArguments(bundle);
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.flroot, a, "a");
            ft.addToBackStack(null);
            ft.commit();
        }

        if (checkedId == R.id.rb_path) {

            FragmentTransaction ft = fm.beginTransaction();
            Fragment temp = fm.findFragmentByTag("b");
            if (temp == null) {
                PathFragment b = new PathFragment();
                ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.flroot, b, "b");
                ft.addToBackStack(null);
                ft.commit();
            } else {
                ft.replace(R.id.flroot, temp, "b");
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }
}
