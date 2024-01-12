package com.fit.app.alina.data.local.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fit.app.alina.data.dataClasses.User

@Database(entities = [User::class], version = 2)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}