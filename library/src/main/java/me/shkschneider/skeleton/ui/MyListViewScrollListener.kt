package me.shkschneider.skeleton.ui

import android.widget.AbsListView

// <https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView#implementing-with-listview>
// AbsListView.setOnScrollListener()
abstract class MyListViewScrollListener : AbsListView.OnScrollListener {

    // The minimum amount of items to have below your current scroll position before _loading more.
    private var _visibleThreshold = 5
    // The current offset index of data you have loaded
    private var _currentPage = 0
    // The total number of items in the dataset after the last load
    private var _previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var _loading = true
    // Sets the starting page index
    private var _startingPageIndex = 0

    constructor(visibleThreshold: Int) {
        _visibleThreshold = visibleThreshold
    }

    constructor(visibleThreshold: Int, startPage: Int) {
        _visibleThreshold = visibleThreshold
        _startingPageIndex = startPage
        _currentPage = startPage
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < _previousTotalItemCount) {
            _currentPage = _startingPageIndex
            _previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) _loading = true
        }
        // If it's still _loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished _loading and update the current page
        // number and total item count.
        if (_loading && totalItemCount > _previousTotalItemCount) {
            _loading = false
            _previousTotalItemCount = totalItemCount
            _currentPage++
        }

        // If it isn't currently _loading, we check to see if we have breached
        // the _visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!_loading && firstVisibleItem + visibleItemCount + _visibleThreshold >= totalItemCount) {
            _loading = onLoadMore(_currentPage + 1, totalItemCount)
        }
    }

    // Defines the process for actually _loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    abstract fun onLoadMore(page: Int, totalItemsCount: Int): Boolean

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        // Empty
    }

}
