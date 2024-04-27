package com.wuc.designpattern.imageloader.cache;

import android.graphics.Bitmap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: wuc
 * @date: 2024/4/27
 * @desc: 本地(sd卡)图片缓存
 */
public class DiskCache implements ImageCache {
    @Override
    public Bitmap get(String url) {
        return null/* 从本地文件中获取该图片 */;
    }

    private String imageUrl2MD5(String imageUrl) {
        // 对imgeUrl进行md5运算, 省略
        String md5 = imageUrl;
        return md5;
    }

    @Override
    public void put(String url, Bitmap bmp) {
        // 将Bitmap写入文件中
        FileOutputStream fos = null;
        try {
            // 构建图片的存储路径 ( 省略了对url取md5)
            fos = new FileOutputStream("sdcard/cache/" + imageUrl2MD5(url));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
