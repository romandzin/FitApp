package com.fit.app.alina.data.local.notification

import com.fit.app.alina.data.dataClasses.Notification

interface IDataNotificationRepo {

    suspend fun inserNotificationText(notification: Notification)

    suspend fun getListOfNotifications(): List<Notification>

    suspend fun deleteAllNotifications()
}