package hr.ferit.matijasokol.digitalplanner.presenters

import hr.ferit.matijasokol.digitalplanner.database.RepositoryImpl
import hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment.SavedItemsFragmentContract

class SavedItemsFragmentPresenter(private val view: SavedItemsFragmentContract.View) : SavedItemsFragmentContract.Presenter {

    override fun getAllItems() {
        val items = RepositoryImpl.getAllItems()
        view.onGetAllItems(items)
    }
}