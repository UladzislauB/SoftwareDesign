package com.example.notes.utils

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.notes.R
import com.example.notes.models.Note

@BindingAdapter("titleWithNullSafety")
fun TextView.setTitleWithNullSafety(item: Note?) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("summaryBody")
fun TextView.setSummaryBody(item: Note?) {
    item?.let {
        text = getSummary(item.body)
    }
}

@BindingAdapter(value = ["titleDetail","isJustCreated"], requireAll = true)
fun EditText.setTitleDetail(item: Note?, isJustCreated: Boolean) {
    if (!isJustCreated) {
        item?.let {
            setText(item.title)
        }
    }
}

@BindingAdapter(value = ["bodyNoteDetail", "isJustCreated"], requireAll = true)
fun TextView.setBodyNoteDetail(item: Note?, isJustCreated: Boolean) {
    if (!isJustCreated) {
        item?.let {
            setText(item.body)
        }
    }
}

@BindingAdapter("colorBackground")
fun View.setColorBackground(item: Note?) {
    item?.let {
        when (it.color_number) {
            0 -> setBackgroundResource(R.color.white)
            1 -> setBackgroundResource(R.color.red_violet)
            2 -> setBackgroundResource(R.color.tea_rose)
            3 -> setBackgroundResource(R.color.ochre)
            4 -> setBackgroundResource(R.color.orange)
            5 -> setBackgroundResource(R.color.maize)
            6 -> setBackgroundResource(R.color.electric_lime)
            7 -> setBackgroundResource(R.color.aquamarine)
            8 -> setBackgroundResource(R.color.sky_blue)
            9 -> setBackgroundResource(R.color.blue_green)
            10 -> setBackgroundResource(R.color.lavender)
            11 -> setBackgroundResource(R.color.peach)
            12 -> setBackgroundResource(R.color.silver)
        }
    }
}

@BindingAdapter("drawableBackground")
fun View.setDrawableBackground(item: Note?) {
    item?.let {
        when (it.color_number) {
            0 -> setBackgroundResource(R.drawable.white_bg_item)
            1 -> setBackgroundResource(R.drawable.red_violet_bg_item)
            2 -> setBackgroundResource(R.drawable.tea_rose_bg_item)
            3 -> setBackgroundResource(R.drawable.ochre_bg_item)
            4 -> setBackgroundResource(R.drawable.orange_bg_item)
            5 -> setBackgroundResource(R.drawable.maize_bg_item)
            6 -> setBackgroundResource(R.drawable.electric_lime_bg_item)
            7 -> setBackgroundResource(R.drawable.aquamarine_bg_item)
            8 -> setBackgroundResource(R.drawable.sky_blue_bg_item)
            9 -> setBackgroundResource(R.drawable.blue_green_bg_item)
            10 -> setBackgroundResource(R.drawable.lavender_bg_item)
            11 -> setBackgroundResource(R.drawable.peach_bg_item)
            12 -> setBackgroundResource(R.drawable.silver_bg_item)
        }
    }
}


@BindingAdapter(value = ["tickText", "colorNumber"], requireAll = true)
fun TextView.setStartColor(item: Note?, number: Int) {
    item?.let {
        if (item.color_number == number) {
            text = "✔"
        }
    }
}