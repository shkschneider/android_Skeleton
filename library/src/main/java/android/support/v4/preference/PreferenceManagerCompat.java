package android.support.v4.preference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import me.shkschneider.skeleton.helper.LogHelper;

// <https://github.com/kolavar/android-support-v4-preferencefragment>
public final class PreferenceManagerCompat {

    public static PreferenceManager newInstance(final Activity activity, final int firstRequestCode) {
		try {
			final Constructor<PreferenceManager> constructor = PreferenceManager.class.getDeclaredConstructor(Activity.class, int.class);
            constructor.setAccessible(true);
			return constructor.newInstance(activity, firstRequestCode);
		}
        catch (final Exception e) {
            LogHelper.error("Constructor<PreferenceManager>");
		}
		return null;
	}

    public static void setFragment(final PreferenceManager manager, final PreferenceFragment fragment) {
    	// Ignore
    }

    public static void setOnPreferenceTreeClickListener(final PreferenceManager manager, final OnPreferenceTreeClickListener listener) {
		try {
            final Field onPreferenceTreeClickListener = PreferenceManager.class.getDeclaredField("mOnPreferenceTreeClickListener");
			onPreferenceTreeClickListener.setAccessible(true);
			if (listener != null) {
				onPreferenceTreeClickListener.set(manager, Proxy.newProxyInstance(onPreferenceTreeClickListener.getType().getClassLoader(),
                        new Class[] { onPreferenceTreeClickListener.getType() },
                        new InvocationHandler() {
                            @Override
                            public Object invoke(final Object proxy, final Method method, final Object[] args) {
                                if (method.getName().equals("onPreferenceTreeClick")) {
                                    return listener.onPreferenceTreeClick((PreferenceScreen) args[0], (Preference) args[1]);
                                }
                                else {
                                    return null;
                                }
                            }
                        }));
			}
            else {
				onPreferenceTreeClickListener.set(manager, null);
			}
		}
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.mOnPreferenceTreeClickListener");
		}
	}

    public static PreferenceScreen inflateFromIntent(final PreferenceManager manager, final Intent intent, final PreferenceScreen screen) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("inflateFromIntent", Intent.class, PreferenceScreen.class);
            method.setAccessible(true);
            return  (PreferenceScreen) method.invoke(manager, intent, screen);
        }
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.inflateFromIntent");
		}
		return null;
	}

    public static PreferenceScreen inflateFromResource(final PreferenceManager manager, final Activity activity, final int resId, final PreferenceScreen screen) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("inflateFromResource", Context.class, int.class, PreferenceScreen.class);
            method.setAccessible(true);
            return  (PreferenceScreen) method.invoke(manager, activity, resId, screen);
        }
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.inflateFromResource");
		}
		return null;
	}

    public static PreferenceScreen getPreferenceScreen(final PreferenceManager manager) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("getPreferenceScreen");
            method.setAccessible(true);
            return (PreferenceScreen) method.invoke(manager);
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
		}
		return null;
	}

    public static void dispatchActivityResult(final PreferenceManager manager, final int requestCode, final int resultCode, final Intent data) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("dispatchActivityResult", int.class, int.class, Intent.class);
            method.setAccessible(true);
            method.invoke(manager, requestCode, resultCode, data);
        }
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.dispatchActivityResult");
		}
	}

    public static void dispatchActivityStop(final PreferenceManager manager) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("dispatchActivityStop");
            method.setAccessible(true);
            method.invoke(manager);
        }
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.dispatchActivityStyop");
		}
	}

    public static void dispatchActivityDestroy(final PreferenceManager manager) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("dispatchActivityDestroy");
            method.setAccessible(true);
            method.invoke(manager);
		}
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.dispatchActivityDestroy");
		}
	}

    public static boolean setPreferences(final PreferenceManager manager, final PreferenceScreen screen) {
		try {
            final Method method = PreferenceManager.class.getDeclaredMethod("setPreferences", PreferenceScreen.class);
            method.setAccessible(true);
			return ((Boolean) method.invoke(manager, screen));
		}
        catch (final Exception e) {
            LogHelper.error("PreferenceManager.setPreferences");
		}
		return false;
	}

    public interface OnPreferenceTreeClickListener {

        boolean onPreferenceTreeClick(final PreferenceScreen preferenceScreen, final Preference preference);

    }

}
