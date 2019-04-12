package com.caij.plgif.integration;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;

import java.security.MessageDigest;

public class PLGifDrawableTransform implements Transformation<PLGifDrawable> {

    @NonNull
    @Override
    public Resource<PLGifDrawable> transform(@NonNull Context context, @NonNull Resource<PLGifDrawable> resource, int outWidth, int outHeight) {
        return resource;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
