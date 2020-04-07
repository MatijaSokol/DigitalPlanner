package hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment

import hr.ferit.matijasokol.digitalplanner.model.Item
import io.realm.RealmResults

interface SavedItemsFragmentContract {

    interface View {
        fun onGetAllItems(items: RealmResults<Item>)
    }

    interface Presenter {
        fun getAllItems()
    }
}