package me.shkschneider.skeleton.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

// <https://stackoverflow.com/a/29168276>
class MyRecyclerViewSeparator : RecyclerView.ItemDecoration {

    private val size: Int
    private val grid: Int
    private var spacing = false

    // Resources.getDimensionPixelSize()
    constructor(px: Int, grid: Int) {
        size = px
        this.grid = grid
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth = ((parent.width - size.toFloat() * (grid - 1)) / grid).toInt()
        val padding = parent.width / grid - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < grid) {
            outRect.top = 0
        } else {
            outRect.top = size
        }
        if (itemPosition % grid == 0) {
            outRect.left = 0
            outRect.right = padding
            spacing = true
        } else if ((itemPosition + 1) % grid == 0) {
            spacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (spacing) {
            spacing = false
            outRect.left = size - padding
            if ((itemPosition + 2) % grid == 0) {
                outRect.right = size - padding
            } else {
                outRect.right = size / 2
            }
        } else if ((itemPosition + 2) % grid == 0) {
            spacing = false
            outRect.left = size / 2
            outRect.right = size - padding
        } else {
            spacing = false
            outRect.left = size / 2
            outRect.right = size / 2
        }
        outRect.bottom = 0
    }

}
