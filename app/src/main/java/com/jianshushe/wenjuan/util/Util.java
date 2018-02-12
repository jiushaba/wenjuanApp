package com.jianshushe.wenjuan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * 项目名：Auth
 * 包名 ： com.jianshushe.face.auth
 * 文件名：Util
 * 创建者：jiushaba
 * 创建时间：2017/11/29 0029上午 9:57
 * 描述： TODO
 */
public class Util {
    public static void postData(String file, String url) throws Exception {

        Bitmap bitmap=compressImage(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] buff = baos.toByteArray();
        HttpPostResponse(url, buff);
    }

    private static Bitmap compressImage(String path) {
        Bitmap bit = convertToBitmap(path,400,300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bit.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }



    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 使用Post发送二进制数据
     * @param url url地址
     * @return 请求结果
     */
    public static void HttpPostResponse(String url, byte[] data) {

        try {
            URLConnection urlConnection=getConnection(url);
            urlConnection.setDoOutput(true);
            OutputStream outputStream=urlConnection.getOutputStream();
            outputStream.write(data);
            urlConnection.getInputStream();
            outputStream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static URLConnection getConnection(String url) throws IOException {
        URL urls = new URL(url);
        URLConnection urlConnection=urls.openConnection();
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        urlConnection.setRequestProperty("Accept-Encoding","gzip,deflate");
        return urlConnection;
    }




}
