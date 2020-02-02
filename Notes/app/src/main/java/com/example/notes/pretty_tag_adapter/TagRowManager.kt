package com.example.notes.pretty_tag_adapter

import com.example.notes.models.Tag

class TagRowManager {

    private val rowList = mutableListOf<TagRow>()
        .apply {

            /**
             *  Manually add the first empty row
             */
            add(TagRow())
        }


    fun add(spanRequired: Float, tag: Tag) {

        for (i in 0..rowList.size) {

            val tagRow = rowList[i]

            if (tagRow.addTag(spanRequired, tag))
                break

            if (i == rowList.lastIndex)
                rowList.add(TagRow())
        }
    }

    fun getSortedSpans() =
        mutableListOf<Int>().apply {
            rowList.forEach {
                addAll(it.spanList)
            }
        }

    fun getSortedTags() =
        mutableListOf<Tag>().apply {
            rowList.forEach {
                addAll(it.tagList)
            }
        }
}