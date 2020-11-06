package www.mc.com.tour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import www.mc.com.databinding.ActivityTourBinding

class TourActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = TourPagerAdapter(supportFragmentManager)
        binding.tourViewPager.adapter = pagerAdapter

        binding.tourViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            /**
             * Called when the scroll state changes. Useful for discovering when the user
             * begins dragging, when the pager is automatically settling to the current page,
             * or when it is fully stopped/idle.
             *
             * @param state The new scroll state.
             * @see ViewPager.SCROLL_STATE_IDLE
             *
             * @see ViewPager.SCROLL_STATE_DRAGGING
             *
             * @see ViewPager.SCROLL_STATE_SETTLING
             */
            override fun onPageScrollStateChanged(state: Int) {

            }

            /**
             * This method will be invoked when the current page is scrolled, either as part
             * of a programmatically initiated smooth scroll or a user initiated touch scroll.
             *
             * @param position Position index of the first page currently being displayed.
             * Page position+1 will be visible if positionOffset is nonzero.
             * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
             * @param positionOffsetPixels Value in pixels indicating the offset from position.
             */
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when(position) {
                    0 -> selectFirstPage()
                    1 -> selectSecondPage()
                    2 -> selectThirdPage()
                }
            }

            /**
             * This method will be invoked when a new page becomes selected. Animation is not
             * necessarily complete.
             *
             * @param position Position index of the new selected page.
             */
            override fun onPageSelected(position: Int) {

            }
        })
    }

    private fun selectFirstPage() {
        binding.pageOneIndicator.isSelected = true
        binding.pageTwoIndicator.isSelected = false
        binding.pageThreeIndicator.isSelected = false
    }

    private fun selectSecondPage() {
        binding.pageOneIndicator.isSelected = false
        binding.pageTwoIndicator.isSelected = true
        binding.pageThreeIndicator.isSelected = false
    }

    private fun selectThirdPage() {
        binding.pageOneIndicator.isSelected = false
        binding.pageTwoIndicator.isSelected = false
        binding.pageThreeIndicator.isSelected = true
    }
}