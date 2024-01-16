package com.fit.app.alina.data.local

import android.content.Context
import androidx.room.Room
import com.fit.app.alina.data.dataClasses.Notification
import com.fit.app.alina.data.dataClasses.User
import com.fit.app.alina.data.local.notification.IDataNotificationRepo
import com.fit.app.alina.data.local.notification.NotificationDatabase
import com.fit.app.alina.data.local.user.IDataUserRepo
import com.fit.app.alina.data.local.user.UserDatabase

class DataImpl(applicationContext: Context): IDataUserRepo, IDataNotificationRepo {

    private val userDatabase = Room.databaseBuilder(
        applicationContext,
        UserDatabase::class.java, "users"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val userDao = userDatabase.userDao()

    private val notificationDatabase = Room.databaseBuilder(
        applicationContext,
        NotificationDatabase::class.java, "notification"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val notificationDao = notificationDatabase.notificationDao()

    override suspend fun insertUser(user: User) {
       userDao.insertUser(user)
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }

    override suspend fun inserNotificationText(notification: Notification) {
        notificationDao.inserNotificationText(notification)
    }

    override suspend fun getListOfNotifications(): List<Notification> {
        return notificationDao.getListOfNotifications()
    }

    override suspend fun deleteAllNotifications() {
        notificationDao.deleteAllNotifications()
    }
}