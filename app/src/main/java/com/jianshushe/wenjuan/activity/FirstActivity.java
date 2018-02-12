package com.jianshushe.wenjuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.util.ShareUtils;


public class FirstActivity extends FragmentActivity {

    LinearLayout from, tongji, seeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_first);
        from = findViewById(R.id.from);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this, Main3Activity.class);
                startActivity(intent);
            }
        });
        tongji = findViewById(R.id.tongji);
        tongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        seeting = findViewById(R.id.seeting);
        seeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this, SeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolen(this, "isFirst", true);
        if (isFirst) {
            ShareUtils.putBoolen(this, "isFirst", false);
        }
        return isFirst;
    }


}
