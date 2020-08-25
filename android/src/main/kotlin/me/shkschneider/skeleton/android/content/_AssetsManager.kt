package me.shkschneider.skeleton.android.content

import android.content.res.AssetManager
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull

fun AssetManager.list(): List<String>? =
    tryOrNull { ContextProvider.applicationContext().assets.list("")?.toList() }
