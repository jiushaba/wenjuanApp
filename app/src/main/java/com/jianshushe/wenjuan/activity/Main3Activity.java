package com.jianshushe.wenjuan.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.Questions;
import com.jianshushe.wenjuan.entity.Survey;
import com.jianshushe.wenjuan.entity.Type;
import com.jianshushe.wenjuan.fragment.fromFragment.CheckBoxFragment;
import com.jianshushe.wenjuan.fragment.fromFragment.PingFengFragment;
import com.jianshushe.wenjuan.fragment.fromFragment.RadioFragment;
import com.jianshushe.wenjuan.fragment.fromFragment.TextFragment;
import com.jianshushe.wenjuan.model.Form;
import com.jianshushe.wenjuan.model.answers;
import com.jianshushe.wenjuan.ui.FromFragment;
import com.jianshushe.wenjuan.util.CameraManager;
import com.jianshushe.wenjuan.util.CustomViewPager;
import com.jianshushe.wenjuan.util.GPSUtils;
import com.jianshushe.wenjuan.util.ShareUtils;
import com.jianshushe.wenjuan.util.StaticCon;
import com.jianshushe.wenjuan.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Main3Activity extends FragmentActivity {


    Survey list = null;
    String uuidStr;
    List<Fragment> fragments = null;
    CustomViewPager viewPager;
    List<Questions> questionsList = new ArrayList<>();
    TextView formTittle;
    Button next;
    //进度条
    ProgressBar progressBar;
    int index, listSize;
    FromFragment[] fragment;
    GPSUtils gpsUtils = null;
    SimpleDateFormat simpleDateFormat = null;
    Form form = new Form();
    SpeechSynthesizer mSpeechSynthesizer = null;
    List<Form> forms = null;
    List<answers> answersList = null;
    //是否开启语音提示
    boolean isSpeck;
    /**
     * 定义前置有关的参数
     */
    private CameraManager frontCameraManager;
    private SurfaceView frontSurfaceView;
    private SurfaceHolder frontHolder;
    private boolean isFrontOpened = false;
    private Camera mFrontCamera;


    /**
     * 按钮倒计时
     *
     * @param
     */

    private TimeCount time;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main3);
        /**
         * 初始化前置相机参数
         */
        // 初始化surface view
        // 初始化surface holder
        frontSurfaceView = findViewById(R.id.surfaceView);
        frontHolder = frontSurfaceView.getHolder();
        frontCameraManager = new CameraManager(mFrontCamera, frontHolder);
        initData();
        UUID uuid = UUID.randomUUID();
        isSpeck = isSpeck = ShareUtils.getBoolen(this, "isSpeck", false);
        uuidStr = uuid.toString();
        forms = new ArrayList<>();
        answersList = new ArrayList<>();
        LitePal.getDatabase();
        sendRequest();
        DataSupport.deleteAll(Form.class);
        DataSupport.deleteAll(answers.class);
        gpsUtils = new GPSUtils(this);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewPager = findViewById(R.id.viewPager);
        viewPager.setScanScroll(false);
        formTittle = findViewById(R.id.formTittle);
        next = findViewById(R.id.next);
        progressBar = findViewById(R.id.progressBar);
        time = new TimeCount(4000, 1000);
        time.start();
    }

    private void initData() {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this); // this 是Context的之类，如Activity
        //      mSpeechSynthesizer.setSpeechSynthesizerListener(listener); //listener是SpeechSynthesizerListener 的实现类，需要实现您自己的业务逻辑。SDK合成后会对这个类的方法进行回调。
        mSpeechSynthesizer.setAppId("10632440");
        mSpeechSynthesizer.setApiKey("lwYx4qqBHA5SGzYK5hveD0Qf", "F24CHCFW6RUgaixvL2bgXpxZD1oT6Ejh");
        mSpeechSynthesizer.auth(TtsMode.MIX);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置发声的人声音，在线生效
        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            //根据fragment的包名得到对应的fragment
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

    }


    private void showRespone(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作
                list = respone(response);
                fragments = new ArrayList<>();
                questionsList = list.questions;
                formTittle.setText(list.getName());
                setData(list.getSurveyid());
                fragment = new FromFragment[questionsList.size()];
                if (questionsList.size() > 1) {
                    next.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < questionsList.size(); i++) {
                    if (questionsList.get(i).getType() == 0) {
                        //单选型
                        fragments.add(fragment[i] = new RadioFragment(questionsList.get(i), i + 1, uuidStr, mFrontCamera, frontCameraManager));
                    } else if (questionsList.get(i).getType() == 1) {
                        //TODO 多选型
                        fragments.add(fragment[i] = new CheckBoxFragment(questionsList.get(i), i + 1, uuidStr, mFrontCamera, frontCameraManager));
                    } else if (questionsList.get(i).getType() == 2) {
                        //评分型
                        fragments.add(fragment[i] = new PingFengFragment(questionsList.get(i), i + 1, uuidStr, mFrontCamera, frontCameraManager));
                    } else if (questionsList.get(i).getType() == 3) {
                        //文字型
                        fragments.add(fragment[i] = new TextFragment(questionsList.get(i), i + 1, uuidStr, mFrontCamera, frontCameraManager));
                    }
                }
                FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(0);
                listSize = questionsList.size();
                index = 0;
                progressBar.setMax(listSize);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //获取进度条的当前值
                            int progressVal = progressBar.getProgress();
                            if (index == listSize - 1) {
                                //TODO  提交
                                if (fragment[index].upData()) {
                                    upDataForm();
                                    subData();
                                    post();
                                    postImg();
                                    Intent intent = new Intent();
                                    intent.setClass(Main3Activity.this, FirstActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (isSpeck) {
                                        mSpeechSynthesizer.speak("还未回答");
                                    }
                                }
                            } else if (index < listSize - 1) {
                                time.start();
                                if (fragment[index].upData()) {
                                    mSpeechSynthesizer.stop();
                                    if (isSpeck) {
                                        mSpeechSynthesizer.speak("已答完" + (index + 1) + "个题，还有" + (listSize - (index + 1) + "个题未答"));
                                    }
                                    viewPager.setCurrentItem(++index);
                                    if (index == listSize - 1) {
                                        next.setText("完成");
                                    }
                                    progressBar.setProgress(progressVal+1);
                                } else {
                                    if (isSpeck) {
                                        mSpeechSynthesizer.speak("还未回答");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.e("语音异常", e.getLocalizedMessage());
                        }
                    }
                });
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
                    URL url = new URL(StaticCon.url(Main3Activity.this) + "/survey/getSurvey?index=3");
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

    /**
     * 解析数据
     *
     * @param response
     * @return
     */
    private Survey respone(final String response) {
        Survey survey = new Survey();
        try {
            JSONObject jsonObject = new JSONObject(response);
            Questions questions = null;
            Type type = null;
            JSONObject jsonObject2 = null;
            JSONObject jsonObject1 = null;
            survey.setName(jsonObject.getString("name"));
            survey.setSurveyid(jsonObject.getInt("surveyID"));
            JSONArray jsonArray = jsonObject.getJSONArray("questions");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject1 = jsonArray.getJSONObject(i);
                questions = new Questions();
                questions.setName(jsonObject1.getString("name"));
                questions.setType(jsonObject1.getInt("type"));
                questions.setDes(jsonObject1.getString("des"));
                questions.setMust(jsonObject1.getInt("must"));
                questions.setSubName(jsonObject1.getString("subName"));
                questions.setQuestionId(jsonObject1.getInt("questionID"));
                if (jsonObject1.has("options")) {
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("options");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        jsonObject2 = jsonArray1.getJSONObject(j);
                        type = new Type();
                        type.setName(jsonObject2.getString("name"));
                        type.setOptionId(jsonObject2.getInt("optionID"));
                        questions.typeList.add(type);
                    }
                }
                survey.questions.add(questions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return survey;
    }


    @Override
    public void onBackPressed() {
        mSpeechSynthesizer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSpeechSynthesizer.release();
        finish();
    }

    private void setData(int surveyId) {
        String strData = simpleDateFormat.format(new Date());
        form.setSurveyID(surveyId + "");
        form.setKey(uuidStr);
        form.setStartdate(strData);
        form.setSpotName(gpsUtils.getAddressStr());
        form.save();
    }

    private void upDataForm() {
        form.setEnddate(simpleDateFormat.format(new Date()));
        form.save();
    }


    /**
     * 提交数据到服务器
     */
    private void subData() {
        forms = DataSupport.findAll(Form.class);
        answersList = DataSupport.findAll(answers.class);
        Log.e("list", forms.toString());
    }


    private void post() {
        String url = StaticCon.url(Main3Activity.this) + "/survey/answer";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parm = null;
        JSONObject jsonObject = null;
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < answersList.size(); i++) {
                jsonObject = new JSONObject();
                jsonObject.put("questionID", answersList.get(i).getQuestionID());
                jsonObject.put("answer", answersList.get(i).getAnswer());
                array.put(jsonObject);
            }
            Form form = forms.get(0);
            parm = new JSONObject();
            parm.put("key", form.getKey());
            parm.put("surveyID", form.getSurveyID());
            parm.put("startdate", form.getStartdate());
            parm.put("enddate", form.getEnddate());
            parm.put("spotName", form.getSpotName());
            parm.put("answers", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("parm", parm.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parm, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("isOk")) {
                        Toast.makeText(Main3Activity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    private void postImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.questions.size(); i++) {
                    String u = StaticCon.url(Main3Activity.this) + "/survey/upImg?key=" + uuidStr + "&questionID=" + list.questions.get(i).getQuestionId();
                    String imageFile = "mnt/sdcard/wenjuan/image/" + uuidStr + "_" + list.questions.get(i).getQuestionId() + ".jpg";
                    try {
                        Util.postData(imageFile, u);
                    } catch (Exception e) {
                        Toast.makeText(Main3Activity.this, "提交错误", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }).start();

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


    /**
     * 按钮倒计时
     */

    class TimeCount extends CountDownTimer{



        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //总时长和计时的间隔
        }

        @Override
        public void onTick(long l) {   // 计时过程显示
            next.setEnabled(false);
            next.setText(l/1000+"秒");
        }

        @Override
        public void onFinish() { //计时完毕时触发
            if (index == listSize - 1) {
                next.setText("完成");
                next.setEnabled(true);
            } else {
                next.setText("下一项");
                next.setEnabled(true);
            }

        }
    }

}
