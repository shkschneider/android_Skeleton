package me.shkschneider.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.shkschneider.app.fragment.ListViewFragment;
import me.shkschneider.app.fragment.MainFragment;
import me.shkschneider.app.fragment.NetworkFragment;
import me.shkschneider.app.R;
import me.shkschneider.skeleton.helper.ActivityHelper;
import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.NavigationDrawerActivity;
import me.shkschneider.skeleton.SkeletonFragment;
import me.shkschneider.skeleton.helper.SystemHelper;

public class MainActivity extends NavigationDrawerActivity {

    public static Intent getInstance(final Activity activity) {
        return new Intent(activity, MainActivity.class).setFlags(IntentHelper.HOME_FLAGS);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchable(getResources().getString(R.string.dots), new SearchCallback() {
            @Override
            public void onSearchTextChange(String q) {
                // Ignore
            }

            @Override
            public void onSearchTextSubmit(String q) {
                ActivityHelper.toast(q);
            }
        });

        navigationDrawer(0);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            startActivity(AboutActivity.getInstance(MainActivity.this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return new ArrayAdapter<SkeletonFragment>(this, R.layout.listview_navigationdrawer_item, new ArrayList<SkeletonFragment>() {
            {
                add(new MainFragment());
                add(new ListViewFragment());
                add(new NetworkFragment());
            }
        }) {
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    final LayoutInflater layoutInflater = (LayoutInflater) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.listview_navigationdrawer_item, parent, false);
                }
                final TextView textView = ((TextView) convertView.findViewById(R.id.textview));
                textView.setText(getTitle(position));
                if (position == navigationDrawer()) {
                    textView.setTextColor(getResources().getColor(R.color.blueDark));
                }
                else {
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
                return convertView;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }
        };
    }

    @Override
    protected Fragment getFragment(int position) {
        return (SkeletonFragment) getAdapter().getItem(position);
    }

    @Override
    protected String getTitle(int position) {
        return ((SkeletonFragment) getFragment(position)).title();
    }

}
