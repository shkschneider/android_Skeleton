package me.shkschneider.skeleton

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

abstract class SkeletonProvider : ContentProvider() {

    override fun insert(uri: Uri,
                        values: ContentValues?): Uri? = null

    override fun query(uri: Uri,
                       projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? = null

    abstract override fun onCreate(): Boolean

    override fun update(uri: Uri,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri,
                        selection: String?,
                        selectionArgs: Array<String>?): Int = 0

    @Suppress("DEPRECATION")
    override fun getType(uri: Uri): String? = MimeTypeHelper.UNKNOWN

}