package me.shkschneider.skeleton.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import me.shkschneider.skeleton.helpers.LogHelper;

@SuppressWarnings("unused")
public final class FragmentHelper {

    public static boolean replace(final FragmentActivity fragmentActivity, final int layout, final Fragment newFragment) {
        if (fragmentActivity == null) {
            LogHelper.warning("FragmentActivity was NULL");
            return false;
        }
        if (newFragment == null) {
            LogHelper.warning("Fragment was NULL");
            return false;
        }

        final FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.replace(layout, newFragment);
        if (fragmentTransaction.isAddToBackStackAllowed()) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
        return true;
    }

    public static boolean replace(final FragmentActivity fragmentActivity, final int layout, final Class<?> c, final Bundle bundle) {
        return replace(fragmentActivity, layout, Fragment.instantiate(fragmentActivity, c.getName(), bundle));
    }

}
