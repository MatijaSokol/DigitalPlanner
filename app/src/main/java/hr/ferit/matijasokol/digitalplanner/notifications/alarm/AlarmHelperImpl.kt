package hr.ferit.matijasokol.digitalplanner.notifications.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.model.MyDate
import hr.ferit.matijasokol.digitalplanner.model.MyTime
import java.util.*

const val ITEM_TEXT = "item_text"
const val ITEM_ID = "item_id"
const val INTENT_ACTION = "alarm_action"

object AlarmHelperImpl :
    AlarmHelper {

    private fun getIntent(context: Context, item: Item): Intent {
        return Intent(context, AlertReceiver::class.java).apply {
            action = INTENT_ACTION
            putExtra(ITEM_TEXT, item.note)
            putExtra(ITEM_ID, item.id)
        }
    }

    override fun setAlarm(context: Context, item: Item) {
        val notificationIntent = getIntent(context, item)
        val pendingIntent = PendingIntent.getBroadcast(context, item.id.toInt(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val myDate = MyDate.parse(item.date)
        val myTime = MyTime.parse(item.time)

        val calendar = Calendar.getInstance()
        //months start from 0 -> 0 January, 1 February...
        calendar.set(myDate.year, myDate.month - 1, myDate.day, myTime.hour, myTime.minute, 0)

        if (!calendar.before(Calendar.getInstance())) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= 23) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }
    }

    override fun cancelAlarm(context: Context, item: Item) {
        val notificationIntent = getIntent(context, item)
        val pendingIntent = PendingIntent.getBroadcast(context, item.id.toInt(), notificationIntent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}