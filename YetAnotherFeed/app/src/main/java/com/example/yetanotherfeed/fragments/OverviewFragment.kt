package com.example.yetanotherfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.yetanotherfeed.adapter.FeedListAdapter
import com.example.yetanotherfeed.databinding.FragmentOverviewBinding
import com.example.yetanotherfeed.viewmodels.OverviewViewModel

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.feedList.adapter = FeedListAdapter()

        viewModel.rssObject.observe(this, Observer {
            it?.let {
                (binding.feedList.adapter as FeedListAdapter).submitList(it.items)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }


}
