package com.example.akiraito.codechallenge.extention

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * 画像の読み込みをしてImageViewに挿入します
 *
 * @param url 画像のURL(gif画像でも可)
 */
@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

/**
 * 画像のUriを読み込みImageViewに挿入します
 *
 * @param url 画像のURL(gif画像でも可)
 */
fun ImageView.loadImage(url: Uri?) {
    Glide.with(this.context).load(url).into(this)
}

/**
 * 画像のUriを読み込みImageViewに挿入します
 *
 * @param imageRes 画像のresource(gif画像でも可)
 */
fun ImageView.loadImage(imageRes: Int?) {
    Glide.with(this.context).load(imageRes).into(this)
}

/**
 * 画像のBitmapを読み込みImageViewに挿入します
 *
 * @param imageRes 画像のresource(gif画像でも可)
 */
fun ImageView.loadImage(imageRes: Drawable?) {
    Glide.with(this.context).load(imageRes).into(this)
}
