package com.stx.xhb.enjoylife.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.chrisbanes.photoview.OnViewTapListener
import com.github.chrisbanes.photoview.PhotoView
import com.stx.xhb.enjoylife.config.GlideApp
import java.util.ArrayList

/**
 * @author: xiaohaibin.
 * @time: 2018/7/11
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
class PhotoViewPagerAdapter(context: Context, imageList: ArrayList<String>):PagerAdapter() {

    private val context: Context
    private val imageList: ArrayList<String>

    init {
        this.context = context
        this.imageList = imageList
    }
    private var mImageLayoutListener: onImageLayoutListener? = null

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(context)
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER)
        val imgUrl = imageList[position]
        GlideApp.with(context)
                .load(imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(photoView)

        photoView.setOnViewTapListener(OnViewTapListener { view, v, v1 ->
            if (mImageLayoutListener != null) {
                mImageLayoutListener!!.setOnImageOnClik()
            }
        })

        photoView.setOnLongClickListener(View.OnLongClickListener {
            if (mImageLayoutListener != null) {
                mImageLayoutListener!!.setLongClick(imgUrl)
            }
            true
        })
        container.addView(photoView)
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setOnClickListener(onClickListener: onImageLayoutListener) {
        mImageLayoutListener = onClickListener
    }

    interface onImageLayoutListener {

        fun setOnImageOnClik()

        fun setLongClick(url: String)

    }
}