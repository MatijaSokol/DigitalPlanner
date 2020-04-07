package hr.ferit.matijasokol.digitalplanner.notifications.alarm

import android.content.Context
import hr.ferit.matijasokol.digitalplanner.model.Item

interface AlarmHelper {

    fun setAlarm(context: Context, item: Item)
    fun cancelAlarm(context: Context, item: Item)
}