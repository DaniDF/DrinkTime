package it.dani.drinktime.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it.dani.drinktime.R
import it.dani.drinktime.controller.AlarmController

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.alarm_activity)

        val clear = this.intent.getBooleanExtra("clear",false)
        if(clear) {
            AlarmController.getInstance().cancel(this)
            this.finish()
        }

        findViewById<TextView>(R.id.alarm_message).apply {
            text = this@AlarmActivity.intent.getStringExtra("message") ?: ""
        }
    }
}