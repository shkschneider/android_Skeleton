package me.shkschneider.skeleton.android.content

import android.content.ComponentName
import android.content.Context
import kotlin.reflect.KClass

fun <T : Any> componentName(pkg: Context, klass: KClass<T>): ComponentName =
        ComponentName(pkg, klass.java)
