package com.sample.weatherinfo.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *  Implement Binding Adapter to download image/icon asset from https://api.openweathermap.org/
 *  Using Glide library
 */

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
}