package hr.ferit.matijasokol.digitalplanner.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal const val UNDEFINED_STRING = ""

open class Item(
    @PrimaryKey
    var id: Long = 0,
    var note: String = UNDEFINED_STRING,
    var date: String = UNDEFINED_STRING,
    var time: String = UNDEFINED_STRING,
    var dateCreated: String = UNDEFINED_STRING,
    var timeCreated: String = UNDEFINED_STRING
) : RealmObject() {

    override fun toString(): String {
        return "Item(id=$id, note=$note, date=${date}, time=${time}, dateCreated=${dateCreated}, timeCreated=${timeCreated})"
    }
}