package me.shkschneider.skeleton.android.content

import android.content.ClipData
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.SystemServices

object ClipboardHelper {

    fun copy(string: String) {
        val clipData = ClipData.newPlainText(ApplicationHelper.packageName, string)
        SystemServices.clipboardManager()?.setPrimaryClip(clipData)
    }

}
