package hr.ferit.matijasokol.digitalplanner.presenters

import hr.ferit.matijasokol.digitalplanner.database.RepositoryImpl
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.ui.activities.detailsActivity.DetailsActivityContract

class DetailsActivityPresenter(private val view: DetailsActivityContract.View) : DetailsActivityContract.Presenter {

    override fun getItemById(id: Long) {
        RepositoryImpl.getItemById(id).also {
            if (it != null) {
                view.onGetItemSuccess(it)
            } else {
                view.onGetItemFailure()
            }
        }
    }

    override fun deleteItem(id: Long) {
        RepositoryImpl.deleteItemById(id)
    }

    override fun updateItem(item: Item) {
        RepositoryImpl.updateItem(item)
    }
}