package com.example.notes.fragments


import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.notes.R
import com.example.notes.adapters.NoteListAdapter
import com.example.notes.adapters.NoteListener
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteMainListBinding
import com.example.notes.viewmodels.NoteMainListViewModel
import com.example.notes.viewmodels.NoteMainListViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class NoteMainListFragment : Fragment() {

    private lateinit var noteMainListViewModel: NoteMainListViewModel

    private lateinit var adapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNoteMainListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_note_main_list, container, false)

        val application = requireNotNull(this.activity).application
        // Create an instance of ViewModelFactory
        val dataSource = NotesDatabase.getInstance(application).noteDatabaseDAO
        val viewModelFactory = NoteMainListViewModelFactory(dataSource, application)


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


        // Instantiating RecyclerView with all notes
        adapter = NoteListAdapter(NoteListener { noteId ->
            Toast.makeText(context, "${noteId}", Toast.LENGTH_SHORT).show()
            noteMainListViewModel.onNoteClicked(noteId)
        })
        binding.notesList.adapter = adapter



        noteMainListViewModel.notes.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
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
        inflater?.inflate(R.menu.menu_fragment_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_sort_title -> {
                noteMainListViewModel.onSortByTitle()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
