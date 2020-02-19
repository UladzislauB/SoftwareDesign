package com.example.yetanotherfeed.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.yetanotherfeed.R
import com.example.yetanotherfeed.models.Item
import com.example.yetanotherfeed.viewmodels.LoadingStatus
import java.text.DateFormat
import java.text.SimpleDateFormat


@BindingAdapter("titleFeed")
fun TextView.setTitleFeed(item: Item?) {
    item?.let {
        text = it.title
    }
}

@BindingAdapter("contentFeed")
fun TextView.setContentFeed(item: Item?) {
    item?.let {
        text = it.content
    }
}

@BindingAdapter("dateFeed")
fun TextView.setDataFeed(item: Item?) {
    item?.let {
        val pubDateMillis = (SimpleDateFormat("yyyy-MM-dd HH:mm:ss") as DateFormat)
            .parse(it.pubDate)?.time
        text = convertMillisToFormatted(pubDateMillis!!, context.resources)
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}