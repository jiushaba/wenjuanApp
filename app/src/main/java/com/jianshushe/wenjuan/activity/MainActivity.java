package com.jianshushe.wenjuan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.ChartEntity.Answers;
import com.jianshushe.wenjuan.entity.ChartEntity.ChartEntity;
import com.jianshushe.wenjuan.entity.ChartEntity.Questions;
import com.jianshushe.wenjuan.entity.ChartEntity.Type2;
import com.jianshushe.wenjuan.util.StaticCon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    ListView listView;
    List<ChartEntity> list = null;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sendRequest();
        listView = findViewById(R.id.listVIew);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    URL url = new URL(StaticCon.url(MainActivity.this)+"/survey/analyze?index=3");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
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

    /**
     * 解析数据
     *
     * @param response
     * @return
     */
    private List<ChartEntity> respone(final String response) {
        List<ChartEntity> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            ChartEntity chartEntity = new ChartEntity();
            Questions questions = null;
            Answers answers = null;
            Type2 type2 = null;
            chartEntity.setName(jsonObject.getString("name"));
            JSONArray jsonArray = jsonObject.getJSONArray("array");
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                questions = new Questions();
                questions.setQuestionName(jsonObject1.getString("questionName"));
                questions.setType(jsonObject1.getInt("type"));
                JSONArray jsonArray1 = jsonObject1.getJSONArray("answers");
                if (jsonObject1.getInt("type") == 0) {
                    for (int j = 0; j <jsonArray1.length() ; j++) {
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                        answers = new Answers();
                        answers.setOptionName(jsonObject2.getString("optionName"));
                        answers.setNum(jsonObject2.getInt("num"));
                        answers.setType(jsonObject2.getInt("type"));
                        questions.list.add(answers);
                    }
                } else if (jsonObject1.getInt("type") == 2||jsonObject1.getInt("type") == 3) {
                    for (int j = 0; j <jsonArray1.length() ; j++) {
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                        type2 = new Type2();
                        type2.setAnswer(jsonObject2.getString("answer"));
                        questions.type2List.add(type2);
                    }
                }
                chartEntity.list.add(questions);

            }
            list.add(chartEntity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }


    private void showRespone(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作
                list = respone(response);
                if (list.size() > 0) {
                    MainActvityAdapter adaper = new MainActvityAdapter(list.get(0), MainActivity.this);
                    listView.setAdapter(adaper);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Questions quest = list.get(0).list.get(position);
                            if (quest.getType() == 0||quest.getType() == 1||quest.getType() == 2) {
                                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                                intent = intent.putExtra("list", quest);
                                startActivity(intent);
                            } else if (quest.getType() == 3) {
                                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                                intent = intent.putExtra("list", quest);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        });
    }

    class MainActvityAdapter extends BaseAdapter {

        ChartEntity chartEntity;
        Context context;

        public MainActvityAdapter(ChartEntity chartEntity, Context context) {
            this.chartEntity = chartEntity;
            this.context = context;
        }

        @Override
        public int getCount() {
            return chartEntity.list.size();
        }

        @Override
        public Object getItem(int position) {
            return chartEntity.list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            TextView name = convertView.findViewById(R.id.name);
            name.setText(chartEntity.list.get(position).getQuestionName());
            return convertView;
        }
    }


    private void netWorkErro() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Toast.makeText(getApplicationContext(), "网络错误，请重新设置", 2000 * 1).show();
                  /*  Intent intent = new Intent();
                    intent.setClass(MainActivity.this, NetWorkActivity.class);
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
