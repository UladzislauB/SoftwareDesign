package com.example.notes.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailBinding
import com.example.notes.viewmodels.NoteDetailViewModel
import com.example.notes.viewmodels.NoteDetailViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    private lateinit var noteDetailViewModel: NoteDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_note_detail, container, false)

        val application = requireNotNull(this.activity).application

        // Getting noteId from Bundle
        val arguments = NoteDetailFragmentArgs.fromBundle(arguments)

        // Instantiating ViewModelFactory
        val database = NotesDatabase.getInstance(application)
        val viewModelFactory = NoteDetailViewModelFactory(
            arguments.noteId,
            arguments.isJustCreated,
            database.noteDatabaseDAO,
            database.tagDatabaseDao,
            database.joinNoteTagDAO
        )

        // Getting ViewModel instance
        noteDetailViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NoteDetailViewModel::class.java)

        binding.viewModel = noteDetailViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onPause() {
        Log.i("NoteDetailFragment", "onPause called")

        val title: String = binding.editTextTitle.text.toString()
        val body: String = binding.editTextBody.text.toString()
        val color: Int = 0
        noteDetailViewModel.onSaveChanges(title, body, color)
        super.onPause()
    }

}
