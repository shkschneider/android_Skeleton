package me.shkschneider.skeleton.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentHelper {

    public static boolean replace(@NonNull final FragmentActivity fragmentActivity, final int layout, @NonNull final Fragment newFragment) {
        final FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.replace(layout, newFragment);
        if (fragmentTransaction.isAddToBackStackAllowed()) {
            fragmentTransaction.addToBackStack(null);
        }

        return (fragmentTransaction.commit() > 0);
    }

    public static boolean replace(@NonNull final FragmentActivity fragmentActivity, final int layout, @NonNull final Class<?> c, @NonNull final Bundle bundle) {
        return replace(fragmentActivity, layout, Fragment.instantiate(fragmentActivity, c.getName(), bundle));
    }

}
