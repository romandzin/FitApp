package com.fit.app.alina.common

import androidx.core.app.NotificationCompat
import com.fit.app.alina.data.dataClasses.Notification
import com.fit.app.alina.data.local.DataImpl
import com.google.firebase.messaging.FirebaseMessagingService
import com.onesignal.notifications.INotificationReceivedEvent
import com.onesignal.notifications.INotificationServiceExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NotificationServiceExtension: INotificationServiceExtension, FirebaseMessagingService() {
    private var body = ""
    private val job = SupervisorJob()
    override fun onNotificationReceived(event: INotificationReceivedEvent) {
        val notification = event.notification
        body = notification.body.toString()
        val title = notification.title.toString()
        saveMessage(title, body)
        notification.setExtender { builder: NotificationCompat.Builder -> builder.setColor(-0xffff01) }
    }

    private fun saveMessage(title: String, messageBody: String?) {
        val data = DataImpl(MyApplication.getAppContext())
        val not = Notification(title = title, text = messageBody!!)
        val scope = CoroutineScope(Dispatchers.IO + job)
        scope.launch {
            data.inserNotificationText(not);
        }
    }
}
