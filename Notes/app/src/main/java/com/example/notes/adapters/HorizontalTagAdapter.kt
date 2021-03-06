package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.TopHorizontalItemTagBinding
import com.example.notes.models.Tag

class HorizontalTagAdapter(
    val lifecycleOwner: LifecycleOwner,
    val clickListener: (tagId: Long) -> Unit
) : ListAdapter<Tag, HorizontalTagAdapter.ViewHolder>(HorizontalTagDiffCallback()) {

    companion object {
        var clicked_tag_id = MutableLiveData<Long>()
    }

    init {
        clicked_tag_id.value = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
        clicked_tag_id.observe(lifecycleOwner, Observer {
            it?.let{
                if (it == item.tagId)
                    holder.binding.horizontalTagItem.setBackgroundResource(R.drawable.tag_horizontal_active)
                else holder.binding.horizontalTagItem.setBackgroundResource(R.drawable.tag_cornered_main_page)
            }
        })
    }


    class ViewHolder private constructor(
        val binding: TopHorizontalItemTagBinding,
        val clickListener: (tagId: Long) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Tag
        ) {
            binding.item = item
            binding.clickListener = HorizontalTagListener(clickListener, binding)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, clickListener: (tagId: Long) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TopHorizontalItemTagBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, clickListener)
            }
        }
    }
}


class HorizontalTagDiffCallback : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.tagId == newItem.tagId
    }

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem == newItem
    }

}


class HorizontalTagListener(
    val clickListener: (tagId: Long) -> Unit,
    val binding: TopHorizontalItemTagBinding
) {
    fun onClick(tag: Tag) {
        clickListener(tag.tagId)
        HorizontalTagAdapter.clicked_tag_id.value = tag.tagId
    }
}