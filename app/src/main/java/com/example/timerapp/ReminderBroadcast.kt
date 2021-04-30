package com.example.timepicker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class ReminderBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationBuilder = NotificationCompat.Builder(context,"timeNotificationId")
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle("Timer")
            .setContentText("Time is up!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(200, notificationBuilder.build())
    }
}