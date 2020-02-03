package com.example.notes.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ListItemNoteBinding
import com.example.notes.models.Note
import com.example.notes.models.Tag
import com.example.notes.pretty_tag_adapter.PrettyTagAdapter

class NoteListAdapter(
    val context: Context,
    private var noteList: List<Note>,
    val clickListener: NoteListener
    ) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>(), Filterable {


    private var noteListFiltered = noteList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = noteListFiltered[position]
        holder.bind(item, clickListener)
        Log.i("NoteListAdapter/I", position.toString() + item.title)
//        holder.binding.itemTagList.apply {
//            adapter = PrettyTagAdapter(mutableListOf(Tag(title = "anime"), Tag(title = "Japan"), Tag(title = "+3")))
//            layoutManager = LinearLayoutManager(context)
//        }
    }

    override fun getItemCount(): Int {
        return noteListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filterResults = FilterResults()

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {

            }

        }
    }

    fun updateNoteListItems(newNoteList: List<Note>) {
        val diffCallback = NoteDiffCallback(noteListFiltered, newNoteList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        (noteListFiltered as ArrayList<Note>).clear()
        (noteListFiltered as ArrayList<Note>).addAll(newNoteList)

        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder private constructor(val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Note,
            clickListener: NoteListener
        ) {
            binding.note = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemNoteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class NoteDiffCallback(
    private val oldNoteList: List<Note>,
    private val newNoteList: List<Note>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].noteId == newNoteList[newItemPosition].noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition] == newNoteList[newItemPosition]
    }

    override fun getOldListSize(): Int {
       return oldNoteList.size
    }

    override fun getNewListSize(): Int {
        return newNoteList.size
    }
}


class NoteListener(val clickListener: (noteId: Long) -> Unit) {
    fun onClick(note: Note) = clickListener(note.noteId)
}