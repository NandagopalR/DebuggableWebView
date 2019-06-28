package com.nanda.webviewdebugsample

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img_search.setOnClickListener(this)
        setupWebView(webView)
    }

    override fun onClick(p0: View?) {
        if (TextUtils.isEmpty(et_web_address.text.trim().toString())) {
            Toast.makeText(this, "Web Address should not be empty!!!", Toast.LENGTH_SHORT).show()
        }
        webView.loadUrl(et_web_address.text.trim().toString())
        Handler().postDelayed(Runnable {
            hideKeyboard()
        }, 50)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et_web_address.windowToken, 0)
    }

    private fun setupWebView(webView: WebView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val settings: WebSettings = webView.settings
        settings.defaultTextEncodingName = "utf-8"
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = false

        webView.isVerticalScrollBarEnabled = true
        webView.requestFocus()
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
    }

    override fun onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}
