package ui.dendi.fcm.notifications

import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import ui.dendi.fcm.data.FirebaseMessagingRepository
import ui.dendi.fcm.R
import ui.dendi.fcm.data.db.MessageEntity
import ui.dendi.fcm.util.DateFormatter
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    @Inject
    lateinit var notificationsManager: NotificationsManager

    @Inject
    lateinit var repository: FirebaseMessagingRepository

    @Inject
    lateinit var dateFormatter: DateFormatter

    private val job = SupervisorJob()
    private val scope = CoroutineScope(job + Dispatchers.IO)
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Error", exception.stackTraceToString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification = message.notification?.let {

            scope.launch(handler) {
                repository.saveMessage(
                    MessageEntity(
                        id = 0,
                        title = it.title ?: "",
                        body = it.body ?: "",
                        date = dateFormatter.format(System.currentTimeMillis())
                    )
                )
            }

            Notification(
                id = Random.nextInt(),
                title = it.title ?: getString(R.string.app_name),
                body = it.body ?: "",
                channel = NotificationChannel(
                    id = "message",
                    title = getString(R.string.notifications_channel_messages),
                ),
                data = message.data
            )
        } ?: return
        notificationsManager.show(notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}