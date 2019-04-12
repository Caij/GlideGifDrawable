package com.caij.glidegifdrawable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.caij.plgif.integration.PLByteBufferGifResourceDecoder;
import com.caij.plgif.integration.PLGifDrawable;
import com.caij.plgif.integration.PLGifResourceEncoder;
import com.caij.plgif.integration.PLStreamGifResourceDecoder;

import java.io.InputStream;
import java.nio.ByteBuffer;



/**
 * Created by Caij on 15/8/14.
 */
@GlideModule
public class TestGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

    }

    @Override
    public void registerComponents(@NonNull final Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        PLByteBufferGifResourceDecoder plByteBufferGifResourceDecoder = new PLByteBufferGifResourceDecoder(glide.getRegistry().getImageHeaderParsers(),
                glide.getBitmapPool());
        PLStreamGifResourceDecoder plStreamGifResourceDecoder = new PLStreamGifResourceDecoder(plByteBufferGifResourceDecoder,
                glide.getRegistry().getImageHeaderParsers(), glide.getArrayPool());
        registry.prepend(Registry.BUCKET_GIF, InputStream.class, PLGifDrawable.class, plStreamGifResourceDecoder)
                .prepend(Registry.BUCKET_GIF, ByteBuffer.class, PLGifDrawable.class, plByteBufferGifResourceDecoder)
        .prepend(PLGifDrawable.class, new PLGifResourceEncoder());
    }
}
