package me.shkschneider.skeleton.extensions.android

import androidx.appcompat.widget.SearchView

// <https://github.com/nowfalsalahudeen/KdroidExt>

fun SearchView.onQueryChange(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(q: String): Boolean {
            query(q)
            return false
        }

        override fun onQueryTextSubmit(q: String): Boolean = false

    })
}

fun SearchView.onQuerySubmit(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(q: String): Boolean = false

        override fun onQueryTextSubmit(q: String): Boolean {
            query(q)
            return false
        }

    })
}
