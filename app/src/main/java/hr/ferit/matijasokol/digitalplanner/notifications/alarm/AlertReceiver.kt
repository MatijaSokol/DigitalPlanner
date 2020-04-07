package hr.ferit.matijasokol.digitalplanner.notifications.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.notifications.notification.NotificationHelperImpl

class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == INTENT_ACTION){
            context?.let {
                val notificationManager = NotificationManagerCompat.from(it)
                val text = intent.getStringExtra(ITEM_TEXT) ?: it.getString(R.string.item_text)
                val id = intent.getLongExtra(ITEM_ID, 0)
                notificationManager.notify(id.toInt(), NotificationHelperImpl.getNotification(it, id, text).build())
            }
        }
    }
}