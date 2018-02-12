package com.jianshushe.wenjuan.fragment.fromFragment;


import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jianshushe.wenjuan.ui.FromFragment;
import com.jianshushe.wenjuan.R;
import com.jianshushe.wenjuan.entity.Questions;
import com.jianshushe.wenjuan.model.answers;
import com.jianshushe.wenjuan.util.CameraManager;

/**
 * 文本Fragment
 */
@SuppressLint("ValidFragment")
public class TextFragment extends FromFragment {
    /**
     * 定义前置有关的参数
     */
    private Camera mFrontCamera;
    CameraManager frontCameraManager;

    Questions list;
    TextView problem;
    EditText text;
    int i;
    String uuidStr;
    public TextFragment(  Questions list, int i, String uuidStr, Camera mFrontCamera, CameraManager frontCameraManage) {

        this.list = list;
        this.i = i;
        this.uuidStr = uuidStr;
        this.mFrontCamera = mFrontCamera;
        this.frontCameraManager = frontCameraManage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initData() {
        problem.setText(i+"、"+list.getName());
    }

    private void initView(View view) {
        problem = view.findViewById(R.id.problem);
        text = view.findViewById(R.id.text);
    }


    @Override
    public boolean upData() {
        boolean isFlash = false;
        String res = text.getText().toString().trim();
        if (list.getMust() == 0) {
            setData(list.getQuestionId(),res, uuidStr);
            isFlash = true;
        } else if (list.getMust() == 1) {
            if (res.equals("")) {
                isFlash = false;
            } else {
                setData(list.getQuestionId(),res, uuidStr);
                isFlash = true;
            }
        }
        if (takeFrontPhoto() && isFlash) {
            return true;
        } else {
            return false;
        }
    }

    private void  setData(int questionId,String answer,String formId) {
        answers answers = new answers();
        answers.setFormId(formId);
        answers.setAnswer(answer);
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
                if ( frontCameraManager.openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
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
