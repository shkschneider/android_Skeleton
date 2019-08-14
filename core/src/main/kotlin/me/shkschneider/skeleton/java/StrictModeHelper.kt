package me.shkschneider.skeleton.java

import android.os.StrictMode

import me.shkschneider.skeleton.helper.ApplicationHelper

object StrictModeHelper {

    fun defaults() {
        StrictMode.enableDefaults()
    }

    fun on(death: Boolean = false) {
        if (! ApplicationHelper.debuggable) {
            return
        }
        val threadPolicyBuilder = StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
        if (death) {
            threadPolicyBuilder.penaltyDeath()
        }
        StrictMode.setThreadPolicy(threadPolicyBuilder.build())
        val vmPolicyBuilder = StrictMode.VmPolicy.Builder()
                // .detectNonSdkApiUsage()
                .detectAll()
                .penaltyLog()
        if (death) {
            vmPolicyBuilder.penaltyDeath()
        }
        StrictMode.setVmPolicy(vmPolicyBuilder.build())
    }

    fun off() {
        if (! ApplicationHelper.debuggable) {
            return
        }
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
        StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX)
    }

}
