package it.dani.drinktime.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import it.dani.drinktime.R
import it.dani.drinktime.view.receiver.RememberReceiver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hourPitcher = findViewById<NumberPicker>(R.id.hour_pitcher).apply {
            minValue = 0
            maxValue = 59
        }
        val minutePitcher = findViewById<NumberPicker>(R.id.minute_pitcher).apply {
            minValue = 0
            maxValue = 59
        }

        findViewById<ExtendedFloatingActionButton>(R.id.save_button).apply {
            setOnClickListener {
                val alarmManager = this@MainActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this@MainActivity, RememberReceiver::class.java).apply {
                    putExtra("message", this@MainActivity.resources.getString(R.string.remember_message))
                }
                val pendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0xA0, intent, PendingIntent.FLAG_IMMUTABLE)

                val afterMillis = this@MainActivity.convertIntoMillis(
                    hours = hourPitcher.value,
                    seconds = minutePitcher.value
                )

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + afterMillis,
                    afterMillis,
                    pendingIntent
                )
            }
        }

    }

    private fun convertIntoMillis(
        hours: Int = 0,
        minutes: Int = 0,
        seconds: Int = 0,
        milliseconds: Int = 0
    ): Long {
        return hours * 3600000L + minutes * 60000L + seconds * 1000L + milliseconds
    }
}