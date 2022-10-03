package ui.dendi.fcm.notifications

interface PushNotificationsTokenProvider {

    suspend fun getToken(): Result<String>

    suspend fun clearToken()
}