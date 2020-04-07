package hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.anim.DepthPageTransformer
import hr.ferit.matijasokol.digitalplanner.presenters.MainActivityPresenter
import hr.ferit.matijasokol.digitalplanner.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.digitalplanner.ui.adapters.PagerAdapter
import hr.ferit.matijasokol.digitalplanner.ui.fragments.itemInputFragment.ItemInputFragment
import hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment.SavedItemsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

const val SWITCH_TAB = "switch_tab"

class MainActivity : BaseActivity(R.layout.activity_main), ItemInputFragment.OnChangeTabListener, MainActivityContract.View {

    var switchTabEnabled: Boolean = false
    private val presenter: MainActivityContract.Presenter by lazy { MainActivityPresenter(this) }

    override fun setUpUi() {
        setSupportActionBar(toolbarMainToolbar as Toolbar)
        setPagerAndTab()
        presenter.getSwitchTabEnabled()
    }

    override fun onGetSwitchTabEnabled(enabled: Boolean) {
        switchTabEnabled = enabled
    }

    private fun setPagerAndTab() {
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.apply {
            setFragments(listOf(
                ItemInputFragment.newInstance(),
                SavedItemsFragment.newInstance()
            ))
            setTitles(listOf(getString(R.string.input), getString(
                R.string.saved
            )))
        }
        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.getItem(0)?.isChecked = switchTabEnabled
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.switchTabOption) {
            item.isChecked = !item.isChecked
            switchTabEnabled = item.isChecked
            presenter.setSwitchTabEnabled(SWITCH_TAB, switchTabEnabled)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun changeTab() {
        if (viewPager.currentItem == 0) {
            viewPager.currentItem = 1
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 1) {
            viewPager.currentItem = 0
        } else {
            super.onBackPressed()
        }
    }
}
