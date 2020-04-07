package hr.ferit.matijasokol.digitalplanner.ui.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutResourceId: Int) : Fragment(layoutResourceId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpUi()
    }

    abstract fun setUpUi()
}