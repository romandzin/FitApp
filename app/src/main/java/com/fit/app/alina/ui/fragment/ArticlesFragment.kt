package com.fit.app.alina.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.fit.app.alina.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment() {

    lateinit var binding: FragmentArticlesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(layoutInflater)
        prepareWebView()
        binding.articles.loadUrl("https://petrova-alina.ru/stati-o-fitnese-trenirovkakh-i-pravilnom-pitanii/")
        return binding.root
    }

    private fun prepareWebView() {
        val set = binding.articles.settings
        set.setSupportZoom(true)
        set.domStorageEnabled = true
        set.builtInZoomControls = true
        set.allowFileAccess = true
        set.allowContentAccess = true
        set.loadWithOverviewMode = true
        set.setGeolocationEnabled(true)
        set.pluginState = WebSettings.PluginState.ON
        set.javaScriptCanOpenWindowsAutomatically = true
        set.javaScriptEnabled = true
        set.supportMultipleWindows()
        CookieManager.getInstance().setAcceptCookie(true)
        binding.articles.webViewClient = object : WebViewClient() {}
        binding.articles.webChromeClient = object : WebChromeClient() {}
        binding.articles.isVisible = true
    }
}