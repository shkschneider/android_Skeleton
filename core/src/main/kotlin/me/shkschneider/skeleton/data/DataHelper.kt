package me.shkschneider.skeleton.data

import android.content.Context
import android.os.Environment
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

object DataHelper {

    // Shared
    // Unsecure
    object External {

        // <http://stackoverflow.com/a/18383302>
        fun download(): File {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        }

        fun cache(): File? {
            return ContextHelper.applicationContext().externalCacheDir
        }

        fun dir(): File {
            return File(
                    FileHelper.join(Environment.getExternalStorageDirectory().path, "/Android/data/")
                            + "${ApplicationHelper.packageName}/files"
            )
        }

        fun file(name: String): File? {
            return ContextHelper.applicationContext().getExternalFilesDir(name)
        }

        fun delete(name: String): Boolean {
            return ContextHelper.applicationContext().deleteFile(name)
        }

        fun wipe(): Boolean {
            var errors = 0
            val dir = dir()
            if (dir.exists()) {
                val files = dir.listFiles() ?: return true
                files.filterNot { it.delete() }.forEach { _ -> errors++ }
            }
            return errors == 0
        }

        val isReadable: Boolean
            get() = Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)

        val isWritable: Boolean
            get() =  Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        val isRemovable: Boolean
            get() =  Environment.isExternalStorageRemovable()

    }

    // Sandboxed
    // Secure
    object Internal {

        fun openInput(name: String): FileInputStream? {
            try {
                return ContextHelper.applicationContext().openFileInput(name)
            } catch (e: FileNotFoundException) {
                Logger.wtf(e)
                return null
            }
        }

        fun openOutput(name: String): FileOutputStream? {
            try {
                return ContextHelper.applicationContext().openFileOutput(name, Context.MODE_PRIVATE)
            } catch (e: FileNotFoundException) {
                Logger.wtf(e)
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

}
