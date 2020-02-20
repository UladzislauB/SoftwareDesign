package com.example.yetanotherfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.yetanotherfeed.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val arguments = DetailFragmentArgs.fromBundle(arguments)

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
