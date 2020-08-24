package me.shkschneider.skeleton.android.core.helper

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf

object BundleHelper {

    fun pack(bundle: Bundle, key: String, parcelable: Parcelable?): Bundle =
        bundle.apply { putParcelable(key, parcelable) }

    fun pack(key: String, parcelable: Parcelable?): Bundle =
        bundleOf(key to parcelable)

    fun unpack(bundle: Bundle, key: String): Parcelable? =
        bundle.getParcelable(key)

}
