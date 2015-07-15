package me.shkschneider.skeleton;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.CollapsingToolbarLayout;

import me.shkschneider.skeleton.helper.LogHelper;

// <https://github.com/chrisbanes/cheesesquare>
public class SkeletonOverlayActivity extends SkeletonActivity {

    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        if (mCollapsingToolbarLayout != null) {
            LogHelper.v("Found a CollapsingToolbarLayout");
            overlay(getResources().getColor(R.color.actionBarColor));
        }
    }

    @Override
    public void title(final String title) {
        if (mCollapsingToolbarLayout != null) {
            mCollapsingToolbarLayout.setTitle(title);
        }
        else {
            super.title(title);
        }
    }

    public void overlay(@ColorInt final int color) {
        if (mCollapsingToolbarLayout != null) {
            statusBarColor(getWindow(), color); // mCollapsingToolbarLayout.setStatusBarScrimColor(color)
            mCollapsingToolbarLayout.setContentScrimColor(color);
        }
    }

}
