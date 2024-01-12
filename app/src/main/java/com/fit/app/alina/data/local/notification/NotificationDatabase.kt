package com.fit.app.alina.data.local.notification

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fit.app.alina.data.dataClasses.Notification

@Database(entities = [Notification::class], version = 5)
abstract class NotificationDatabase: RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}