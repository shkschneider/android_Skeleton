package me.shkschneider.skeleton.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

public class FragmentHelper {

    public static boolean replace(@NotNull final FragmentActivity fragmentActivity, final int layout, @NotNull final Fragment newFragment) {
        final FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.replace(layout, newFragment);
        if (fragmentTransaction.isAddToBackStackAllowed()) {
            fragmentTransaction.addToBackStack(null);
        }

        return (fragmentTransaction.commit() > 0);
    }

    public static boolean replace(@NotNull final FragmentActivity fragmentActivity, final int layout, @NotNull final Class<?> c, @NotNull final Bundle bundle) {
        return replace(fragmentActivity, layout, Fragment.instantiate(fragmentActivity, c.getName(), bundle));
    }

}
