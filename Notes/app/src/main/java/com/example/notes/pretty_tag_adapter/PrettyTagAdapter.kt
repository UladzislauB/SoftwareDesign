package com.example.notes.pretty_tag_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.models.Tag
import kotlinx.android.synthetic.main.pretty_row_tag.view.*
import kotlin.properties.Delegates

class PrettyTagAdapter(
    private var tagList: MutableList<Tag>
) : RecyclerView.Adapter<PrettyTagAdapter.ViewHolder>() {

    private var recyclerView: RecyclerView? = null

    /**
     * Is RecyclerView done measuring
     */
    private var ready = false

    private val measureHelper = MeasureHelper(this, tagList.size)

    /***
     * Determines when the measuring of all the cells is done.
     * If the newVal is true the adapter should be updated.
     */
    var measuringDone by Delegates.observable(false) { _, _, newVal ->
        if (newVal)
            update()
    }

    private fun update() {
        recyclerView ?: return

        recyclerView?.apply {

            visibility = View.VISIBLE

            tagList = measureHelper.getItems()

            layoutManager = MultipleSpanGridLayoutManager(
                context,
                MeasureHelper.SPAN_COUNT,
                measureHelper.getSpans()
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pretty_row_tag,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tagList[position]

        val shouldMeasure = measureHelper.shouldMeasure()

        holder.setData(tag, shouldMeasure)

        if (shouldMeasure)
            measureHelper.measure(holder, tag)
    }

    /***
     * When recyclerView is attached measure the width and calculate the baseCell on measureHelper.
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView.apply {

            visibility = View.INVISIBLE

            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    measureHelper.measureBaseCell(recyclerView.width)

                    ready = true

                    // NotifyDataSet because itemCount value needs to update
                    notifyDataSetChanged()

                }
            })
        }
    }

    override fun getItemCount() = if (ready) tagList.size else 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(tag: Tag, shouldMeasure: Boolean) {
            itemView.rowTitle.apply {
                text = tag.title
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            }

            /** if the measuring is done set the width to fill the whole cell to avoid unwanted
            empty spaces between the cells */
            if (!shouldMeasure)
                itemView.rowTitle.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT

        }
    }
}