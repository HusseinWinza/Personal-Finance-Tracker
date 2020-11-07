package www.mc.com.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by SegunFrancis
 *
 * Specifies equal margins for [RecyclerView] items
 */

class MarginItemDecoration(
    private val bottomMargin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val itemCount = state.itemCount
            val itemPosition = parent.getChildAdapterPosition(view)
            if (itemCount > 0 && itemPosition == itemCount - 1) {
                bottom = bottomMargin
            }
        }
    }
}