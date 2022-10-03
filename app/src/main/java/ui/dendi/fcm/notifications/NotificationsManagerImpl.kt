package ui.dendi.fcm.notifications

import android.app.Application
import android.app.NotificationChannel
import android.os.Build
import androidx.core.content.getSystemService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.fcm.presentation.MainActivity
import ui.dendi.fcm.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsManagerImpl @Inject constructor(
    private val application: Application
) : NotificationsManager {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun show(notification: Notification) {
        val manager = application.getSystemService<NotificationManager>() ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notification.channel.id,
                notification.channel.title,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(application, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtras(notification.extras)
        }

        val pendingIntent = PendingIntent.getActivity(
            application,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(application, notification.channel.id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notification.body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        manager.notify(notification.id, builder.build())
    }

    private val Notification.extras: Bundle
        get() {
            val bundle = bundleOf(
                "title" to title,
                "body" to body
            )
            data.entries.forEach { entry ->
                bundle.putString(entry.key, entry.value)
            }
            return bundle
        }
}

@Module
@InstallIn(SingletonComponent::class)
interface NotificationsManagerModule {

    @Binds
    fun binds(impl: NotificationsManagerImpl): NotificationsManager
}