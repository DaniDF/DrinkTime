package it.dani.drinktime.controller

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmController private constructor() {

    val intents : MutableList<PendingIntent> = LinkedList<PendingIntent>()

    private var count = 0

    fun setAlarm(context : Context, timeInMillis : Long, intent : Intent) : PendingIntent {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, 0xA0 + this.count++, intent, PendingIntent.FLAG_IMMUTABLE)

        this.intents += pendingIntent

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + timeInMillis,
            timeInMillis,
            pendingIntent
        )

        return pendingIntent
    }

    fun cancel(context: Context) {
        this.intents.forEach { this.cancel(context,it) }
    }

    fun cancel(context: Context, pendingIntent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private val alarmController = AlarmController()

        fun getInstance() : AlarmController {
            return alarmController
        }
    }
}