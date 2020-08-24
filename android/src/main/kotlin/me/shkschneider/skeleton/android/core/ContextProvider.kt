package me.shkschneider.skeleton

import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.log.Logger

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
