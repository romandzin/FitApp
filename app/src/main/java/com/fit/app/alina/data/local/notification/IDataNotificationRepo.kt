package com.fit.app.alina.data.local.notification

import com.fit.app.alina.data.Notification
import com.fit.app.alina.data.User

interface IDataNotificationRepo {

    suspend fun inserNotificationText(notification: Notification)

    suspend fun getListOfNotifications(): List<String>

    suspend fun deleteAllNotifications()
}