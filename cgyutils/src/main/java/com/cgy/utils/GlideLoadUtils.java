package com.cgy.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
 */
public class GlideLoadUtils {

    //region declare

    private String TAG = "ImageLoader";

    /**
     * 内部类单例模式
     */
    public GlideLoadUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    private void commonNormalDeal(Context context, String url, RequestOptions options, ImageView imageView) {
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    //endregion

    //region default method

    public void loadUrlTopRound(Context context, String url, ImageView imageView, int default_image) {
        loadUrlTopRound(context, url, imageView, default_image, 5);
    }

    public void loadUrlTopRoundCrop(Context context, String url, final ImageView imageView, int default_image) {
        loadUrlTopRoundCrop(context, url, imageView, default_image, 5);
    }

    public void loadUrlRoundCrop(Context context, String url, final ImageView imageView, int default_image) {
        loadUrlRoundCrop(context, url, imageView, default_image, 20);
    }

    public void loadUrlRound(Context context, String url, final ImageView imageView, int default_image) {
        loadUrlRound(context, url, imageView, default_image, 20);
    }

    public void loadResRound(Context context, int res, final ImageView imageView) {
        loadResRound(context, res, imageView, 6);
    }

    //endregion

    //region glide method

    public void loadUrl(Context context, String url, final ImageView imageView, int default_image) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = new RequestOptions()
                .error(default_image)
                .placeholder(default_image);
        commonNormalDeal(context, url, options, imageView);
    }

    public void loadUrlTopRound(Context context, String url, ImageView imageView, int default_image, int roundingRadius) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideRoundedCornersTransform(roundingRadius, GlideRoundedCornersTransform.CornerType.TOP))
                .override(300, 300)
                .error(default_image)
                .placeholder(default_image);
        commonNormalDeal(context, url, options, imageView);
    }

    public void loadUrlTopRoundCrop(Context context, String url, final ImageView imageView, int default_image, int roundingRadius) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = new RequestOptions()
                .transforms(new CenterCrop(), new GlideRoundedCornersTransform(roundingRadius, GlideRoundedCornersTransform.CornerType.TOP))
                .override(300, 300)
                .error(default_image)
                .placeholder(default_image);
        commonNormalDeal(context, url, options, imageView);
    }

    public void loadUrlRoundCrop(Context context, String url, final ImageView imageView, int default_image, int roundingRadius) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = new RequestOptions()
                .transforms(new CenterCrop(), new RoundedCorners(roundingRadius))
                .override(300, 300)
                .error(default_image)
                .placeholder(default_image);
        commonNormalDeal(context, url, options, imageView);
    }

    public void loadUrlRound(Context context, String url, final ImageView imageView, int default_image, int roundingRadius) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(roundingRadius))
                .override(300, 300)
                .error(default_image)
                .placeholder(default_image);
        commonNormalDeal(context, url, options, imageView);
    }

    public void loadResRound(Context context, int res, final ImageView imageView, int roundingRadius) {
        if (context == null) {
            Log.i(TAG, "Picture loading failed,activity is null");
            return;
        }
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(roundingRadius));
        Glide.with(context).load(res)
                .apply(options)
                .into(imageView);
    }

    //endregion

}