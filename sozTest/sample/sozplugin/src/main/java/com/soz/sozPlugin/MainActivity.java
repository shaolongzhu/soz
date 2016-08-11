package com.soz.sozPlugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ImageView) findViewById(R.id.img_test))
                .setImageResource(R.drawable.skin_left_menu_icon);
    }
}
