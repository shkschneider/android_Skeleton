package me.shkschneider.skeleton.android.core.data

import android.content.Context
import android.os.Environment
import me.shkschneider.skeleton.android.core.helper.ApplicationHelper
import me.shkschneider.skeleton.android.core.helper.ContextHelper
import me.shkschneider.skeleton.android.log.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

object DataHelper {

    // Shared
    // Unsecure
    object External {

        // <http://stackoverflow.com/a/18383302>
        fun download(): File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        fun cache(): File? =
            ContextHelper.applicationContext().externalCacheDir

        fun dir(): File =
            File(
                    FileHelper.join(Environment.getExternalStorageDirectory().path, "/Android/data/")
                            + ApplicationHelper.packageName
                            + "/files"
            )

        fun file(name: String): File? =
            ContextHelper.applicationContext().getExternalFilesDir(name)

        fun delete(name: String): Boolean =
            ContextHelper.applicationContext().deleteFile(name)

        fun wipe(): Boolean {
            var errors = 0
            val dir = dir()
            if (dir.exists()) {
                val files = dir.listFiles() ?: return true
                files.filterNot { it.delete() }.forEach { _ -> errors++ }
            }
            return errors == 0
        }

        fun isReadable(): Boolean =
            Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)

        fun isWritable(): Boolean =
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        fun isRemovable(): Boolean =
            Environment.isExternalStorageRemovable()

    }

    // Sandboxed
    // Secure
    object Internal {

        fun openInput(name: String): FileInputStream? =
            try {
                ContextHelper.applicationContext().openFileInput(name)
            } catch (e: FileNotFoundException) {
                Logger.wtf(e)
                null
            }

        fun openOutput(name: String): FileOutputStream? =
            try {
                ContextHelper.applicationContext().openFileOutput(name, Context.MODE_PRIVATE)
            } catch (e: FileNotFoundException) {
                Logger.wtf(e)
                null
            }

        fun root(): File =
            Environment.getRootDirectory()

        fun cache(): File =
            ContextHelper.applicationContext().cacheDir

        fun dir(): File =
            ContextHelper.applicationContext().filesDir

        fun file(name: String): File =
            File(dir(), name)

        fun delete(name: String): Boolean =
            ContextHelper.applicationContext().deleteFile(name)

        fun wipe(): Boolean {
            var errors = 0
            val dir = dir()
            if (dir.exists()) {
                val files = dir.listFiles() ?: return true
                errors += files.filterNot { it.delete() }.count()
            }
            return errors == 0
        }

    }

}
