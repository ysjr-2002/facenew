package com.visitor.tengli.face.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visitor.tengli.face.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by yangshaojie  on 2018/8/28
 * email: ysjr-2002@163.com
 */
public class PathFragment extends Fragment {

    Context context;
    @BindView(R.id.tv_content)
    TextView tvContent;
    Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_path, container, false);

        read();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void read() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            File dir = new File("/sys/class/thermal/");

            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (Pattern.matches("thermal_zone[0-9]+", file.getName())) {
                        return true;
                    }
                    return false;
                }
            });


            final int SIZE = files.length;
            String line = null;
            String type = null;
            String temp = null;
            for (int i = 0; i < SIZE; i++) {
                br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone" + i + "/type"));
                line = br.readLine();
                if (line != null) {
                    type = line;
                }

                br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone" + i + "/temp"));
                line = br.readLine();
                if (line != null) {
                    long temperature = Long.parseLong(line);
                    if (temperature < 0) {
                        temp = "Unknow";
                    } else {
                        temp = (float) (temperature / 1000.0) + "°C";
                    }
                }
                sb.append("\t" + type + " : " + temp + "\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            sb.append("\t" + e.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        tvContent.setText(sb.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
