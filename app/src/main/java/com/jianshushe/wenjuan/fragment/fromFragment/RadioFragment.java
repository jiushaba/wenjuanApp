package com.jianshushe.wenjuan.fragment.fromFragment;


import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jianshushe.wenjuan.ui.FromFragment;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.Questions;
import com.jianshushe.wenjuan.model.answers;
import com.jianshushe.wenjuan.util.CameraManager;

/**
 * 单选Fragment
 */
@SuppressLint("ValidFragment")
public class RadioFragment extends FromFragment {
    /**
     * 定义前置有关的参数
     */
    private Camera mFrontCamera;
    CameraManager frontCameraManager;
    //fragment

    Questions list;
    TextView problem;
    RadioButton radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10;
    RadioButton[] arr = null;
    int i;
    String uuidStr;

    public RadioFragment(Questions list, int i, String uuidStr, Camera mFrontCamera, CameraManager frontCameraManager) {
        this.list = list;
        this.i = i;
        this.uuidStr = uuidStr;
        this.mFrontCamera = mFrontCamera;
        this.frontCameraManager = frontCameraManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initData() {
        problem.setText(i + "、" + list.getName());
        for (int a = 0; a < list.typeList.size(); a++) {
            arr[a].setVisibility(View.VISIBLE);
            arr[a].setText(list.typeList.get(a).getName());
        }
    }

    private void initView(View view) {
        problem = view.findViewById(R.id.problem);
        radio1 = view.findViewById(R.id.Radio_1);
        radio2 = view.findViewById(R.id.Radio_2);
        radio3 = view.findViewById(R.id.Radio_3);
        radio4 = view.findViewById(R.id.Radio_4);
        radio5 = view.findViewById(R.id.Radio_5);
        radio6 = view.findViewById(R.id.Radio_6);
        radio7 = view.findViewById(R.id.Radio_7);
        radio8 = view.findViewById(R.id.Radio_8);
        radio9 = view.findViewById(R.id.Radio_9);
        radio10 = view.findViewById(R.id.Radio_10);
        arr = new RadioButton[]{radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10};

    }


    @Override
    public boolean upData() {
        boolean isFlash = false;
        String res = "";
        for (int j = 0; j < list.typeList.size(); j++) {
            if (arr[j].isChecked()) {
                res = String.valueOf(list.typeList.get(j).getOptionId());
            }
        }
        if (list.getMust() == 0) {
            setData(list.getQuestionId(), res, uuidStr);
            isFlash = true;
        } else if (list.getMust() == 1) {
            if (res.equals("")) {
                isFlash = false;
            } else {
                setData(list.getQuestionId(), res, uuidStr);
                isFlash = true;
            }
        }
        if (takeFrontPhoto() && isFlash) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 保存答题数据到数据库
     *
     * @param questionId
     * @param answerval
     * @param formId
     */
    private void setData(int questionId, String answerval, String formId) {
        answers answers = new answers();
        answers.setFormId(formId);
        answers.setAnswer(answerval);
        answers.setQuestionID(questionId);
        answers.save();
    }


    /**
     * @return 开启前置摄像头照相
     */
    private boolean takeFrontPhoto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (frontCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
                    frontCameraManager.setImageName(uuidStr, list.getQuestionId() + "");
                    mFrontCamera = frontCameraManager.getCamera();
                    //自动对焦
                    mFrontCamera.autoFocus(mAutoFocus);
                    // 拍照
                    mFrontCamera.takePicture(null, null, frontCameraManager.new PicCallback(mFrontCamera));
                }

            }
        }).start();
        return true;
    }

    /**
     * 自动对焦的回调方法，用来处理对焦成功/不成功后的事件
     */
    private Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //TODO:空实现
        }
    };

}
