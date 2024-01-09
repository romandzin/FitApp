package com.fit.app.alina.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fit.app.alina.data.User

@Database(entities = [User::class], version = 2)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}