package hr.ferit.matijasokol.digitalplanner.database

import android.util.Log
import hr.ferit.matijasokol.digitalplanner.model.Item
import io.realm.Realm
import io.realm.RealmResults

object RepositoryImpl : Repository {

    private const val TAG = "[DEBUG] RepositoryImpl"
    private const val ID_FIELD = "id"
    private val realm: Realm = Realm.getDefaultInstance()

    override fun saveItem(item: Item) {
        realm.executeTransactionAsync( { realm: Realm ->
            var currentMaxId: Long = 0
            realm.where(Item::class.java).max(ID_FIELD)?.let {
                currentMaxId = it.toLong()
            }
            item.id = currentMaxId + 1
            realm.copyToRealm(item)
        }, {
            Log.d(TAG, "Success saveItem")
        }, { error ->
            Log.d(TAG, "Error saveItem: ${error.message}")
        } )
    }

    override fun getAllItems(): RealmResults<Item> = realm.where(Item::class.java).findAllAsync().sort(ID_FIELD)

    override fun deleteItemById(id: Long) {
        realm.executeTransactionAsync( { realm: Realm ->
            realm.where(Item::class.java).equalTo(ID_FIELD, id).findFirst()?.deleteFromRealm()
        }, {
            Log.d(TAG, "Success deleteItemById")
        }, {error ->
            Log.d(TAG, "Error deleteItemById: ${error.message}")
        } )
    }

    override fun getItemById(id: Long): Item? = realm.where(Item::class.java).equalTo(ID_FIELD, id).findFirst()

    override fun updateItem(item: Item) {
        realm.executeTransactionAsync( { realm: Realm ->
            realm.where(Item::class.java).equalTo(ID_FIELD, item.id).findFirst()?.let {
                it.note = item.note
                it.date = item.date
                it.time = item.time
            }
        }, {
            Log.d(TAG, "Success getItemById")
        }, { error ->
            Log.d(TAG, "Error getItemById: ${error.message}")
        })
    }
}