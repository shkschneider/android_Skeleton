package me.shkschneider.skeleton.security

import android.util.Base64

object Base64Helper {

    fun encode(data: ByteArray) : String {
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    fun decode(data: String) : ByteArray {
        return Base64.decode(data, Base64.NO_WRAP)
    }

}
