package com.fit.app.alina.ui.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fit.app.alina.R
import com.fit.app.alina.data.User
import com.fit.app.alina.ui.fragment.MainScreenFragment

class VideosAdapter(private val dataSet: Array<String>, private val fragment: MainScreenFragment):
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

        var user: User? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lockedIcon: ImageView
        var videoView: WebView
        var clickable: View

        init {
            videoView = view.findViewById(R.id.video)
            lockedIcon = view.findViewById(R.id.locked_icon)
            clickable = view.findViewById(R.id.clickable)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_video_layout, viewGroup, false)
        fragment.mainViewModel.userData.observe(fragment) {
            user = it
            Log.d("tag", it.toString())
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (user?.isSubscribed == true) viewHolder.lockedIcon.visibility = View.INVISIBLE
        else viewHolder.lockedIcon.isVisible = true
        viewHolder.videoView.loadUrl(dataSet[position])
        viewHolder.clickable.setOnClickListener {
            if (user?.isSubscribed == true) fragment.initChosenVideo(dataSet[position])
            else fragment.openDialog()
        }
        setWebView(viewHolder)
    }

    private fun setWebView(holder: ViewHolder) {
        val set = holder.videoView.settings
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
        holder.videoView.webViewClient = object: WebViewClient() {
        }
        holder.videoView.webChromeClient = object : WebChromeClient() {
        }
    }


    override fun getItemCount() = dataSet.size

}

