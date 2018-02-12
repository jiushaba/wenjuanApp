package com.jianshushe.wenjuan.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CameraActivity extends Activity{



    private ImageView imageView;
    private boolean canTake = false;
    //Camera object
    Camera mCamera;
    //Preview surface
    SurfaceView surfaceView;
    //Preview surface handle for callback
    SurfaceHolder surfaceHolder;
    boolean previewing;
    int mCurrentCamIndex = 0;
    public CameraActivity(SurfaceView surfaceView ,ImageView imageView ,Activity activity) {
        this.surfaceView = surfaceView;
        this.imageView = imageView;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceViewCallback());
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    /**
     * 拍照
     */
    public void setCanTake(){
        canTake = true;
    }


    private final class SurfaceViewCallback implements android.view.SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
            if (previewing) {
                mCamera.stopPreview();
                previewing = false;
            }
            try {
                mCamera.setPreviewDisplay(arg0);
                mCamera.startPreview();
                previewing = true;
                setCameraDisplayOrientation(CameraActivity.this, mCurrentCamIndex, mCamera);
            } catch (Exception e) {
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
         mCamera = Camera.open();
            //change to front camera
            mCamera = openFrontFacingCameraGingerbread();
            // get Camera parameters
            Camera.Parameters params = mCamera.getParameters();

            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                // Autofocus mode is supported
            }
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Log.e("stuart", "onPreviewFrame " + canTake);
                    if (canTake) {
                        getSurfacePic(bytes, camera, "hahahaah");
                        canTake = false;
                    }
                }
            });
        }
        public void surfaceDestroyed(SurfaceHolder holder) {
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                previewing = false;
            } catch (Exception e) {
                Log.e("相机异常", e.getLocalizedMessage());
            }

        }


    }

    private static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        //degrees the angle that the picture will be rotated clockwise. Valid values are 0, 90, 180, and 270.
        //The starting position is 0 (landscape).
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                    mCurrentCamIndex = camIdx;
                } catch (RuntimeException e) {
                  //  Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }
    public void getSurfacePic(byte[] data, Camera camera, String name) {
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
        if (image != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);

            Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

            //**********************
            //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
            rotateMyBitmap(bmp, name);
            //**********************************


        }
    }

    public void rotateMyBitmap(Bitmap bmp, String name) {
        //*****旋转一下
        Matrix matrix = new Matrix();
        matrix.postRotate(270);

        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);

        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        //*******显示一下
        imageView.setImageBitmap(nbmp2);
        compressImage(nbmp2);
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        saveBitmapTofile(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }





    /**
     * 保存图片到指定文件夹
     *
     * @param bmp
     * @return
     */
    private boolean saveBitmapTofile(byte[] bmp) {
      /*  String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString()
                + File.separator
                + "PicTest_" + System.currentTimeMillis() + ".jpg";*/
        String fileName = "/sdcard/wenjuan/"+System.currentTimeMillis() + ".jpg";
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bos.write(bmp);
            bos.flush();
            bos.close();
            //   Toast.makeText(CameraActivity.this, "[Test] Photo take and store in" + file.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // Toast.makeText(CameraActivity.this, "Picture Failed" + e.toString(),Toast.LENGTH_LONG).show();
        }
        return true;
    }





}
