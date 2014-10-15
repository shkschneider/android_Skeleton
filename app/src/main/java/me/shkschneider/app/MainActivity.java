package me.shkschneider.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import me.shkschneider.skeleton.ActivityHelper;
import me.shkschneider.skeleton.NavigationDrawerActivity;

public class MainActivity extends NavigationDrawerActivity {

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
    }

    @Override
    protected ArrayAdapter getAdapter() {
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
            {
                add("Main");
                add("ListView");
                add("Network");
                add("About");
            }
        };
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new ListViewFragment();
            case 2:
                return new NetworkFragment();
            case 3:
                return new AboutFragment();
        }
        return null;
    }

    @Override
    protected String getTitle(int position) {
        return (String) getAdapter().getItem(position);
    }

}
