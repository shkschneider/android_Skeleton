package me.shkschneider.skeleton;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.ui.MyScrollView;

// <http://cyrilmottier.com/2013/05/24/pushing-the-actionbar-to-the-next-level>
public class SkeletonOverlayActivity extends SkeletonActivity {

    private Drawable mActionBarBackgroundDrawable;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        overlay(new ColorDrawable(getResources().getColor(R.color.actionBarColor)));
    }

    public void overlay(@NonNull final Drawable drawable) {
        mActionBarBackgroundDrawable = drawable;
        mActionBarBackgroundDrawable.setAlpha(0);
        if (AndroidHelper.api() >= AndroidHelper.API_16) {
            overlay16(mActionBarBackgroundDrawable);
        }
        else {
            overlay14(mActionBarBackgroundDrawable);
        }
    }

    @TargetApi(AndroidHelper.API_16)
    private void overlay16(@NonNull final Drawable drawable) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL");
            return ;
        }
        mToolbar.setBackground(drawable);
    }

    @SuppressWarnings("deprecation")
    private void overlay14(@NonNull final Drawable drawable) {
        if (mToolbar == null) {
            LogHelper.warning("Toolbar was NULL");
            return ;
        }
        mToolbar.setBackgroundDrawable(drawable);
    }

    public void overlay(@NonNull final MyScrollView myScrollView, @NonNull final View headerView) {
        if (getSupportActionBar() == null) {
            LogHelper.warning("ActionBar was NULL");
            return ;
        }
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

    // TODO overlay(@NonNull final AbsListView absListView, @NonNull final View headerView)

}
