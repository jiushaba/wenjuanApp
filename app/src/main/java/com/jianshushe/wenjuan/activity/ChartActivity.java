package com.jianshushe.wenjuan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.adapter.FragmentAdapter;
import com.jianshushe.wenjuan.entity.ChartEntity.Answers;
import com.jianshushe.wenjuan.entity.ChartEntity.ChartEntity;
import com.jianshushe.wenjuan.entity.ChartEntity.Questions;
import com.jianshushe.wenjuan.fragment.chart.ChartFragment;
import com.jianshushe.wenjuan.fragment.chart.HistogramFragment;
import com.jianshushe.wenjuan.fragment.chart.PieChartFragmentFragment;
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


public class ChartActivity extends FragmentActivity {

    List<Fragment> fragmentList = null;
    ViewPager viewPager;
  //  List<ChartEntity> list = null;
    TextView tittle;
    Questions questions = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        questions = (Questions) getIntent().getSerializableExtra("list");
        initView();
        initData();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        HistogramFragment histogramFragment = null;
        PieChartFragmentFragment pieChartFragmentFragment = null;
        ChartFragment chartFragment = null;
        if (questions.getType()==0){
            histogramFragment = new HistogramFragment(questions,0);
            pieChartFragmentFragment = new PieChartFragmentFragment(questions);
            fragmentList.add(histogramFragment);
            fragmentList.add(pieChartFragmentFragment);
        } else if (questions.getType() == 1) {

        } else if (questions.getType() == 2) {
            chartFragment = new ChartFragment(questions);
            histogramFragment = new HistogramFragment(questions,2);
            fragmentList.add(histogramFragment);
            fragmentList.add(chartFragment);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fm, fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tittle.setText(questions.getQuestionName());
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        tittle = findViewById(R.id.tittle);
    }


    private void sendRequest() {
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(StaticCon.url(ChartActivity.this)+"/survey/analyze?index=1");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
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
                    e.printStackTrace();
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
    private   List<ChartEntity> respone(final String response) {
        List<ChartEntity> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            ChartEntity chartEntity = new ChartEntity();
            Questions questions = null;
            Answers answers = null;
            chartEntity.setName(jsonObject.getString("name"));
            JSONArray jsonArray = jsonObject.getJSONArray("questions");
            for (int i = 0; i < jsonArray.length(); i++) {
                questions = new Questions();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                questions.setQuestionName(jsonObject1.getString("questionName"));
                JSONArray jsonArray1 = jsonObject1.getJSONArray("answers");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    answers = new Answers();
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    answers.setOptionName(jsonObject2.getString("optionName"));
                    answers.setNum(jsonObject2.getInt("num"));
                    questions.list.add(answers);
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


            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
