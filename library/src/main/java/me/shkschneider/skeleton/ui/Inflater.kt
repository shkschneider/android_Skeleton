package me.shkschneider.skeleton.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.shkschneider.skeleton.helper.ContextHelper

object Inflater {

    fun inflate(layoutInflater: LayoutInflater, container: ViewGroup?, layout: Int): View {
        return layoutInflater.inflate(layout, container, false)
    }

    fun inflate(container: ViewGroup, layout: Int): View {
        val layoutInflater = LayoutInflater.from(container.context)
        return inflate(layoutInflater, container, layout)
    }

    fun inflate(layout: Int): View {
        val layoutInflater = LayoutInflater.from(ContextHelper.applicationContext())
        return inflate(layoutInflater, null, layout)
    }

}
