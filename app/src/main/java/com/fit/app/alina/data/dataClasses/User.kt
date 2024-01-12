package com.fit.app.alina.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    var key: String = "",
    var phone: String = "",
    var email: String = "",
    var name: String = "",
    var gender: String = "",
    var age: String = "",
    var height: String = "",
    var currentWeight: String = "",
    var desiredWeight: String = "",
    var isSubscribed: Boolean = false
)
