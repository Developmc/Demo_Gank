package com.example.gankdemo.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.gankdemo.base.interfaces.ActivityInitializer;

import butterknife.ButterKnife;

/**Base Activity
 * Created by clement on 17/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityInitializer {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onBindLayoutID());
        ButterKnife.bind(this);
        initBehavior(savedInstanceState);
    }
}
