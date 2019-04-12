## GlideGifDrawable


GlideGifDrawable is a [Glide](https://github.com/bumptech/glide) integration library for decoding and displaying gif images on Android platforms. It is based on [android-gif-drawable](https://github.com/koral--/android-gif-drawable) project.


```gradle
implementation "com.zlc.glide:webpdecoder:1.4.${GLIDE_VERSION}"
```

## Usage

Basic usage see [Glide](https://github.com/bumptech/glide) API [documents](https://bumptech.github.io/glide/)

```
 GlideApp.with(this).load("https://wx1.sinaimg.cn/large/005FMDaAly1fziw980dvmg30ds06pkcl.gif")
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .optionalTransform(PLGifDrawable.class, new PLGifDrawableTransform())
                .into(imageView);
```

## License

MIT License

