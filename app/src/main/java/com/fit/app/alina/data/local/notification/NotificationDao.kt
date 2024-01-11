package com.fit.app.alina.data.local.notification

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.fit.app.alina.data.Notification
import com.fit.app.alina.data.User

@Dao
interface NotificationDao {
    @Insert
    suspend fun inserNotificationText(notification: Notification)

    @Query("SELECT * FROM notification")
    suspend fun getListOfNotifications(): List<String>

    @Query("DELETE FROM notification")
    suspend fun deleteAllNotifications();
}