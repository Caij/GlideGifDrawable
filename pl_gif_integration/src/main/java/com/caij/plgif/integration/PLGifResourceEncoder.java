package com.caij.plgif.integration;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.ByteBufferUtil;

import java.io.File;
import java.io.IOException;

public class PLGifResourceEncoder implements ResourceEncoder<PLGifDrawable> {

    @NonNull
    @Override
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return EncodeStrategy.SOURCE;
    }

    @Override
    public boolean encode(@NonNull Resource<PLGifDrawable> data, @NonNull File file, @NonNull Options options) {
        PLGifDrawable drawable = data.get();
        boolean success = false;
        try {
            ByteBufferUtil.toFile(drawable.getBuffer(), file);
            success = true;
        } catch (IOException e) {

        }
        return success;
    }
}
