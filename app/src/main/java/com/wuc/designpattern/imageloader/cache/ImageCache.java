package com.wuc.designpattern.imageloader.cache;

import android.graphics.Bitmap;

/**
 * @author: wuc
 * @date: 2024/4/27
 * @desc: 图片缓存接口
 */
public interface ImageCache {

    Bitmap get(String url);

    void put(String url, Bitmap bitmap);
}
