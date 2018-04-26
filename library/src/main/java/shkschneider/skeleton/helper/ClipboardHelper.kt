package me.shkschneider.skeleton.helper

import android.content.ClipData

object ClipboardHelper {

    fun copy(string: String) {
        val clipData = ClipData.newPlainText(ApplicationHelper.packageName(), string)
        SystemServices.clipboardManager()?.primaryClip = clipData
    }

}
