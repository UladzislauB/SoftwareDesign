package com.example.notes.fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.notes.R
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentNoteDetailBinding
import com.example.notes.models.Tag
import com.example.notes.pretty_tag_adapter.PrettyTagAdapter
import com.example.notes.viewmodels.NoteDetailViewModel
import com.example.notes.viewmodels.NoteDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_note_detail.*

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    private lateinit var noteDetailViewModel: NoteDetailViewModel

    private lateinit var arguments: NoteDetailFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_note_detail, container, false)


        val application = requireNotNull(this.activity).application

        // Getting noteId from Bundle
        arguments = NoteDetailFragmentArgs.fromBundle(getArguments())

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

        binding.prettyTagList.apply {
            adapter = PrettyTagAdapter(tagList)
            layoutManager = LinearLayoutManager(this@NoteDetailFragment.context)
        }

        // Setting color control handlers
        binding.apply {
            include.whiteColorBtn.setOnClickListener { colorBtnClickHandler(0) }
            include.redVioletBtn.setOnClickListener { colorBtnClickHandler(1) }
            include.teaRoseColorBtn.setOnClickListener { colorBtnClickHandler(2) }
            include.ochreColorBtn.setOnClickListener { colorBtnClickHandler(3) }
            include.orangeColorBtn.setOnClickListener { colorBtnClickHandler(4) }
            include.maizeColorBtn.setOnClickListener { colorBtnClickHandler(5) }
            include.electricLimeColorBtn.setOnClickListener { colorBtnClickHandler(6) }
            include.aquamarineColorBtn.setOnClickListener { colorBtnClickHandler(7) }
            include.skyBlueColorBtn.setOnClickListener { colorBtnClickHandler(8) }
            include.blueGreenColorBtn.setOnClickListener { colorBtnClickHandler(9) }
            include.lavenderColorBtn.setOnClickListener { colorBtnClickHandler(10) }
            include.peachColorBtn.setOnClickListener { colorBtnClickHandler(11) }
            include.silverColorBtn.setOnClickListener { colorBtnClickHandler(12) }
        }


        return binding.root
    }

    fun colorBtnClickHandler(number: Int) {

        noteDetailViewModel.setColor(number)

        binding.apply {
            include.whiteColorBtn.text = ""
            include.redVioletBtn.text = ""
            include.teaRoseColorBtn.text = ""
            include.ochreColorBtn.text = ""
            include.orangeColorBtn.text = ""
            include.maizeColorBtn.text = ""
            include.electricLimeColorBtn.text = ""
            include.aquamarineColorBtn.text = ""
            include.skyBlueColorBtn.text = ""
            include.blueGreenColorBtn.text = ""
            include.lavenderColorBtn.text = ""
            include.peachColorBtn.text = ""
            include.silverColorBtn.text = ""
        }

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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_note_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_tags -> {
                findNavController().navigate(
                    NoteDetailFragmentDirections.actionNoteDetailFragmentToTagListFragment(
                        arguments.noteId
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val tagList: MutableList<Tag>
        get() {
            return mutableListOf(
                Tag(title = "Exercise"),
                Tag(title = "Be Cool"),
                Tag(title = "Floss"),
                Tag(title = "Read the Sign"),
                Tag(title = "Meditation"),
                Tag(title = "Be Cool in an awesome way"),
                Tag(title = "Go Crazy")
            )
        }

    override fun onPause() {
        Log.i("NoteDetailFragment", "onPause called")

        val title: String = binding.editTextTitle.text.toString()
        val body: String = binding.editTextBody.text.toString()
        noteDetailViewModel.onSaveChanges(title, body)
        super.onPause()
    }

}
