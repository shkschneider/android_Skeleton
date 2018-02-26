package me.shkschneider.skeleton.helper

import android.content.res.AssetManager

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Collections

import me.shkschneider.skeleton.data.InternalDataHelper

object AssetsHelper {

    fun assetManager(): AssetManager {
        return ContextHelper.applicationContext().assets
    }

    fun list(): List<String>? {
        val list = ArrayList<String>()
        try {
            Collections.addAll(list, *assetManager().list(""))
            return list
        } catch (e: IOException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun open(name: String): InputStream? {
        val assetManager = assetManager()
        try {
            return assetManager.open(name)
        } catch (e: IOException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun dump(): Boolean {
        var errors = 0
        val assets = list()
        if (assets == null) {
            errors++
        } else {
            for (asset in assets) {
                try {
                    val inputStream = open(asset) ?: throw NullPointerException()
                    val outputStream = InternalDataHelper.openOutput(asset) ?: throw NullPointerException()
                    inputStream.copyTo(outputStream)
                    inputStream.close()
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    LogHelper.wtf(e)
                    errors++
                }
            }
        }
        return errors == 0
    }

}
