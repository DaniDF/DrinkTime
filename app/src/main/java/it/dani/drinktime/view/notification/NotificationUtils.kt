package it.dani.drinktime.view.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import it.dani.drinktime.R

object NotificationUtils {
    const val ALERT_CHANNEL_ID = "Alert_ID"
    private const val NOTIFICATION_CHANNEL_ID = "Notification_ID"

    fun createNotificationAlertChannel(context : Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationName = context.resources.getString(R.string.notification_alert_name)
            val notificationChannel = NotificationChannel(ALERT_CHANNEL_ID,notificationName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = context.resources.getString(R.string.notification_alert_description)
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }
    }

    fun createNotificationNotificationChannel(context : Context)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notificationName = context.resources.getString(R.string.notification_notification_name)
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,notificationName, NotificationManager.IMPORTANCE_LOW)
            notificationChannel.description = context.resources.getString(R.string.notification_notification_description)
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }
    }
}