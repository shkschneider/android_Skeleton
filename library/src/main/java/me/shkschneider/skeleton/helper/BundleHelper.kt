package me.shkschneider.skeleton.helper

import android.os.Bundle

import java.io.Serializable

object BundleHelper {

    fun pack(bundle: Bundle, key: String, serializable: Serializable?): Bundle {
        bundle.putSerializable(key, serializable)
        return bundle
    }

    fun pack(key: String, serializable: Serializable?): Bundle {
        val bundle = Bundle()
        return pack(bundle, key, serializable)
    }

    fun unpack(bundle: Bundle, key: String): Serializable? {
        if (! bundle.containsKey(key)) {
            Logger.warning("Bundle has no such key")
            return null
        }
        return bundle.getSerializable(key)
    }

}
