package com.fit.app.alina.ui.adapters

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
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fit.app.alina.R
import com.fit.app.alina.data.dataClasses.Notification
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.ui.fragment.MainScreenFragment

class NotificationsAdapter(private val dataSet: List<Notification>):
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    var user: User? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var mainTextView: TextView

        init {
            title = view.findViewById(R.id.not_title)
            mainTextView = view.findViewById(R.id.not_text)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_notification_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].title
        viewHolder.mainTextView.text = dataSet[position].text
    }


    override fun getItemCount() = dataSet.size

}

