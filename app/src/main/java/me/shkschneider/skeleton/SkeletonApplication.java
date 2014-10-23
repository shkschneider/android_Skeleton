package me.shkschneider.skeleton;

import android.app.Application;
import android.content.Context;

/**
 * Base Application you should use!
 *
 * Context CONTEXT
 */
public class SkeletonApplication extends Application {

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = getApplicationContext();
    }

}
