package me.shkschneider.skeleton.android.arch

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SkeletonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // <https://proandroiddev.com/kotlin-android-extensions-using-view-binding-the-right-way-707cd0c9e648>
    @Deprecated("Never use itemView directly, import synthetic subviews", ReplaceWith(""))
    fun getItemView(): View {
        return itemView
    }

}
