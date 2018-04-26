package me.shkschneider.skeleton.ui

object ScrollHelper {

    // <http://benjii.me/2010/08/endless-scrolling-listview-in-android/>
    fun hasMoreItems(firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int, threshold: Int): Boolean {
        return totalItemCount - visibleItemCount <= firstVisibleItem + threshold
    }

}
