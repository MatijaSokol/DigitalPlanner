package hr.ferit.matijasokol.digitalplanner.presenters

import hr.ferit.matijasokol.digitalplanner.persistence.PreferenceManager
import hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity.MainActivityContract
import hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity.SWITCH_TAB

class MainActivityPresenter(private val view: MainActivityContract.View) : MainActivityContract.Presenter {

    override fun getSwitchTabEnabled() {
        val enabled = PreferenceManager.getValue(SWITCH_TAB, false)
        view.onGetSwitchTabEnabled(enabled)
    }

    override fun setSwitchTabEnabled(key: String, enabled: Boolean) {
        PreferenceManager.saveValue(SWITCH_TAB, enabled)
    }
}