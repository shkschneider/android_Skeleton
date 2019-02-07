package me.shkschneider.skeleton.extensions.android

import android.content.ComponentName
import android.content.Context
import kotlin.reflect.KClass

fun <T : Any> ComponentName(pkg: Context, klass: KClass<T>): ComponentName =
        ComponentName(pkg, klass.java)
