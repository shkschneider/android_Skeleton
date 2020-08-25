package me.shkschneider.skeleton.android.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

import me.shkschneider.skeleton.android.provider.ContextProvider

object Inflater {

    fun inflate(layoutInflater: LayoutInflater, container: ViewGroup?, @LayoutRes layout: Int): View =
        layoutInflater.inflate(layout, container, false)

    fun inflate(container: ViewGroup, @LayoutRes layout: Int): View =
        inflate(LayoutInflater.from(container.context), container, layout)

    fun inflate(@LayoutRes layout: Int): View =
        inflate(LayoutInflater.from(ContextProvider.applicationContext()), null, layout)

}
