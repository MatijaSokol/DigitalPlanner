package hr.ferit.matijasokol.digitalplanner.extensions

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

fun Context.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun TextView.stringText(): String = text.toString().trim()

fun calendarYear(): Int = Calendar.getInstance().get(Calendar.YEAR)
fun calendarMonth(): Int = Calendar.getInstance().get(Calendar.MONTH)
fun calendarDayOfMonth(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
fun calendarHourOfDay(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
fun calendarMinute(): Int = Calendar.getInstance().get(Calendar.MINUTE)