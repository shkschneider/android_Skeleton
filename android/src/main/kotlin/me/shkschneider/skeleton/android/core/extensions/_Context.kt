package me.shkschneider.skeleton.android.core.extensions

import android.content.Context
import kotlin.reflect.KClass

fun <T : Any> Context.getSystemService(klass: KClass<T>): T? =
        getSystemService(klass.java)
