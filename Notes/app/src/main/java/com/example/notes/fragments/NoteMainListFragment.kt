package com.example.notes.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.notes.R
import com.example.notes.adapters.NoteListAdapter
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteMainListBinding
import com.example.notes.viewmodels.NoteMainListViewModel
import com.example.notes.viewmodels.NoteMainListViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NoteMainListFragment : Fragment() {

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
        val noteMainListViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(NoteMainListViewModel::class.java)


        // Setting ViewModel to the layout
        binding.viewModel = noteMainListViewModel

        binding.setLifecycleOwner(this)


        noteMainListViewModel.navigateToNoteDetail.observe(this, Observer { note ->
            note?.let {
                this.findNavController()
                    .navigate(
                        NoteMainListFragmentDirections.actionNoteMainListFragmentToNoteDetailFragment(
                            note.noteId, noteMainListViewModel.tappedCreate
                        )
                    )
                noteMainListViewModel.finishCreateTapping()
                noteMainListViewModel.doneNavigating()
            }
        })


        // Instantiating RecyclerView with all notes
        val adapter = NoteListAdapter()
        binding.notesList.adapter = adapter

        noteMainListViewModel.notes.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }


}
