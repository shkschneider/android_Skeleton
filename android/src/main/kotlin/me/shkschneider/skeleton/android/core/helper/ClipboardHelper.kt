package me.shkschneider.skeleton.android.core.helper

import android.content.ClipData
import me.shkschneider.skeleton.android.core.helperx.SystemServices

object ClipboardHelper {

    fun copy(string: String) {
        val clipData = ClipData.newPlainText(ApplicationHelper.packageName, string)
        SystemServices.clipboardManager()?.setPrimaryClip(clipData)
    }

}
