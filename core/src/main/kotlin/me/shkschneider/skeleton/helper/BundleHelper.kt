package me.shkschneider.skeleton.helper

import android.os.Bundle
import me.shkschneider.skeleton.helperx.Logger

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
        bundle.getSerializable(key)?.let {
            return true
        }
        Logger.warning("Bundle has no such key")
        return null
    }

}
