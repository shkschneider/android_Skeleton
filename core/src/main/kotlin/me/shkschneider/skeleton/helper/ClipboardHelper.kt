package me.shkschneider.skeleton.helper

import android.content.ClipData
import me.shkschneider.skeleton.helperx.SystemServices

object ClipboardHelper {

    fun copy(string: String) {
        val clipData = ClipData.newPlainText(ApplicationHelper.packageName, string)
        SystemServices.clipboardManager()?.primaryClip = clipData
    }

}
