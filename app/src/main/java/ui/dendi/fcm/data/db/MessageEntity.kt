package ui.dendi.fcm.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ui.dendi.fcm.data.db.MessageEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String,
    val date: String
) {
    companion object {
        const val TABLE_NAME = "message_table"
    }
}