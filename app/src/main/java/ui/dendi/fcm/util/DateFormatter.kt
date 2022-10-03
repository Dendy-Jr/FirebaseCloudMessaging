package ui.dendi.fcm.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatter @Inject constructor() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())

    fun format(timestamp: Long): String {
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}