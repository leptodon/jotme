package ru.cactus.jotme.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.cactus.jotme.R
import ru.cactus.jotme.databinding.WebViewActivityBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: WebViewActivityBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.web_view_activity)

        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.settings.apply {
            javaScriptEnabled = true
            loadsImagesAutomatically = true
        }

        myWebView.webViewClient = MyWebViewClient()
        myWebView.loadUrl("https://github.com/leptodon")
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }
}