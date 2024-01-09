package com.fit.app.alina.data.local

import android.content.Context
import androidx.room.Room
import com.fit.app.alina.data.User

class DataRepoImpl(applicationContext: Context): IDataRepo {

    private val db = Room.databaseBuilder(
        applicationContext,
        UserDatabase::class.java, "database-name"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val userDao = db.userDao()

    override suspend fun insertUser(user: User) {
       userDao.insertUser(user)
    }

    override suspend fun getUser(): User {
        return userDao.getUser()
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }
}