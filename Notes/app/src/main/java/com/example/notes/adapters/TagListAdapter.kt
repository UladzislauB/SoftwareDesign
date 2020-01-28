package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ListItemTagBinding
import com.example.notes.models.Tag


class TagListAdapter : ListAdapter<Tag, TagListAdapter.ViewHolder>(TagDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

//  override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val charString = charSequence.toString()
//                if (charString.isEmpty()) {
//                    tagListFiltered = tagList
//                } else {
//                    val filteredList = ArrayList<Tag>()
//                    for (row in tagList) {
//                        if (row.title.contains(charString as CharSequence, true)) {
//                            filteredList.add(row)
//                        }
//                    }
//
//                    tagListFiltered = filteredList
//                }
//
//                val filterResults = FilterResults()
//                filterResults.values = tagListFiltered
//                return filterResults
//            }
//
//            override fun publishResults(
//                charSequence: CharSequence,
//                filterResults: FilterResults
//            ) {
//                tagListFiltered = filterResults.values as MutableList<Tag>
//                notifyDataSetChanged()
//            }
//        }
//    }

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


class TagDiffCallback : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem.tagId == newItem.tagId
    }

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
        return oldItem == newItem
    }


}