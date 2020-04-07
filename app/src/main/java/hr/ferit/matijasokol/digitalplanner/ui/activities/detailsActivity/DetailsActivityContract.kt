package hr.ferit.matijasokol.digitalplanner.ui.activities.detailsActivity

import hr.ferit.matijasokol.digitalplanner.model.Item

interface DetailsActivityContract {

    interface View {
        fun onGetItemSuccess(item: Item)
        fun onGetItemFailure()
    }

    interface Presenter {
        fun getItemById(id: Long)
        fun deleteItem(id: Long)
        fun updateItem(item: Item)
    }
}