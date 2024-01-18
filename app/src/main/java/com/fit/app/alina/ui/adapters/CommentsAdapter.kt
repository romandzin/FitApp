package com.fit.app.alina.ui.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fit.app.alina.R
import com.fit.app.alina.data.dataClasses.Comment

class CommentsAdapter(private val dataSet: List<Comment>):
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var personName: TextView
        var mainText: TextView
        var profileImage: ImageView
        var time: TextView


        init {
            personName = view.findViewById(R.id.personName)
            mainText = view.findViewById(R.id.mainText)
            profileImage = view.findViewById(R.id.profileImage)
            time = view.findViewById(R.id.time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_comment_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.personName.text = dataSet[position].name
        viewHolder.mainText.text = dataSet[position].commentText
        viewHolder.time.text = dataSet[position].time
    }


    override fun getItemCount() = dataSet.size

}