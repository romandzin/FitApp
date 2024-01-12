package com.fit.app.alina.data.dataClasses

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["text"], unique = false)])
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0,

    val title: String,
    val text: String
)