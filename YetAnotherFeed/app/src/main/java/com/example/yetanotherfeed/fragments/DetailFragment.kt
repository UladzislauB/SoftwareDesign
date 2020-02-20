package com.example.yetanotherfeed.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.yetanotherfeed.databinding.FragmentDetailBinding
import com.example.yetanotherfeed.network.MyWebViewClient

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        val arguments = DetailFragmentArgs.fromBundle(arguments)

        binding.progressBar.max = 100

        binding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl(arguments.link)
            webViewClient = MyWebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.progressBar.progress = newProgress
                }
            }
        }

        return binding.root
    }

}
