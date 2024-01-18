package com.fit.app.alina.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fit.app.alina.R
import com.fit.app.alina.data.dataClasses.Article
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.ui.fragment.ArticlesFragment
import com.squareup.picasso.Picasso

class ArticlesAdapter(private val dataSet: ArrayList<Article>, val fragment: ArticlesFragment) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var user: User? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var photo: ImageView
        var title: TextView
        //var text: TextView

        init {
            title = view.findViewById(R.id.article_title)
            photo = view.findViewById(R.id.article_photo)
            //text = view.findViewById(R.id.clickable)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_article, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Picasso.with(fragment.context)
            .load(dataSet[position].photo)
            .into(viewHolder.photo)
        viewHolder.title.text = dataSet[position].articleTitle
        viewHolder.itemView.setOnClickListener {
            fragment.itemClicked(dataSet[position])
        }
    }


    override fun getItemCount() = dataSet.size

}

