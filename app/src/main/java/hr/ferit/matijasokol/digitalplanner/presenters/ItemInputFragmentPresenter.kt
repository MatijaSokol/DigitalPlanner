package hr.ferit.matijasokol.digitalplanner.presenters

import hr.ferit.matijasokol.digitalplanner.database.RepositoryImpl
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.ui.fragments.itemInputFragment.ItemInputFragmentContract

class ItemInputFragmentPresenter(private val view: ItemInputFragmentContract.View) : ItemInputFragmentContract.Presenter {

    override fun saveItem(item: Item) {
        RepositoryImpl.saveItem(item)
    }
}