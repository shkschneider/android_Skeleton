package me.shkschneider.skeleton;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import me.shkschneider.skeleton.ui.MyScrollView;

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level>
public class SkeletonFadingActionBarActivity extends SkeletonActivity {

    private Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Don't forget to set this theme in your AndroidManifest: SkeletonTheme.ActionBarOverlay

        fadingActionBar(new ColorDrawable(getResources().getColor(R.color.actionBarColor)));
    }

    public void fadingActionBar(@NonNull final Drawable drawable) {
        mActionBarBackgroundDrawable = drawable;
        mActionBarBackgroundDrawable.setAlpha(0);
        getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);
    }

    public void fadingActionBar(@NonNull final MyScrollView myScrollView, @NonNull final View headerView) {
        myScrollView.setOnScrollViewListener(new MyScrollView.OnScrollViewListener() {
            @Override
            public void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
                final int height = headerView.getHeight() - getSupportActionBar().getHeight();
                final float ratio = (float) Math.min(Math.max(t, 0), height) / height;
                final int alpha = (int) (ratio * 255);
                mActionBarBackgroundDrawable.setAlpha(alpha);
            }
        });
    }

    // TODO fadingActionBar(@NonNull final AbsListView absListView, @NonNull final View headerView)

}
