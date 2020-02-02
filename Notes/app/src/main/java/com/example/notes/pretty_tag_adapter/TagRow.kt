package com.example.notes.pretty_tag_adapter

import com.example.notes.models.Tag
import kotlin.math.ceil

class TagRow {

    /***
     * The available spans that the current row has.
     */
    var freeSpans = MeasureHelper.SPAN_COUNT

    /***
     * List of the Tags hosted in the current row.
     */
    val tagList = mutableListOf<Tag>()

    /***
     * List of the spans required by each holder hosted in the current row.
     */
    val spanList = mutableListOf<Int>()


    fun addTag(spanRequired: Float, tag: Tag) : Boolean {

        if (spanRequired < freeSpans)
            if (tagList.add(tag)) {

                val spanRequiredInt = ceil(spanRequired).toInt()

                spanList.add(spanRequiredInt)

                freeSpans -= spanRequiredInt

                return true
            }
        return false
    }
}