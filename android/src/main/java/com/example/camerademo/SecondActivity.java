package com.example.camerademo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 创建日期：2019/4/24 on 3:37 PM.
 * 描述：
 */

public class SecondActivity extends Activity {
    public static final String VALUE = "flutter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(getIntent().getStringExtra(VALUE));
    }


}
