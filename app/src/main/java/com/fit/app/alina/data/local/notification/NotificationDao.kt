package com.fit.app.alina.data.local.notification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fit.app.alina.data.dataClasses.Notification

@Dao
interface NotificationDao {
    @Insert
    suspend fun inserNotificationText(notification: Notification)

    @Query("SELECT * FROM notification")
    suspend fun getListOfNotifications(): List<Notification>

    @Query("DELETE FROM notification")
    suspend fun deleteAllNotifications();
}