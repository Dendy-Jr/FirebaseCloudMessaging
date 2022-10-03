package ui.dendi.fcm.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.dendi.fcm.data.FirebaseMessagingRepository
import ui.dendi.fcm.data.db.MessageEntity
import ui.dendi.fcm.notifications.PushNotificationsTokenProvider
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: FirebaseMessagingRepository,
    private val pushNotificationsTokenProvider: PushNotificationsTokenProvider
) : ViewModel() {

    private val _messages = MutableStateFlow(listOf<MessageEntity>())
    val messages = _messages.asStateFlow()

    init {
        getToken()
        getMessages()
    }

    private fun getToken() {
        viewModelScope.launch {
            pushNotificationsTokenProvider.getToken()
                .onSuccess { token ->
                    Log.d("FirebaseMessaging", "Token retrieved, token: $token")
                }.onFailure { error ->
                    Log.d("FirebaseMessaging", "Failed to get token, error: $error")
                }
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            repository.getMessages().collect { messages ->
                _messages.value = messages
            }
        }
    }
}