package hr.ferit.matijasokol.digitalplanner.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()

    fun setFragments(list: List<Fragment>) {
        fragments.apply { 
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun setTitles(list: List<String>) {
        titles.apply { 
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}