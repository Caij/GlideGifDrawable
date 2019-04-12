package com.caij.glidegifdrawable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caij.plgif.integration.PLGifDrawable;
import com.caij.plgif.integration.PLGifDrawableTransform;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image);

        GlideApp.with(this).load("https://wx1.sinaimg.cn/large/005FMDaAly1fziw980dvmg30ds06pkcl.gif")
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .optionalTransform(PLGifDrawable.class, new PLGifDrawableTransform())
                .into(imageView);
    }
}
