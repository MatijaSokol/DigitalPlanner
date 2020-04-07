package hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.presenters.SavedItemsFragmentPresenter
import hr.ferit.matijasokol.digitalplanner.ui.activities.detailsActivity.DetailsActivity
import hr.ferit.matijasokol.digitalplanner.ui.adapters.ItemsAdapter
import hr.ferit.matijasokol.digitalplanner.ui.fragments.base.BaseFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_saved_items.*

const val ITEM_EXTRA = "item extra"

class SavedItemsFragment : BaseFragment(R.layout.fragment_saved_items), SavedItemsFragmentContract.View {

    private val presenter: SavedItemsFragmentContract.Presenter by lazy { SavedItemsFragmentPresenter(this) }
    private val itemsAdapter by lazy { ItemsAdapter(data) { position, item -> onItemClicked(position, item) } }
    private lateinit var data: RealmResults<Item>

    private fun onItemClicked(position: Int, item: Item) {
        Intent(activity, DetailsActivity::class.java).also { intent ->
            intent.putExtra(ITEM_EXTRA, item.id)
            startActivity(intent)
        }
    }

    override fun setUpUi() {
        presenter.getAllItems()
    }

    private fun setRecycler() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = itemsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onGetAllItems(items: RealmResults<Item>) {
        data = items
        setRecycler()
    }

    companion object {
        fun newInstance() = SavedItemsFragment()
    }
}