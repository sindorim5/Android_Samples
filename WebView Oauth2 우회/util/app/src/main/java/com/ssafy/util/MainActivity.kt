package com.ssafy.util

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)

        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl("https://i8d210.p.ssafy.io")
        }

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportMultipleWindows(true)
            Log.d("싸피", "onCreate1: $userAgentString")
            userAgentString = getReplaceUserAgent(userAgentString)
            Log.d("싸피", "onCreate2: $userAgentString")
        }
    }

    private fun getReplaceUserAgent(defaultAgent: String): String {
        var userAgent = Regex(" Build/.+; wv").replace(defaultAgent, "")
        userAgent = Regex("Version/.+? ").replace(userAgent,"")
        return userAgent
    }
}