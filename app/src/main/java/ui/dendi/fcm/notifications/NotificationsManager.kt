package ui.dendi.fcm.notifications

interface NotificationsManager {

    fun show(notification: Notification)
}

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val channel: NotificationChannel,
    val data:Map<String, String>
)

data class NotificationChannel(
    val id: String,
    val title: String
)