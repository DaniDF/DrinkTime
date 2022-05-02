package it.dani.drinktime.view.receiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import it.dani.drinktime.R
import it.dani.drinktime.view.AlarmActivity
import it.dani.drinktime.view.notification.NotificationUtils

class RememberReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: ""

        context?.let { ctx ->
            val newIntent = Intent(ctx, AlarmActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("message",message)
            }
            val pendingIntent = PendingIntent.getActivity(ctx, 0xC0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val newIntentClear = Intent(ctx,AlarmActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("clear",true)
            }
            val pendingIntentClear = PendingIntent.getActivity(ctx,0xC1,newIntentClear,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            NotificationUtils.createNotificationAlertChannel(ctx)
            val notificationBuilder =
                NotificationCompat.Builder(ctx, NotificationUtils.ALERT_CHANNEL_ID).apply {
                    setContentText(ctx.resources.getString(R.string.notification_alarm_title))
                    setSmallIcon(R.mipmap.ic_launcher)
                    priority = NotificationManager.IMPORTANCE_MAX
                    setContentIntent(pendingIntent)
                    setAutoCancel(true)
                    addAction(R.drawable.ic_timer_off,ctx.resources.getString(R.string.notification_clear),pendingIntentClear)
                }

            val manager = NotificationManagerCompat.from(ctx)
            notificationBuilder.setContentText(message)
            manager.notify(0x10, notificationBuilder.build())
        }
    }
}