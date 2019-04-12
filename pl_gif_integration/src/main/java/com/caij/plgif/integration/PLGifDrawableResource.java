package com.caij.plgif.integration;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.resource.drawable.DrawableResource;


public class PLGifDrawableResource extends DrawableResource<PLGifDrawable> {

    public PLGifDrawableResource(PLGifDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<PLGifDrawable> getResourceClass() {
        return PLGifDrawable.class;
    }

    @Override
    public int getSize() {
        return (int) drawable.getAllocationByteCount();
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.recycle();
    }
}
