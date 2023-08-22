package com.merizalde.firebasecloudmessaging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.merizalde.firebasecloudmessaging.ui.activities.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.notification

        val title = data?.title
        val message = data?.body

        if (remoteMessage.getNotification() != null) {
            Log.d("FB_MESS", title.toString())
            Log.d("FB_MESS", message.toString())
            createNotificationChannel()
            sendNotification(title.toString(), message.toString())
        }

    }

    private val CHANNEL: String = "Notificaciones"

    @SuppressLint("MissingPermission")
    fun sendNotification(titulo: String, texto: String) {
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val noti = NotificationCompat.Builder(this, CHANNEL).apply {
            setContentIntent(notifyPendingIntent)
            setAutoCancel(true)
            setContentTitle(titulo)
            setContentText(texto)
            setSmallIcon(R.drawable.doraemon)
            priority = NotificationCompat.PRIORITY_DEFAULT

        }

        with(NotificationManagerCompat.from(this)) {
            notify(1, noti.build())
        }
    }

    private fun createNotificationChannel() {
        val name = "Aviso"
        val descriptionText = "Notificaciones simples de aviso"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}