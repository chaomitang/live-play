package com.wuqi.playersdk.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.wuqi.playersdk.R;

import io.reactivex.rxjava3.functions.Consumer;

public class TestSurfaceImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_surface_image_view);
        requestCameraPermission();
    }

    private void requestCameraPermission() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Throwable {
                initView();
            }
        }, new Consumer<Throwable>() {
            @SuppressLint("LongLogTag")
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.e("TestSurfaceImageViewActivity", "Camera permission grant failed");
            }
        });

    }

    private void initView() {

    }
}
