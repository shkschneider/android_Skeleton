package me.shkschneider.skeleton.android.core

import me.shkschneider.skeleton.android.core.helper.ContextHelper
import me.shkschneider.skeleton.android.log.Logger

class ContextProvider : SkeletonProvider() {

    override fun onCreate(): Boolean {
        ContextHelper.applicationContext(context!!)
        return context?.let {
            Logger.info("Context set")
            return true
        } ?: run {
            Logger.warning("Context INVALID")
            return false
        }
    }

}
