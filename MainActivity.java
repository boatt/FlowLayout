package com.itheima.demo2;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowLayout = new FlowLayout(getBaseContext());
        setContentView(flowLayout);
        int padding = 10;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 300; i= i+20) {
            list.add(i + "-:-" + i);
        }
        flowLayout.setPadding(padding, padding, padding, padding);
        TextView tv;
        for (String text : list) {
            tv = new TextView(getBaseContext());
            Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), 5);
            Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), 5);
            tv.setBackground(DrawableUtil.generateSelector(pressed, normal));
            tv.setTextColor(Color.WHITE);
            tv.setPadding(padding, padding, padding, padding);
            tv.setText(text);
            tv.setClickable(true);
            flowLayout.addView(tv);
        }
    }
}
