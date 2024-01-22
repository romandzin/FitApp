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
import com.fit.app.alina.R
import com.fit.app.alina.data.dataClasses.Video
import com.fit.app.alina.databinding.FragmentEnterDataBinding
import com.fit.app.alina.databinding.FragmentVideoPlayerBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.ui.adapters.CommentsAdapter
import com.fit.app.alina.viewModel.MainViewModel

class VideoPlayerFragment : Fragment() {

    lateinit var binding: FragmentVideoPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        prepareWebView()
        binding.completeButton.setOnClickListener {
            //TODO выполнение тренировки
        }
    }

    private fun prepareWebView() {
        val set = binding.chosen.settings
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
        binding.chosen.webViewClient = object : WebViewClient() {}
        binding.chosen.webChromeClient = object : WebChromeClient() {}
        binding.chosen.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        val currentVideo = (activity as MainActivity).mainViewModel.currentOpenedVideo.value
        binding.chosen.loadUrl(currentVideo!!.url)
        binding.description.text = currentVideo.description
        binding.comments.adapter = CommentsAdapter(currentVideo.comments)
    }
}