package com.example.notes.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import com.example.notes.R
import com.example.notes.databinding.FragmentNoteMainListBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteMainListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNoteMainListBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_note_main_list, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(NoteMainListFragmentDirections.actionNoteMainListFragmentToNoteDetailFragment())
        }

        return binding.root
    }


}
