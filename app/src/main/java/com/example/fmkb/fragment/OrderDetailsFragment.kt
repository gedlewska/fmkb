package com.example.fmkb.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fmkb.viewModel.OrderViewModel
import com.example.fmkb.R

class OrderDetailsFragment : Fragment() {

    private val viewModel: OrderViewModel by activityViewModels()
    lateinit var webView: WebView
    lateinit var noUrlTv: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_order_details, container, false)

        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        noUrlTv = view.findViewById(R.id.noUrlTv)
        progressBar = view.findViewById(R.id.progressBar)

        if (viewModel.url.value.isNullOrEmpty()) {
            webView.visibility = View.GONE
            noUrlTv.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.url.observe(requireActivity(), { item ->
            item?.let { loadWebView(item) }
        })

    }

    private fun loadWebView(url: String) {
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
                webView.visibility = View.GONE
                noUrlTv.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                webView.visibility = View.VISIBLE
                noUrlTv.visibility = View.GONE
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                webView.visibility = View.GONE
                noUrlTv.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                showToast("Error: $error")
                super.onReceivedError(view, request, error)
            }
        }
    }

    private fun showToast(message: String) {
        if (context != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}