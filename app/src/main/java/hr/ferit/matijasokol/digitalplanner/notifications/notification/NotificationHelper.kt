package hr.ferit.matijasokol.digitalplanner.notifications.notification

import android.content.Context
import androidx.core.app.NotificationCompat

interface NotificationHelper {

    fun createChannels()
    fun getNotification(context: Context, itemId: Long, itemText: String): NotificationCompat.Builder
}