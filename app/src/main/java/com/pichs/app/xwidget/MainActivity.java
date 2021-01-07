package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.pichs.switcher.Switcher;

public class MainActivity extends AppCompatActivity {

    private Switcher mSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitcher = findViewById(R.id.swwww);
    }

    public void changeColor(View view) {
        mSwitcher.setSize(100, 50);
        ViewGroup.LayoutParams layoutParams = mSwitcher.getLayoutParams();
        layoutParams.height = 200;
        layoutParams.width = 100;
        mSwitcher.setLayoutParams(layoutParams);

        mSwitcher.setSwitcherColor(Color.RED, Color.BLUE, Color.BLACK);

    }
}