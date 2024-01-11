package com.fit.app.alina.data.local.notification

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fit.app.alina.data.Notification
import com.fit.app.alina.data.User
import com.fit.app.alina.data.local.user.UserDao

@Database(entities = [Notification::class], version = 1)
abstract class NotificationDatabase: RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}