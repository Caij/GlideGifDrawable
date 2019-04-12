package com.caij.plgif.integration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifOptions;
import pl.droidsonroids.gif.InputSource;

public class PLGifDrawable extends GifDrawable {

    private ByteBuffer byteBuffer;


    public PLGifDrawable(@NonNull ByteBuffer buffer, GifOptions gifOptions) throws IOException {
        this(new InputSource.DirectByteBufferSource(buffer), null, null, true, gifOptions);
        byteBuffer = buffer;
    }

    private PLGifDrawable(@NonNull InputSource inputSource, @Nullable GifDrawable oldDrawable, @Nullable ScheduledThreadPoolExecutor executor,
                          boolean isRenderingTriggeredOnDraw, @NonNull GifOptions options) throws IOException {
        super(inputSource, oldDrawable, executor, isRenderingTriggeredOnDraw, options);

    }

    public ByteBuffer getBuffer() {
        return byteBuffer;
    }
}
