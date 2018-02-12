package com.jianshushe.wenjuan.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.jianshushe.wenjuan.R;

import java.util.ArrayList;

public class PlshActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plsh);
        final View root = findViewById(R.id.welcome);
        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(1f, 0.1f);
        aa.setDuration(3000);
        //控制渐变时间
        root.startAnimation(aa);
        aa.setFillAfter(true);//动画结束后保持状态
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                initPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

    @Override
    public void onBackPressed() {

    }

    //  下面是android 6.0以上的动态授权

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.CAMERA
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        if (!toApplyList.isEmpty()) {
            String[] tmpList = new String[toApplyList.size()];
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        } else {
            open();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        switch (requestCode) {
            case 123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PlshActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                    open();
                } else {
                   /* Toast.makeText(PlshActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();*/
                   open();
                }
                break;
        }
    }

    private void open() {
        Intent intent = new Intent();
        intent.setClass(PlshActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }

}
