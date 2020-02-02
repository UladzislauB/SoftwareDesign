package com.example.notes.pretty_tag_adapter

import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.notes.models.Tag
import kotlinx.android.synthetic.main.pretty_row_tag.view.*

class MeasureHelper(
    private val adapter: PrettyTagAdapter,
    private val count: Int
) {

    companion object {
        /***
         * Number of the total spans that the layout manager is allowed to draw.
         */
        const val SPAN_COUNT = 42
    }

    private var measuredCount = 0

    /***
     * TagRowManager is responsible for adding and getting of the tags.
     */
    private val rowManager = TagRowManager()

    private var baseCell: Float = 0f

    fun measureBaseCell(width: Int) {
        baseCell = (width / SPAN_COUNT).toFloat()
    }

    fun shouldMeasure() = measuredCount != count

    fun getItems() = rowManager.getSortedTags()

    fun getSpans() = rowManager.getSortedSpans()

    private fun cellMeasured() {
        if (!adapter.measuringDone && !shouldMeasure())
            adapter.measuringDone = true
    }


    fun measure(holder: PrettyTagAdapter.ViewHolder, tag: Tag) {

        /** Get the ItemView and minimize it's height as small as possible
            to fit as many cells as it's possible in the screen. */
        val itemView = holder.itemView.apply {
            layoutParams.height = 1
        }

        val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                // Remove the observer to avoid multiple callbacks
                itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Include also the horizontal margin of the layout
                val marginTotal =
                    (itemView.rowTitle.layoutParams as ViewGroup.MarginLayoutParams).marginStart * 2

                val span = (itemView.rowTitle.width + marginTotal) / baseCell

                measuredCount++

                rowManager.add(span, tag)

                cellMeasured()
            }

        }

        // Observe for the view
        itemView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }
}