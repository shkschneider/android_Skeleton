package me.shkschneider.skeleton.java;

import android.os.StrictMode;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class StrictModeHelper {

    public static void on() {
        on(false);
    }

    public static void on(final boolean death) {
        if (! ApplicationHelper.debuggable()) {
            return;
        }

        final StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (death) {
            // threadPolicyBuilder.penaltyDialog();
            threadPolicyBuilder.penaltyDeath();
        }
        StrictMode.setThreadPolicy(threadPolicyBuilder.build());

        final StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (death) {
            vmPolicyBuilder.penaltyDeath();
        }
        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

    public static void off() {
        if (! ApplicationHelper.debuggable()) {
            return;
        }

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        StrictMode.setVmPolicy(StrictMode.VmPolicy.LAX);
    }

}
