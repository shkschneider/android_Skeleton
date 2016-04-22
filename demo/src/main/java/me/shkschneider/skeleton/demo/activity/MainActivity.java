package me.shkschneider.skeleton.demo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import me.shkschneider.runtimepermissionscompat.RuntimePermissionsCompat;
import me.shkschneider.skeleton.demo.R;
import me.shkschneider.skeleton.demo.fragment.FloatingActionButtonFragment;
import me.shkschneider.skeleton.demo.fragment.InputsFragment;
import me.shkschneider.skeleton.demo.fragment.RecyclerViewFragment;
import me.shkschneider.skeleton.demo.fragment.MainFragment;
import me.shkschneider.skeleton.SkeletonNavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.demo.fragment.NetworkFragment;
import me.shkschneider.skeleton.demo.fragment.SnackBarFragment;
import me.shkschneider.skeleton.demo.fragment.TransitionFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerIconIndicatorFragment;
import me.shkschneider.skeleton.demo.fragment.ViewPagerTextIndicatorFragment;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.PermissionsHelper;

public class  MainActivity extends SkeletonNavigationDrawerActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigationHeader(R.layout.navigationdrawer_header);
        setNavigationMenu(R.menu.navigationdrawer);

        if (RuntimePermissionsCompat.isRevocable(MainActivity.this, PermissionsHelper.READ_PHONE_STATE)) {
            RuntimePermissionsCompat.requestPermission(MainActivity.this, PermissionsHelper.READ_PHONE_STATE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (navigationDrawer() != R.id.menu_recyclerView) {
            searchable(null, null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(AboutActivity.getIntent(MainActivity.this));
                return true;
            case R.id.menu_settings:
                startActivity(SettingsActivity.getIntent(MainActivity.this));
                return true;
            case R.id.menu_main:
            case R.id.menu_viewPagerTextIndicator:
            case R.id.menu_viewPagerIconIndicator:
            case R.id.menu_network:
            case R.id.menu_recyclerView:
            case R.id.menu_floatingActionButton:
            case R.id.menu_snackBar:
            case R.id.menu_transition:
            case R.id.menu_inputs:
                navigationDrawer(item.getItemId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected SkeletonFragment getFragment(final int itemId) {
        switch (itemId) {
            case R.id.menu_main:
                return new MainFragment();
            case R.id.menu_viewPagerTextIndicator:
                return new ViewPagerTextIndicatorFragment();
            case R.id.menu_viewPagerIconIndicator:
                return new ViewPagerIconIndicatorFragment();
            case R.id.menu_network:
                return new NetworkFragment();
            case R.id.menu_recyclerView:
                return new RecyclerViewFragment();
            case R.id.menu_floatingActionButton:
                return new FloatingActionButtonFragment();
            case R.id.menu_snackBar:
                return new SnackBarFragment();
            case R.id.menu_transition:
                return new TransitionFragment();
            case R.id.menu_inputs:
                return new InputsFragment();
            default:
                return null;
        }
    }

    @Override
    public boolean navigationDrawer(final int itemId) {
        if (super.navigationDrawer(itemId)) {
            subtitle(getFragment(itemId).title());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (! navigationDrawerOpenedOrOpening()) {
            openNavigationDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final boolean granted = RuntimePermissionsCompat.isGranted(MainActivity.this, PermissionsHelper.READ_PHONE_STATE);
        ActivityHelper.toast("READ_PHONE_STATE: " + (granted ? "GRANTED" : "DENIED"));
    }

}
