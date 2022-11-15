package com.cgy.chengy.demofactory.activity

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.cgy.chengy.demofactory.R
import com.cgy.chengy.demofactory.app.BaseActivity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

class TvImageBannerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_image_banner)
        initView()
    }

    private fun initView() {
        val viewBanner = findViewById<Banner>(R.id.view_banner)
        viewBanner.setBannerStyle(BannerConfig.NOT_INDICATOR)
        viewBanner.setDelayTime(10000)
        viewBanner.setImageLoader(MyImageLoader())
        viewBanner.setOffscreenPageLimit(3)
        viewBanner.setImages(getImages())
        viewBanner.start()
    }

    class MyImageLoader : ImageLoader() {
        override fun displayImage(context: Context, path: Any, imageView: ImageView) {
            imageView.setImageResource(path as Int)
        }

        override fun createImageView(context: Context): ImageView {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return imageView
        }
    }

    private fun getImages(): ArrayList<Int> {
        val result = arrayListOf<Int>()
        return result
    }

}


































