package me.shkschneider.skeleton;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;

import me.shkschneider.skeleton.helper.LogHelper;

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level>
// <https://github.com/chrisbanes/cheesesquare>
public class SkeletonOverlayActivity extends SkeletonActivity {

    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        if (mCollapsingToolbarLayout != null) {
            LogHelper.verbose("Found a CollapsingToolbarLayout");
        }

        overlay(new ColorDrawable(getResources().getColor(R.color.actionBarColor)));
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

    public void overlay(@NonNull final Drawable drawable) {
        if (mCollapsingToolbarLayout != null) {
            mCollapsingToolbarLayout.setContentScrim(drawable);
        }
    }

}
