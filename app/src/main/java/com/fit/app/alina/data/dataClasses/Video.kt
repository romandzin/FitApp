package com.fit.app.alina.data.dataClasses

data class Video(
    val url: String,
    val description: String,
    val comments: ArrayList<Comment>
)
