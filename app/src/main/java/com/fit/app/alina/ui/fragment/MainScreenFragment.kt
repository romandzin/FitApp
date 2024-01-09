package com.fit.app.alina.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.fit.app.alina.data.User
import com.fit.app.alina.databinding.FragmentMainScreenBinding
import com.fit.app.alina.ui.activity.MainActivity
import com.fit.app.alina.ui.adapters.VideosAdapter
import com.fit.app.alina.viewModel.MainViewModel


class MainScreenFragment : Fragment() {

    lateinit var binding: FragmentMainScreenBinding
    var user: User? = null
    val mainViewModel: MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        mainViewModel.userData.observe(viewLifecycleOwner) { userData ->
            user = userData
        }
        initView()
        mainViewModel.stage.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    onShowButtons()
                }

                2 -> {
                    showVideos()
                }
            }
        }
        return binding.root
    }

    private fun onShowButtons() {
        binding.beautiful.isVisible = true
        binding.chosen.isVisible = false
        binding.weightLoss.isVisible = true
        binding.relief.isVisible = true
        binding.videos.isVisible = false
    }

    private fun initView() {
        binding.weightLoss.setOnClickListener {
            mainViewModel.openNewStage()
        }
        binding.beautiful.setOnClickListener {
            mainViewModel.openNewStage()
        }
        binding.relief.setOnClickListener {
            mainViewModel.openNewStage()
        }
        binding.buttonSubscribe.setOnClickListener {
            mainViewModel.subscribedButtonClicked()
            binding.videos.adapter!!.notifyDataSetChanged()
        }
    }

    private fun showVideos() {
        binding.beautiful.isVisible = false
        binding.chosen.isVisible = false
        binding.weightLoss.isVisible = false
        binding.relief.isVisible = false
        binding.videos.isVisible = true
        val adapter = VideosAdapter(
            arrayOf("https://player.vimeo.com/video/679443177?badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=58479"),
            this,
        )
        binding.videos.adapter = adapter
    }

    fun openDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage("Для доступа к видео требуется подписка")
            .setTitle("Подписка")
            .setPositiveButton("Подписаться") { dialog, which ->
                mainViewModel.subscribedButtonClicked()
                binding.videos.adapter!!.notifyDataSetChanged()
                dialog.cancel()
            }
            .setNegativeButton("Нет") { dialog, which ->
                dialog.cancel()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun initChosenVideo(videoUrl: String) {
        mainViewModel.openNewStage()
        binding.chosen.loadUrl(videoUrl)
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
        binding.videos.isVisible = false
        binding.chosen.isVisible = true
    }
}