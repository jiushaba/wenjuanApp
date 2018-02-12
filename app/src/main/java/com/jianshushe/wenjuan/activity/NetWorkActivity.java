package com.jianshushe.wenjuan.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.util.ShareUtils;

public class NetWorkActivity extends FragmentActivity {


    ImageView back;
    EditText ip_1,ip_2,ip_3,ip_4, port;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ip_1 = findViewById(R.id.ip_1);
        ip_1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //失去焦点的事件
                    String ip_1val = ip_1.getText().toString().trim();
                    if (!ip_1val.equals("")) {
                        if (Integer.parseInt(ip_1val) > 255) {
                            Toast.makeText(NetWorkActivity.this, "不能大于255", Toast.LENGTH_SHORT).show();
                            ip_1.setText("");
                        }
                    }
                }
            }
        });
        ip_2 = findViewById(R.id.ip_2);
        ip_2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //失去焦点的事件
                    String ip_2val = ip_2.getText().toString().trim();
                    if (!ip_2val.equals("")) {
                        if (Integer.parseInt(ip_2val) > 255) {
                            Toast.makeText(NetWorkActivity.this, "不能大于255", Toast.LENGTH_SHORT).show();
                            ip_2.setText("");
                        }
                    }
                }
            }
        });
        ip_3 = findViewById(R.id.ip_3);
        ip_3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //失去焦点的事件
                    String ip_3val = ip_3.getText().toString().trim();
                    if (!ip_3val.equals("")) {
                        if (Integer.parseInt(ip_3val) > 255) {
                            Toast.makeText(NetWorkActivity.this, "不能大于255", Toast.LENGTH_SHORT).show();
                            ip_3.setText("");
                        }
                    }
                }
            }
        });
        ip_4 = findViewById(R.id.ip_4);
        ip_4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //失去焦点的事件
                    String ip_4val = ip_4.getText().toString().trim();
                    if (!ip_4val.equals("")) {
                        if (Integer.parseInt(ip_4val) > 255) {
                            Toast.makeText(NetWorkActivity.this, "不能大于255", Toast.LENGTH_SHORT).show();
                            ip_4.setText("");
                        }
                    }
                }
            }
        });
        port = findViewById(R.id.port);
        port.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //失去焦点的事件
                String port_val = port.getText().toString().trim();
                if (!port_val.equals("")) {
                    if (Integer.parseInt(port_val) > 65535) {
                        Toast.makeText(NetWorkActivity.this, "不能大于255", Toast.LENGTH_SHORT).show();
                        port.setText("");
                    }
                }
            }
        });
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipVal = "";
                String ip_1Val = ip_1.getText().toString().trim();
                String ip_2Val = ip_2.getText().toString().trim();
                String ip_3Val = ip_3.getText().toString().trim();
                String ip_4Val = ip_4.getText().toString().trim();
                if (!ip_1Val.equals("") && !ip_2Val.equals("") && !ip_3Val.equals("") && !ip_4Val.equals("")) {
                    ipVal = ip_1Val + "." + ip_2Val + "." + ip_3Val + "." + ip_4Val;
                }
                String portVal = port.getText().toString();
                if (!ipVal.equals("") && !portVal.equals("")) {
                    ShareUtils.putString(NetWorkActivity.this, "ip", ipVal);
                    ShareUtils.putString(NetWorkActivity.this, "port", portVal);
                    Toast.makeText(NetWorkActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (ipVal.equals("")) {
                        Toast.makeText(NetWorkActivity.this, "地址为空", Toast.LENGTH_SHORT).show();
                        if (ip_1Val.equals("")) {
                            ip_1.setFocusable(true);
                            ip_1.setFocusableInTouchMode(true);
                            ip_1.requestFocus();
                        } else if (ip_2Val.equals("")) {
                            ip_2.setFocusable(true);
                            ip_2.setFocusableInTouchMode(true);
                            ip_2.requestFocus();
                        } else if (ip_3Val.equals("")) {
                            ip_3.setFocusable(true);
                            ip_3.setFocusableInTouchMode(true);
                            ip_3.requestFocus();
                        } else if (ip_4Val.equals("")) {
                            ip_4.setFocusable(true);
                            ip_4.setFocusableInTouchMode(true);
                            ip_4.requestFocus();
                        }
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }else if (portVal.equals("")){
                        Toast.makeText(NetWorkActivity.this, "端口为空", Toast.LENGTH_SHORT).show();
                        port.setFocusable(true);
                        port.setFocusableInTouchMode(true);
                        port.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                }
            }
        });
        ip_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3) {
                    ip_2.setFocusable(true);
                    ip_2.setFocusableInTouchMode(true);
                    ip_2.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        ip_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3) {
                    ip_3.setFocusable(true);
                    ip_3.setFocusableInTouchMode(true);
                    ip_3.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        ip_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3) {
                    ip_4.setFocusable(true);
                    ip_4.setFocusableInTouchMode(true);
                    ip_4.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        ip_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 3) {
                    port.setFocusable(true);
                    port.setFocusableInTouchMode(true);
                    port.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        EditText[] edArr = {ip_1, ip_2, ip_3, ip_4};
        if (isNetWork()) {
            String ipval = ShareUtils.getString(NetWorkActivity.this, "ip", "");
            String portval = ShareUtils.getString(NetWorkActivity.this, "port", "");
            String[] ipArr = ipval.split("\\.");
            for (int i = 0; i <ipArr.length ; i++) {
                edArr[i].setText(ipArr[i]);
            }
            port.setText(portval);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean isNetWork() {
        String ip = ShareUtils.getString(NetWorkActivity.this, "ip", "");
        String port = ShareUtils.getString(NetWorkActivity.this, "port", "");
        if (!ip.equals("") && !port.equals("")) {
            return true;
        }
        return false;
    }
}
