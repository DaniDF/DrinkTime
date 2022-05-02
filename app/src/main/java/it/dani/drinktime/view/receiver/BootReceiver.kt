package it.dani.drinktime.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import it.dani.drinktime.R
import it.dani.drinktime.controller.AlarmController

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when(it.action) {
                "android.intent.action.BOOT_COMPLETED" -> {
                    context?.let { ctx ->
                        val alarmTimeInMillis = ctx.getSharedPreferences(
                            ctx.resources.getString(R.string.shared_preferences_filename),
                            Context.MODE_PRIVATE
                        ).getLong("alarm",-1)

                        if(alarmTimeInMillis >= 0) {
                            val newIntent = Intent(ctx, RememberReceiver::class.java).apply {
                                putExtra("message", ctx.resources.getString(R.string.remember_message))
                            }
                            AlarmController.getInstance().setAlarm(ctx,alarmTimeInMillis,newIntent)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}