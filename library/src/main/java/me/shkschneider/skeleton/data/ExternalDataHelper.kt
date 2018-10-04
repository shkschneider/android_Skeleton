package me.shkschneider.skeleton.data

import android.os.Environment
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import java.io.File

object ExternalDataHelper {

    // <http://stackoverflow.com/a/18383302>
    fun download(): File {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }

    fun cache(): File? {
        return ContextHelper.applicationContext().externalCacheDir
    }

    fun dir(): File {
        var path = FileHelper.join(Environment.getExternalStorageDirectory().path, "/Android/data/")
        path = FileHelper.join(path, ApplicationHelper.packageName())
        path = FileHelper.join(path, "/files")
        return File(path)
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
            files.filterNot { it.delete() }.forEach { errors++ }
        }
        return errors == 0
    }

    fun isReadable(): Boolean {
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun isWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isRemovable(): Boolean {
        return Environment.isExternalStorageRemovable()
    }

}
