package hr.ferit.matijasokol.digitalplanner.model

internal const val UNDEFINED_INT = 0

class MyTime(
    var minute: Int = UNDEFINED_INT,
    var hour: Int = UNDEFINED_INT
) {

    companion object {
        fun parse(text: String): MyTime {
            val timeParts = text.split(":")
            return if (timeParts.size == 2) {
                val hour: Int = timeParts[0].toInt()
                val minute: Int = timeParts[1].toInt()

                MyTime(minute, hour)
            } else {
                MyTime()
            }
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        if (minute < 10) { stringBuilder.append("0") }
        stringBuilder.append("$minute:")
        if (hour < 10) { stringBuilder.append("0") }
        stringBuilder.append(hour)
        return stringBuilder.toString()
    }
}