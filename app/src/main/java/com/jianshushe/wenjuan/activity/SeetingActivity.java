package com.jianshushe.wenjuan.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.util.ShareUtils;

public class SeetingActivity extends FragmentActivity {

    private Switch isSpeck;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting);
        isSpeck = findViewById(R.id.isSpeck);

        if (isSpeck()) {
         //  isSpeck.setActivated(true);
           isSpeck.setChecked(true);
        } else {
            isSpeck.setChecked(false);
        }
        isSpeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ShareUtils.putBoolen(SeetingActivity.this, "isSpeck", true);
                } else {
                    ShareUtils.putBoolen(SeetingActivity.this, "isSpeck", false);
                }
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isSpeck() {

        boolean isSpeck = ShareUtils.getBoolen(this, "isSpeck", false);
        if (isSpeck) {
            return true;
        } else {
            return false;
        }
    }
}
