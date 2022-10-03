@file:OptIn(ExperimentalCoroutinesApi::class)

package ui.dendi.fcm.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

class PushNotificationsTokenProviderImpl @Inject constructor() : PushNotificationsTokenProvider {

    override suspend fun getToken(): Result<String> = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                Log.d("FirebaseMessaging", "Token retrieved, token: $token")
                if (!continuation.isActive) return@addOnSuccessListener
                continuation.resume(Result.success(token), null)
            }.addOnFailureListener { error ->
                Log.d("FirebaseMessaging", "Failed to get token, error: $error")
                if (!continuation.isActive) return@addOnFailureListener
                continuation.resume(Result.failure(error))
            }
    }

    override suspend fun clearToken(): Unit = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().deleteToken()
            .addOnSuccessListener {
                Log.d("FirebaseMessaging", "Token cleared")
                if (!continuation.isActive) return@addOnSuccessListener
                continuation.resume(Unit)
            }.addOnFailureListener { error ->
                Log.d("FirebaseMessaging", "Failed to clear token, error: $error")
                if (!continuation.isActive) return@addOnFailureListener
                continuation.resume(Unit)
            }
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface PushNotificationsTokenProviderModule {

    @Singleton
    @Binds
    fun binds(impl: PushNotificationsTokenProviderImpl): PushNotificationsTokenProvider
}