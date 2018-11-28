package me.shkschneider.skeleton.di

import org.kodein.di.Instance
import org.kodein.di.KodeinAware
import org.kodein.di.generic

inline fun <reified T : Any> KodeinAware.get(tag: Any? = null) = Instance<T>(generic(), tag)
