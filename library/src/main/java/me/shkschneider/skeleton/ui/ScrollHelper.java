package me.shkschneider.skeleton.ui;

public class ScrollHelper {

    // <http://benjii.me/2010/08/endless-scrolling-listview-in-android/>
    public static boolean hasMoreItems(final int firstVisibleItem, final int visibleItemCount, final int totalItemCount, final int threshold) {
        return ((totalItemCount - visibleItemCount) <= (firstVisibleItem + threshold));
    }

}
