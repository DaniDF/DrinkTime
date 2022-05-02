package it.dani.drinktime.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.dani.drinktime.R
import it.dani.drinktime.controller.AlarmController
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

        this.getSharedPreferences(this.resources.getString(R.string.shared_preferences_filename),Context.MODE_PRIVATE).also {
            val alarm = it.getLong("alarm",-1)
            if(alarm >= 0) {
                val mapValuesHMSMS = this.convertIntoHMSMS(alarm)
                hourPitcher.apply { value = mapValuesHMSMS["hour"] ?: 0 }
                minutePitcher.apply { value = mapValuesHMSMS["minute"] ?: 0 }
            }
        }

        findViewById<ExtendedFloatingActionButton>(R.id.save_button).apply {
            setOnClickListener {
                val intent = Intent(this@MainActivity, RememberReceiver::class.java).apply {
                    putExtra("message", this@MainActivity.resources.getString(R.string.remember_message))
                }

                val afterMillis = this@MainActivity.convertIntoMillis(
                    hours = hourPitcher.value,
                    minutes = minutePitcher.value
                )

                AlarmController.getInstance().setAlarm(this@MainActivity,afterMillis,intent)

                this@MainActivity.getSharedPreferences(
                    this@MainActivity.resources.getString(R.string.shared_preferences_filename),
                    Context.MODE_PRIVATE
                )?.let {
                    with(it.edit()) {
                        putLong("alarm",afterMillis)
                        apply()
                    }
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.clear_button).apply {
            setOnClickListener {
                AlarmController.getInstance().cancel(this@MainActivity)
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

    private fun convertIntoHMSMS(timeInMillis : Long) : Map<String,Int> {
        val result = HashMap<String,Int>()

        result["hour"] = (timeInMillis / 3600000L).toInt()
        var partial = timeInMillis % 3600000L

        result["minute"] = (partial / 60000L).toInt()
        partial %= 60000L

        result["second"] = (partial / 1000L).toInt()
        partial %= 1000L

        result["millisecond"] = partial.toInt()

        return result
    }
}