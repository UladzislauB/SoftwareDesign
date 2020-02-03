package com.example.notes.fragments


import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notes.R
import com.example.notes.adapters.HorizontalTagAdapter
import com.example.notes.adapters.HorizontalTagListener
import com.example.notes.adapters.NoteListAdapter
import com.example.notes.adapters.NoteListener
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteMainListBinding
import com.example.notes.models.Tag
import com.example.notes.viewmodels.NoteMainListViewModel
import com.example.notes.viewmodels.NoteMainListViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class NoteMainListFragment : Fragment() {

    private lateinit var noteMainListViewModel: NoteMainListViewModel

    private lateinit var adapter: NoteListAdapter

    private lateinit var binding: FragmentNoteMainListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_main_list, container, false)

        val application = requireNotNull(this.activity).application
        // Create an instance of ViewModelFactory
        val database = NotesDatabase.getInstance(application)
        val viewModelFactory = NoteMainListViewModelFactory(
            database.noteDatabaseDAO,
            database.tagDatabaseDao,
            database.joinNoteTagDAO
        )


        // Reference to the ViewModel
        noteMainListViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(NoteMainListViewModel::class.java)


        // Setting ViewModel to the layout
        binding.viewModel = noteMainListViewModel

        binding.setLifecycleOwner(this)


        noteMainListViewModel.navigateToNoteDetail.observe(this, Observer { noteId ->
            noteId?.let {
                this.findNavController()
                    .navigate(
                        NoteMainListFragmentDirections.actionNoteMainListFragmentToNoteDetailFragment(
                            noteId, noteMainListViewModel.tappedCreate
                        )
                    )
                noteMainListViewModel.finishCreateTapping()
                noteMainListViewModel.doneNavigating()
            }
        })




        val horizontalTagAdapter = HorizontalTagAdapter { tagId ->
            Toast.makeText(this.context, tagId.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.horizontalTagList.apply {
            adapter = horizontalTagAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        }

        noteMainListViewModel.tags.observe(this, Observer {
            it?.let {
                val list = mutableListOf<Tag>(Tag(0, "All"))
                list.addAll(it)
                horizontalTagAdapter.submitList(list)
            }
        })

        // Instantiating RecyclerView with all notes
        var isReady = false

        noteMainListViewModel.notes.observe(this, Observer {
            it?.let {
                if (!isReady) {
                    adapter = NoteListAdapter(this.context!!, it, NoteListener { noteId ->
                        noteMainListViewModel.onNoteClicked(noteId)
                    })
                    binding.notesList.adapter = adapter
                } else
                adapter.updateNoteListItems(it)
            }
        })

        // Here we define whether we should use list or grid
        var spanCount: Int
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 1
        } else {
            spanCount = 3
        }

        val manager = GridLayoutManager(activity, spanCount)
        binding.notesList.layoutManager = manager


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_note_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_sort_title -> {
                noteMainListViewModel.onSortByTitle()
                adapter.updateNoteListItems(noteMainListViewModel.notes.value!!)
            }
            R.id.action_sort_date_change -> {
                noteMainListViewModel.onSortByDateChange()
                adapter.updateNoteListItems(noteMainListViewModel.notes.value!!)
            }
            R.id.action_sort_id -> {
                noteMainListViewModel.onNormalOrder()
                adapter.updateNoteListItems(noteMainListViewModel.notes.value!!)
            }
            R.id.action_delete_all_notes -> {
                noteMainListViewModel.onClear()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
