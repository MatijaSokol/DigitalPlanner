package hr.ferit.matijasokol.digitalplanner.model

class MyDate(
    var day: Int = UNDEFINED_INT,
    var month: Int = UNDEFINED_INT,
    var year: Int = UNDEFINED_INT
) {

    companion object {
        fun parse(text: String): MyDate {
            val dateParts = text.split("/")
            return if (dateParts.size == 3) {
                val day: Int = dateParts[0].toInt()
                val month: Int = dateParts[1].toInt()
                val year: Int = dateParts[2].toInt()

                MyDate(day, month, year)
            } else {
                MyDate()
            }
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        if (day < 10) { stringBuilder.append("0") }
        stringBuilder.append("$day/")
        if (month < 10) { stringBuilder.append("0") }
        stringBuilder.append("$month/$year")
        return stringBuilder.toString()
    }
}