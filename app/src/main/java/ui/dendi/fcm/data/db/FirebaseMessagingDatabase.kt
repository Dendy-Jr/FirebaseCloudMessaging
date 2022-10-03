package ui.dendi.fcm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FirebaseMessageDatabase : RoomDatabase() {

    abstract val messageDao: MessageDao

    companion object {
        const val DATABASE_NAME = "messages_db"
    }
}