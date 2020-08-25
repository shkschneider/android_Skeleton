package me.shkschneider.skeleton.android.content

import android.content.res.AssetManager
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.io.InputStream

private fun assetManager(): AssetManager =
    ContextProvider.applicationContext().assets

fun AssetManager.list(): List<String>? =
    tryOrNull { assetManager().list("")?.toList() }

fun AssetManager.open(name: String): InputStream? =
    tryOrNull { assetManager().open(name) }
