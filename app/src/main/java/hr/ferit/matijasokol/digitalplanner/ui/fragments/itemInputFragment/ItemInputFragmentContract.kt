package hr.ferit.matijasokol.digitalplanner.ui.fragments.itemInputFragment

import hr.ferit.matijasokol.digitalplanner.model.Item

interface ItemInputFragmentContract {

    interface View

    interface Presenter {
        fun saveItem(item: Item)
    }
}