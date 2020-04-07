package hr.ferit.matijasokol.digitalplanner.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.model.Item
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_view.view.*

class ItemsAdapter(
    realmData: RealmResults<Item>,
    private val onItemClicked: ((Int, Item) -> Unit)
) : RealmRecyclerViewAdapter<Item, ItemsAdapter.ItemViewHolder>(realmData, true) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemId(position: Int): Long {
        getItem(position)?.let {
            return it.id
        }
        return 0
    }

    class ItemViewHolder(
        private val containerView: View,
        private val onItemClicked: (Int, Item) -> Unit
    ) : RecyclerView.ViewHolder(containerView) {

        fun bind(item: Item) {
            with(containerView) {
                textViewSavedDate.text = item.date
                textViewSavedTime.text = item.time
                textViewSavedNote.text = item.note

                setOnClickListener { onItemClicked(adapterPosition, item) }
            }
        }
    }
}