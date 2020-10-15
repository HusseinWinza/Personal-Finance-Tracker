package www.mc.com.tour

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * Created by SegunFrancis
 *
 * [ViewPager] adapter that is responsible for the swiping screens in [TourActivity]
 *
 * @param fragmentManager FragmentManager of the activity. Use `supportFragmentManager`
 * for in activities and `childFragmentManager` in fragments
 */

class TourPagerAdapter(
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TourScreenOneFragment()
            1 -> TourScreenTwoFragment()
            2 -> NameFragment()
            else -> throw IllegalStateException("Unknown argument")
        }
    }

    /**
     * Return the number of views available.
     */
    override fun getCount(): Int {
        return 3
    }
}