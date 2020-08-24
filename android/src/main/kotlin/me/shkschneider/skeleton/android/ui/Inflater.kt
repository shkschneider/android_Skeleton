package me.shkschneider.skeleton.android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

import me.shkschneider.skeleton.android.core.helper.ContextHelper

object Inflater {

    fun inflate(layoutInflater: LayoutInflater, container: ViewGroup?, @LayoutRes layout: Int): View =
        layoutInflater.inflate(layout, container, false)

    fun inflate(container: ViewGroup, @LayoutRes layout: Int): View =
        inflate(LayoutInflater.from(container.context), container, layout)

    fun inflate(@LayoutRes layout: Int): View =
        inflate(LayoutInflater.from(ContextHelper.applicationContext()), null, layout)

}
