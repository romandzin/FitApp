package com.fit.app.alina.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey
    val text: String
)