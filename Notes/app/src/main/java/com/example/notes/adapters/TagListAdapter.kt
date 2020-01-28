package com.example.notes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ListItemTagBinding
import com.example.notes.models.Tag
import java.util.*
import kotlin.collections.ArrayList

class TagListAdapter(
    private var context: Context,
    private var tagList: ArrayList<Tag>
) : RecyclerView.Adapter<TagListAdapter.ViewHolder>(), Filterable {

    private var tagListFiltered = tagList

    override fun getItemCount(): Int {
        return tagListFiltered.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tagListFiltered.get(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    tagListFiltered = tagList
                } else {
                    val filteredList = ArrayList<Tag>()
                    for (row in tagList) {
                        if (row.title.contains(charString as CharSequence, true)) {
                                filteredList.add(row)
                            }
                    }

                    tagListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = tagListFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                tagListFiltered = filterResults.values as ArrayList<Tag>
                notifyDataSetChanged()
            }
        }
    }

    fun updateList(newList: ArrayList<Tag>) {
        val diffResult = DiffUtil.calculateDiff(TagDiffCallback(this.tagListFiltered, newList))
        this.tagListFiltered.clear()
        this.tagListFiltered.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder private constructor(val binding: ListItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Tag
        ) {
            binding.tag = item
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


class TagDiffCallback(var newTags: ArrayList<Tag>, var oldTags: ArrayList<Tag>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTags.get(oldItemPosition).tagId == newTags.get(newItemPosition).tagId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTags[oldItemPosition] == newTags[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldTags.size
    }

    override fun getNewListSize(): Int {
        return newTags.size
    }


}