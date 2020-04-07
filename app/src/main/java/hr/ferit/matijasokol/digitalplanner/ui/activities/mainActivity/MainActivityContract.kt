package hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity

interface MainActivityContract {

    interface View {
        fun onGetSwitchTabEnabled(enabled: Boolean)
    }

    interface Presenter {
        fun getSwitchTabEnabled()
        fun setSwitchTabEnabled(key: String, enabled: Boolean)
    }
}