package com.caij.plgif.integration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.gif.GifOptions;
import com.bumptech.glide.util.Util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;


public class PLByteBufferGifResourceDecoder implements ResourceDecoder<ByteBuffer, PLGifDrawable> {

    private final List<ImageHeaderParser> parsers;
    private final GifHeaderParserPool parserPool = PARSER_POOL;
    private final BitmapPool bitmapPool;

    private static final GifHeaderParserPool PARSER_POOL = new GifHeaderParserPool();

    public PLByteBufferGifResourceDecoder(List<ImageHeaderParser> parsers, BitmapPool bitmapPool) {
        this.parsers = parsers;
        this.bitmapPool = bitmapPool;
    }

    @Override
    public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
        return !options.get(GifOptions.DISABLE_ANIMATION)
                && ImageHeaderParserUtils.getType(parsers, source) == ImageHeaderParser.ImageType.GIF;
    }

    @Nullable
    @Override
    public Resource<PLGifDrawable> decode(@NonNull ByteBuffer source, int width, int height, @NonNull Options options) throws IOException {
        final GifHeaderParser parser = parserPool.obtain(source);

        final GifHeader header = parser.parseHeader();

//        Bitmap.Config config = options.get(GifOptions.DECODE_FORMAT) == DecodeFormat.PREFER_RGB_565
//                ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;

        int sampleSize = getSampleSize(header, width, height);

        try {
//            Bitmap rebitmap = bitmapPool.getDirty(width, height, config);
            pl.droidsonroids.gif.GifOptions gifOptions = new pl.droidsonroids.gif.GifOptions();
            gifOptions.setInSampleSize(sampleSize);
            PLGifDrawable plGifDrawable = new PLGifDrawable(source, gifOptions);
            return new PLGifDrawableResource(plGifDrawable);
        } finally {
            parserPool.release(parser);
        }
    }



    private static int getSampleSize(GifHeader gifHeader, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(gifHeader.getHeight() / targetHeight,
                gifHeader.getWidth() / targetWidth);
        int powerOfTwoSampleSize = exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize);
        // Although functionally equivalent to 0 for BitmapFactory, 1 is a safer default for our code
        // than 0.
        int sampleSize = Math.max(1, powerOfTwoSampleSize);
        return sampleSize;
    }

    static class GifHeaderParserPool {
        private final Queue<GifHeaderParser> pool = Util.createQueue(0);

        synchronized GifHeaderParser obtain(ByteBuffer buffer) {
            GifHeaderParser result = pool.poll();
            if (result == null) {
                result = new GifHeaderParser();
            }
            return result.setData(buffer);
        }

        synchronized void release(GifHeaderParser parser) {
            parser.clear();
            pool.offer(parser);
        }
    }
}
