package me.shkschneider.skeleton.android.core.extensions

import android.content.ComponentName
import android.content.Context
import kotlin.reflect.KClass

fun <T : Any> componentName(pkg: Context, klass: KClass<T>): ComponentName =
        ComponentName(pkg, klass.java)
