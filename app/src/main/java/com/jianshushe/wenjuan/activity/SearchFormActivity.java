package com.jianshushe.wenjuan.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.serachForm.Form;
import com.jianshushe.wenjuan.util.StaticCon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchFormActivity extends FragmentActivity {

    ListView listView;
    EditText search;
    Button chazhao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);
        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search);
        chazhao = findViewById(R.id.chazhao);
        chazhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

    }




    private void sendRequest() {
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(StaticCon.url(SearchFormActivity.this) + "/android/form/find?name");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(4000);
                    connection.setReadTimeout(4000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showRespone(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    netWorkErro();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }



    private void showRespone(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Form> formList = respone(response.toString());
                String[] data = new String[formList.size()];
                for (Form form : formList) {
                    data[formList.indexOf(form)] = form.getFormName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchFormActivity.this, android.R.layout.simple_list_item_1, data);
                listView.setAdapter(adapter);
            }
        });
    }





    /**
     * 解析数据
     *
     * @param response
     * @return
     */
    private List<Form> respone(final String response) {
        Gson gson = new Gson();
        List<Form> formList = gson.fromJson(response, new TypeToken<List<Form>>() {
        }.getType());
        return formList;
    }

    private void netWorkErro() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Toast.makeText(getApplicationContext(), "网络错误，请重新设置", 2000 * 1).show();
                 /*   Intent intent = new Intent();
                    intent.setClass(Main3Activity.this, NetWorkActivity.class);
                    startActivity(intent);*/
                    finish();
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
                Looper.loop();
            }
        }).start();
    }


}
