package com.example.notes.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.ListItemTagBinding
import com.example.notes.viewmodels.TagListViewModel
import com.example.notes.viewmodels.TagListViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class TagListFragment : Fragment() {

    private lateinit var binding: ListItemTagBinding

    private lateinit var tagListViewModel: TagListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_tag_list, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = TagListFragmentArgs.fromBundle(arguments)

        val database = NotesDatabase.getInstance(application)
        val viewModelFactory = TagListViewModelFactory(
            arguments.noteId,
            database.tagDatabaseDao,
            database.joinNoteTagDAO
        )
        tagListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TagListViewModel::class.java)


        return binding.root
    }


}
