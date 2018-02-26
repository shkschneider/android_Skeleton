package me.shkschneider.skeleton.data

import android.content.Context
import android.os.Environment

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.LogHelper

object InternalDataHelper {

    fun openInput(name: String): FileInputStream? {
        try {
            return ContextHelper.applicationContext().openFileInput(name)
        } catch (e: FileNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun openOutput(name: String): FileOutputStream? {
        try {
            return ContextHelper.applicationContext().openFileOutput(name, Context.MODE_PRIVATE)
        } catch (e: FileNotFoundException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun root(): File {
        return Environment.getRootDirectory()
    }

    fun cache(): File {
        return ContextHelper.applicationContext().cacheDir
    }

    fun dir(): File {
        return ContextHelper.applicationContext().filesDir
    }

    fun file(name: String): File? {
        return File(dir(), name)
    }

    fun delete(name: String): Boolean {
        return ContextHelper.applicationContext().deleteFile(name)
    }

    fun wipe(): Boolean {
        var errors = 0
        val dir = dir()
        if (dir.exists()) {
            val files = dir.listFiles() ?: return true
            files.filterNot { it.delete() }.forEach { errors++ }
        }
        return errors == 0
    }

}
