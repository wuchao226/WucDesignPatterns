package com.wuc.designpattern.imageloader.refactor;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wuc.designpattern.imageloader.cache.ImageCache;
import com.wuc.designpattern.imageloader.cache.MemoryCache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wuc
 * @date: 2024/4/27
 * @desc: 图片加载类
 */
public class ImageLoader {

    ImageCache mCache = new MemoryCache();
    /**
     * 等待加载的列表
     */
    List<String> mWattingList = new ArrayList<String>();
    /**
     * 设置缓存策略
     *
     * @param cache 缓存
     */
    public void setImageCache(ImageCache cache) {
        mCache = cache;
    }

    /**
     * 显示图片
     *
     * @param url       图片url
     * @param imageView 要显示图片的ImageView
     */
    public void displayImage(String url, ImageView imageView) {
        Bitmap bmp = mCache.get(url);
        if (bmp == null) {
            downloadImageAsync(url, imageView);
        } else {
            imageView.setImageBitmap(bmp);
        }
    }

    /**
     * 下载图片 ( 后台下载 )
     *
     * @param url       图片url
     * @param imageView 要显示图片的ImageView
     */
    private void downloadImageAsync(String url, ImageView imageView) {
        Bitmap bmp = downloadBitmap(url);
        mCache.put(url, bmp);
        imageView.setImageBitmap(bmp);
    }

    private Bitmap downloadBitmap(String url) {
        return null/* 从网络中获取图片 */;
    }



}
