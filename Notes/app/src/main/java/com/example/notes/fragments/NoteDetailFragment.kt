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


        // Setting color control handlers
        binding.include.whiteColorBtn.setOnClickListener { colorBtnClickHandler(0) }
        binding.include.redVioletBtn.setOnClickListener { colorBtnClickHandler(1) }
        binding.include.teaRoseColorBtn.setOnClickListener { colorBtnClickHandler(2) }
        binding.include.ochreColorBtn.setOnClickListener { colorBtnClickHandler(3) }
        binding.include.orangeColorBtn.setOnClickListener { colorBtnClickHandler(4) }
        binding.include.maizeColorBtn.setOnClickListener { colorBtnClickHandler(5) }
        binding.include.electricLimeColorBtn.setOnClickListener { colorBtnClickHandler(6) }
        binding.include.aquamarineColorBtn.setOnClickListener { colorBtnClickHandler(7) }
        binding.include.skyBlueColorBtn.setOnClickListener { colorBtnClickHandler(8) }
        binding.include.blueGreenColorBtn.setOnClickListener { colorBtnClickHandler(9) }
        binding.include.lavenderColorBtn.setOnClickListener { colorBtnClickHandler(10) }
        binding.include.peachColorBtn.setOnClickListener { colorBtnClickHandler(11) }
        binding.include.silverColorBtn.setOnClickListener { colorBtnClickHandler(12) }



        return binding.root
    }

    fun colorBtnClickHandler(number: Int) {
        binding.include.whiteColorBtn.text = ""
        binding.include.redVioletBtn.text = ""
        binding.include.teaRoseColorBtn.text = ""
        binding.include.ochreColorBtn.text = ""
        binding.include.orangeColorBtn.text = ""
        binding.include.maizeColorBtn.text = ""
        binding.include.electricLimeColorBtn.text = ""
        binding.include.aquamarineColorBtn.text = ""
        binding.include.skyBlueColorBtn.text = ""
        binding.include.blueGreenColorBtn.text = ""
        binding.include.lavenderColorBtn.text = ""
        binding.include.peachColorBtn.text = ""
        binding.include.silverColorBtn.text = ""


        when (number) {
            0 -> {
                binding.include.whiteColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.white)
            }
            1 -> {
                binding.include.redVioletBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.red_violet)
            }
            2 -> {
                binding.include.teaRoseColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.tea_rose)
            }
            3 -> {
                binding.include.ochreColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.ochre)
            }
            4 -> {
                binding.include.orangeColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.orange)
            }
            5 -> {
                binding.include.maizeColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.maize)
            }
            6 -> {
                binding.include.electricLimeColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.electric_lime)
            }
            7 -> {
                binding.include.aquamarineColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.aquamarine)
            }
            8 -> {
                binding.include.skyBlueColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.sky_blue)
            }
            9 -> {
                binding.include.blueGreenColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.blue_green)
            }
            10 -> {
                binding.include.lavenderColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.lavender)
            }
            11 -> {
                binding.include.peachColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.peach)
            }
            12 -> {
                binding.include.silverColorBtn.text = "✔"
                binding.noteDetailLayout.setBackgroundResource(R.color.silver)
            }
        }
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
