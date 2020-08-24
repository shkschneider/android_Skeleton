package me.shkschneider.skeleton.android.content

import me.shkschneider.skeleton.android.content.SkeletonProvider
import me.shkschneider.skeleton.android.provider.ContextProvider

// TODO AndroidX AppStartup
class SkeletonAndroidProvider : SkeletonProvider() {

    override fun onCreate(): Boolean {
        ContextProvider.applicationContext(requireNotNull(context))
        return true
    }

}
