package hr.ferit.matijasokol.digitalplanner.notifications.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.app.App
import hr.ferit.matijasokol.digitalplanner.notifications.alarm.INTENT_ACTION
import hr.ferit.matijasokol.digitalplanner.ui.activities.detailsActivity.DetailsActivity
import hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment.ITEM_EXTRA

object NotificationHelperImpl : NotificationHelper {

    private const val CHANNEL_ID = "item notification id"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createChannels() {
        NotificationChannel(CHANNEL_ID, App.instance.getString(R.string.item_notification_channel), NotificationManager.IMPORTANCE_HIGH).apply {
            description = App.instance.getString(R.string.item_notification_description)
            enableLights(true)
            lightColor = Color.GREEN
            enableVibration(true)
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            setShowBadge(true)

            val notificationManager = App.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(this)
        }
    }

    override fun getNotification(context: Context, itemId: Long, itemText: String): NotificationCompat.Builder {
        val intent = Intent(context, DetailsActivity::class.java).apply {
            action = INTENT_ACTION
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(ITEM_EXTRA, itemId)
        }
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(itemId.toInt(), PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText(itemText)
            .setSmallIcon(R.drawable.notepad)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}