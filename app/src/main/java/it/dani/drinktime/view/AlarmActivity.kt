package it.dani.drinktime.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it.dani.drinktime.R
import it.dani.drinktime.view.receiver.RememberReceiver

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.alarm_activity)

        val clear = this.intent.getBooleanExtra("clear",false)
        if(clear) {
            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, RememberReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0xA0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.cancel(pendingIntent)
            this.finish()
        }

        findViewById<TextView>(R.id.alarm_message).apply {
            text = this@AlarmActivity.intent.getStringExtra("message") ?: ""
        }
    }
}