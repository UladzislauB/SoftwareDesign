package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.Nullable

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ListItemTagBinding
import com.example.notes.models.JoinNoteTag
import com.example.notes.models.Tag


class TagListAdapter(
    private var tagList: List<Tag>,
    private var joinList: List<Tag>,
    val clickListener: (tagId: Long) -> Unit
) : RecyclerView.Adapter<TagListAdapter.ViewHolder>(), Filterable {

    private var tagListFiltered = tagList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tagListFiltered[position]
        val realClickListener = TagListListener(clickListener, holder.binding)
        holder.bind(item, realClickListener)
        holder.binding.tagCheckbox.isChecked = joinList.contains(item)
    }

    override fun getItemCount(): Int {
        return tagListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filterResults = FilterResults()
                if (charString.isEmpty()) {
                    filterResults.values = tagList.toMutableList()
                    return filterResults
                } else {
                    val filteredList: ArrayList<Tag> = ArrayList()
                    for (row in tagList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.title.contains(charSequence, true)) {
                            filteredList.add(row)
                        }
                    }

                    filterResults.values = filteredList
                    return filterResults
                }
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                updateTagListItems(filterResults.values as ArrayList<Tag>)
            }
        }
    }

    fun updateTagListItems(newTagList: List<Tag>) {
        val diffCallback = TagDiffCallback(tagListFiltered, newTagList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        (tagListFiltered as ArrayList<Tag>).clear()
        (tagListFiltered as ArrayList<Tag>).addAll(newTagList)

        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder private constructor(val binding: ListItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Tag, clickListener: TagListListener
        ) {
            binding.tag = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTagBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class TagDiffCallback(
    private val oldTagList: List<Tag>,
    private val newTagList: List<Tag>
) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTagList[oldItemPosition].tagId == newTagList[newItemPosition].tagId
    }

    override fun getOldListSize(): Int {
        return oldTagList.size
    }

    override fun getNewListSize(): Int {
        return newTagList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTagList[oldItemPosition] == newTagList[newItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }

}


class TagListListener(val clickListener: (tagId: Long) -> Unit, val binding: ListItemTagBinding) {
    fun onClick(tag: Tag) {
        clickListener(tag.tagId)
        binding.tagCheckbox.toggle()
    }
}