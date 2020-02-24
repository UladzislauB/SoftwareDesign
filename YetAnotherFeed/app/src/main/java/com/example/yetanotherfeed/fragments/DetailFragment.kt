package com.example.yetanotherfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.yetanotherfeed.database.getDatabase
import com.example.yetanotherfeed.databinding.FragmentDetailBinding
import com.example.yetanotherfeed.viewmodels.DetailViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val arguments = DetailFragmentArgs.fromBundle(arguments)

        val application = requireNotNull(this.activity).application
        val database = getDatabase(application)
        viewModel = ViewModelProviders.of(
            this, DetailViewModel
                .Factory(arguments.link, database.itemDao)
        )
            .get(DetailViewModel::class.java)

        binding.lifecycleOwner = this

        progressBar = binding.progressBar
        progressBar.max = 100

        binding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(arguments.link)
            webViewClient = object : myWebClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)

                    progressBar.visibility = View.GONE
                    binding.webView.visibility = View.VISIBLE
                    viewModel.setCachedState()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    viewModel.onError()
                    super.onReceivedError(view, request, error)
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    progressBar.progress = newProgress
                }
            }
        }

        return binding.root
    }

    open inner class myWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            progressBar.visibility = View.VISIBLE
            view.loadUrl(url)
            return true

        }

        override fun onPageFinished(view: WebView, url: String) {

            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

    }
}
