package com.example.notes.utils

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
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