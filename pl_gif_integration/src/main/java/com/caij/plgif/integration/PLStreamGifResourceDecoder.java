package com.caij.plgif.integration;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.resource.gif.GifOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class PLStreamGifResourceDecoder implements ResourceDecoder<InputStream, PLGifDrawable> {

    private final List<ImageHeaderParser> parsers;
    private final ArrayPool byteArrayPool;

    private PLByteBufferGifResourceDecoder plByteBufferGifResourceDecoder;


    public PLStreamGifResourceDecoder(PLByteBufferGifResourceDecoder plByteBufferGifResourceDecoder,List<ImageHeaderParser> parsers, ArrayPool byteArrayPool) {
        this.plByteBufferGifResourceDecoder = plByteBufferGifResourceDecoder;
        this.parsers = parsers;
        this.byteArrayPool = byteArrayPool;
    }

    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return !options.get(GifOptions.DISABLE_ANIMATION)
                && ImageHeaderParserUtils.getType(parsers, source, byteArrayPool) == ImageHeaderParser.ImageType.GIF;
    }

    @Nullable
    @Override
    public Resource<PLGifDrawable> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        byte[] data = inputStreamToBytes(source);
        if (data == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        return plByteBufferGifResourceDecoder.decode(byteBuffer, width, height, options);
    }

    static byte[] inputStreamToBytes(InputStream is) {
        final int bufferSize = 16384;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(bufferSize);
        try {
            int nRead;
            byte[] data = new byte[bufferSize];
            while ((nRead = is.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            return null;
        }
        return buffer.toByteArray();
    }
}
