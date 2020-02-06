package com.example.notes.fragments


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper

import com.example.notes.R
import com.example.notes.adapters.SwipeToDeleteCallback
import com.example.notes.adapters.TagListAdapter
import com.example.notes.database.NotesDatabase
import com.example.notes.databinding.FragmentTagListBinding
import com.example.notes.viewmodels.TagListViewModel
import com.example.notes.viewmodels.TagListViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class TagListFragment : Fragment() {

    private lateinit var binding: FragmentTagListBinding

    private lateinit var tagListViewModel: TagListViewModel

    private lateinit var adapter: TagListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

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


        binding.viewModel = tagListViewModel
        binding.setLifecycleOwner(this)


        binding.addTagBtn.visibility = View.GONE

        var areTagsInstantiated = false
        var areJoinsInstantiated = false
        var adapterInstantiated = false

        tagListViewModel.joins.observe(this, Observer { list ->
            if (!areJoinsInstantiated) {
                areJoinsInstantiated = true
            }
            if (areJoinsInstantiated && areTagsInstantiated && !adapterInstantiated) {
                adapter = TagListAdapter(this.context!!, tagListViewModel.tags.value!!, list) { tagId ->
                    tagListViewModel.onTagClicked(tagId)
                    Toast.makeText(context, "yo", Toast.LENGTH_SHORT).show()
                }
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter){
                    tagListViewModel.onTagDelete(it)
                    adapter.updateTagListItems(tagListViewModel.tags.value!!)
                })
                itemTouchHelper.attachToRecyclerView(binding.tagList)
                binding.tagList.adapter = adapter
                adapterInstantiated = true
            }
            if (adapterInstantiated) {
                adapter.updateJoins(list)
                adapter.updateTagListItems(tagListViewModel.tags.value!!)
            }

        })

        tagListViewModel.tags.observe(this, Observer { list ->
            if (!areTagsInstantiated) {
                areTagsInstantiated = true
            }
            if (areJoinsInstantiated && areTagsInstantiated && !adapterInstantiated) {
                adapter = TagListAdapter(this.context!!, list, tagListViewModel.joins.value!!) { tagId ->
                    tagListViewModel.onTagClicked(tagId)
                    Toast.makeText(context, "yo", Toast.LENGTH_SHORT).show()
                }
                val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter){
                    tagListViewModel.onTagDelete(it)
                    adapter.updateTagListItems(tagListViewModel.tags.value!!)
                })
                itemTouchHelper.attachToRecyclerView(binding.tagList)
                binding.tagList.adapter = adapter
                adapterInstantiated = true
            }
        })


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_fragment_tag_list, menu)
        val searchView = menu?.findItem(R.id.tag_search)?.actionView as SearchView

        val cachedSearchQuery = tagListViewModel.searchQuery

        if (cachedSearchQuery.isNotEmpty()) {
            searchView.setQuery(cachedSearchQuery, false)
            adapter.filter.filter(cachedSearchQuery)
            searchView.isFocusable = true
            searchView.isIconified = false
        }

        searchView.setOnSearchClickListener {
            binding.addTagBtn.visibility = View.VISIBLE
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                adapter.filter.filter(query)
                tagListViewModel.searchQuery = query
                return false
            }
        })

        binding.addTagBtn.setOnClickListener {
            val textQuery = searchView.query.toString()
            if (textQuery.isNotEmpty()) {
                tagListViewModel.onCreate(textQuery)
                searchView.setQuery("", false)
                searchView.clearFocus()
                searchView.isIconified = true
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}
