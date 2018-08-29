package com.visitor.tengli.face;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.visitor.tengli.face.util.Config;
import com.visitor.tengli.face.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyActivity extends BaseActivity {


    @BindView(R.id.button)
    Button button;

    @Override
    int getLayout() {
        return R.layout.activity_study;
    }

    @Override
    void create() {

//        int[] int_arrays = {1, 2, 3, 4, 5};
//        String[] str_array = new String[]{"1", "2", "3", "4", "5"};
//        String[] str_array_temp = {"7", "8", "9", "10"};
//
//        for (int i : int_arrays
//                ) {
//
//            Log.d(Config.tag, "int->" + i);
//        }
//
//        for (String s :
//                str_array) {
//
//            Log.d(Config.tag, "str->" + s);
//        }
//
//        for (String s :
//                str_array_temp) {
//
//            Log.d(Config.tag, "str->" + s);
//        }
//
//        fun(11, 22, 33, 44);
//        hash();
        //list_int();
        list_str();
        getdisplay();


        String a = "12";
        String b = a.concat("23");
    }

    private void getdisplay() {

        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int di = dm.densityDpi;

        Toast.makeText(this, String.format("width %s height %s dpi %s", width, height, di), Toast.LENGTH_SHORT).show();
    }

    private void list_str() {

        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        String x = list.get(1);
        if (TextUtils.equals(list.get(1), "bbb")) {

            String temp = "";
        }
    }

    private void list_int() {

        List<Integer> list = new ArrayList<>();
        for (int i = 100; i < 111; i++) {
            list.add(i);
        }

        int len = list.size();
        for (Integer i :
                list) {

            int kk = i.intValue();
            Log.d(Config.tag, i.toString());

            if (i == 101) {
                String ok = "";
            }
        }
    }

    private void hash() {

        Map<String, String> maps = new HashMap<>();
        maps.put("1", "父亲");
        maps.put("2", "母亲");

        if (maps.containsKey("1")) {
            Log.d(Config.tag, "父亲");
        }

        if (maps.containsKey("2")) {
            Log.d(Config.tag, "母亲");
        }

        if (maps.containsKey("3") == false) {
            Log.d(Config.tag, "不包含");
        }

        for (Map.Entry<String, String> entry :
                maps.entrySet()) {

            Log.d(Config.tag, String.format("key %s  value %s", entry.getKey(), entry.getValue()));
        }

        for (String key :
                maps.keySet()) {
            Log.d(Config.tag, String.format("key %s", key));
        }

        for (String val :
                maps.values()) {
            Log.d(Config.tag, String.format("value %s", val));
        }
    }

    private void fun(int... array) {

        int len = array.length;
        for (int i :
                array) {

            Log.d(Config.tag, "fun->" + i);
        }
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        int width = button.getWidth();
        int height = button.getHeight();
        ToastUtil.Show(this, "width=" + width + " height=" + height);
        LinearLayout.LayoutParams test = new LinearLayout.LayoutParams(300, 92);
        button.setLayoutParams(test);
    }

}
