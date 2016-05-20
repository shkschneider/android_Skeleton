package me.shkschneider.skeleton.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import me.shkschneider.skeleton.SkeletonActivity;

public class MainActivity extends SkeletonActivity {

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar.setTitle("Skeleton");
        mToolbar.setSubtitle("for Android");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                switch (position) {
                    case 0:
                        return new ShkFragment();
                    case 1:
                        return new SkFragment();
                }
                return null;
            }
            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case 0:
                        return "ShkSchneider";
                    case 1:
                        return "Skeleton";
                }
                return null;
            }
            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
