package ui.dendi.fcm.data

import kotlinx.coroutines.flow.Flow
import ui.dendi.fcm.data.db.MessageDao
import ui.dendi.fcm.data.db.MessageEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMessagingRepository @Inject constructor(
    private val dao: MessageDao
){

    fun getMessages(): Flow<List<MessageEntity>> {
        return dao.getMessages()
    }

    suspend fun saveMessage(message: MessageEntity) {
        dao.insertMessage(message)
    }
}