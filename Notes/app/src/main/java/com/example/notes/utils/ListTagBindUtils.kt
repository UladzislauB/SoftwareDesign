package com.example.notes.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.notes.models.Tag

@BindingAdapter("tagTitle")
fun TextView.setTagTitle(item: Tag?) {
    item?.let {
        text = item.title
    }
}