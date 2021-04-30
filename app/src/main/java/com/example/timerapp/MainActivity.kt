package com.example.timepicker

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var timeTv: TextView
    private lateinit var timePickerBtn: Button

    private lateinit var calendar: Calendar

    private var hour: Int = 0
    private var minute: Int = 0

    private fun init() {
        timeTv = findViewById(R.id.timeTv)
        timePickerBtn = findViewById(R.id.timePickerBtn)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        timePickerBtn.setOnClickListener {
            setTimePickerDialogAndShow()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTimePickerDialogAndShow() {
        val timePickerDialog =
            TimePickerDialog(this, { _, hour, minute ->
                this.hour = hour
                this.minute = minute

                calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND,0)

                timeTv.text = DateFormat.format("hh:mm", calendar)

                setNotification()
            }, 12, 0, true)
        timePickerDialog.updateTime(hour, minute)
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotification() {

        Log.d("Notification","Setted")
        val channel = NotificationChannel(
            "timeNotificationId",
            "TimePickerReminderChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager =
            getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(this, ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 200, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this,"Timer setted",3000.toInt()).show()
    }
}