package com.example.zhushaolong.soztest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.soz.utils.AppManagerUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void runApp(View view) {
        AppManagerUtils.runApp(this, "com.letv.tv");
    }
}
