package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;

/**
 * SystemUiHelper by Chris Banes
 * <https://gist.github.com/chrisbanes/73de18faffca571f7292>
 *
 * - KITKAT+
 * - JELLY_BEAN+
 * - ICE_CREAM_SANDWICH+
 * - HONEYCOMB+
 */
public final class SystemUiHelper {

    public static final int LEVEL_LOW_PROFILE = 0; // hides StatusBar's buttons & BottomBar's buttons, until triggered (restores all)
    public static final int LEVEL_HIDE_STATUS_BAR = 1; // hides StatusBar & ActionBar, until triggered (restores all)
    public static final int LEVEL_LEAN_BACK = 2; // hides all (with FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES), until triggered back (restores all but offsets the view if ActionBar)
    public static final int LEVEL_IMMERSIVE = 3; // hides all, until triggered back (restores all but offsets the view if ActionBar)

    public static final int FLAG_NONE = 0x0;
    public static final int FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES = 0x1;
    public static final int FLAG_IMMERSIVE_STICKY = 0x2; // hides ActionBar

    private final SystemUiHelperImpl mImpl;

    private final Handler mHandler;
    private final Runnable mHideRunnable;

    public SystemUiHelper(final Activity activity, final int level, final int flags) {
        this(activity, level, flags, null);
    }

    public SystemUiHelper(final Activity activity, final int level, final int flags, final OnVisibilityChangeListener listener) {
        mHandler = new Handler(Looper.getMainLooper());
        mHideRunnable = new HideRunnable();

        if (AndroidHelper.api() >= Build.VERSION_CODES.KITKAT) {
            mImpl = new SystemUiHelperImplKK(activity, level, flags, listener);
        }
        else if (AndroidHelper.api() >= Build.VERSION_CODES.JELLY_BEAN) {
            mImpl = new SystemUiHelperImplJB(activity, level, flags, listener);
        }
        else if (AndroidHelper.api() >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mImpl = new SystemUiHelperImplICS(activity, level, flags, listener);
        }
        else if (AndroidHelper.api() >= Build.VERSION_CODES.HONEYCOMB) {
            mImpl = new SystemUiHelperImplHC(activity, level, flags, listener);
        }
        else {
            mImpl = new SystemUiHelperImplBase(activity, level, flags, listener);
        }
    }

    public boolean isShowing() {
        return mImpl.isShowing();
    }

    public void show() {
        removeQueuedRunnables();
        mImpl.show();
    }

    public void hide() {
        removeQueuedRunnables();
        mImpl.hide();
    }

    public void delayHide(final long delayMillis) {
        removeQueuedRunnables();
        mHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void toggle() {
        if (mImpl.isShowing()) {
            mImpl.hide();
        }
        else {
            mImpl.show();
        }
    }

    private void removeQueuedRunnables() {
        mHandler.removeCallbacks(mHideRunnable);
    }

    public interface OnVisibilityChangeListener {

        public void onVisibilityChange(final boolean visible);

    }

    static abstract class SystemUiHelperImpl {

        protected final Activity mActivity;
        protected final int mLevel;
        protected final int mFlags;
        protected final OnVisibilityChangeListener mOnVisibilityChangeListener;
        protected boolean mIsShowing = true;

        public SystemUiHelperImpl(final Activity activity, final int level, final int flags, final OnVisibilityChangeListener onVisibilityChangeListener) {
            mActivity = activity;
            mLevel = level;
            mFlags = flags;
            mOnVisibilityChangeListener = onVisibilityChangeListener;
        }

        abstract void show();
        abstract void hide();

        boolean isShowing() {
            return mIsShowing;
        }

        void setIsShowing(final boolean isShowing) {
            mIsShowing = isShowing;
            if (mOnVisibilityChangeListener != null) {
                mOnVisibilityChangeListener.onVisibilityChange(mIsShowing);
            }
        }

    }

    static class SystemUiHelperImplBase extends SystemUiHelperImpl {

        public SystemUiHelperImplBase(final Activity activity, final int level, final int flags, final OnVisibilityChangeListener onVisibilityChangeListener) {
            super(activity, level, flags, onVisibilityChangeListener);
            if ((mFlags & SystemUiHelper.FLAG_LAYOUT_IN_SCREEN_OLDER_DEVICES) != 0) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
        }

        @Override
        void show() {
            if (mLevel > SystemUiHelper.LEVEL_LOW_PROFILE) {
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setIsShowing(true);
            }
        }

        @Override
        void hide() {
            if (mLevel > SystemUiHelper.LEVEL_LOW_PROFILE) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setIsShowing(false);
            }
        }

    }

    private class HideRunnable implements Runnable {

        @Override
        public void run() {
            hide();
        }

    }

    // KITKAT

    @TargetApi(Build.VERSION_CODES.KITKAT)
    class SystemUiHelperImplKK extends SystemUiHelperImplJB {

        public SystemUiHelperImplKK(final Activity activity, final int level, final int flags, final SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
            super(activity, level, flags, onVisibilityChangeListener);
        }

        @Override
        protected int createHideFlags() {
            int flag = super.createHideFlags();
            if (mLevel == SystemUiHelper.LEVEL_IMMERSIVE) {
                flag |= (((mFlags & SystemUiHelper.FLAG_IMMERSIVE_STICKY) != 0)
                        ? View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        : View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
            return flag;
        }

    }

    // JELLY_BEAN

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    class SystemUiHelperImplJB extends SystemUiHelperImplICS {

        public SystemUiHelperImplJB(final Activity activity, final int level, final int flags, final SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
            super(activity, level, flags, onVisibilityChangeListener);
        }

        @Override
        protected int createShowFlags() {
            int flag = super.createShowFlags();
            if (mLevel >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR) {
                flag |= (View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                    flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                }
            }
            return flag;
        }

        @Override
        protected int createHideFlags() {
            int flag = super.createHideFlags();
            if (mLevel >= SystemUiHelper.LEVEL_HIDE_STATUS_BAR) {
                flag |= (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
                if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                    flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                }
            }
            return flag;
        }

        @Override
        protected void onSystemUiShown() {
            if (mLevel == SystemUiHelper.LEVEL_LOW_PROFILE) {
                final ActionBar actionBar = mActivity.getActionBar();
                if (actionBar != null) {
                    actionBar.show();
                }
            }
            setIsShowing(true);
        }

        @Override
        protected void onSystemUiHidden() {
            if (mLevel == SystemUiHelper.LEVEL_LOW_PROFILE) {
                final ActionBar actionBar = mActivity.getActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
            }
            setIsShowing(false);
        }

    }

    // ICE_CREAM_SANDWICH

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    class SystemUiHelperImplICS extends SystemUiHelperImplHC {

        public SystemUiHelperImplICS(final Activity activity, final int level, final int flags, final SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
            super(activity, level, flags, onVisibilityChangeListener);
        }

        @Override
        protected int createShowFlags() {
            return View.SYSTEM_UI_FLAG_VISIBLE;
        }

        @Override
        protected int createTestFlags() {
            if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            return View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        @Override
        protected int createHideFlags() {
            int flag = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            if (mLevel >= SystemUiHelper.LEVEL_LEAN_BACK) {
                flag |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            return flag;
        }

    }

    // HONEYCOMB

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class SystemUiHelperImplHC extends SystemUiHelper.SystemUiHelperImpl implements View.OnSystemUiVisibilityChangeListener {

        private final View mDecorView;

        public SystemUiHelperImplHC(final Activity activity, final int level, final int flags, final SystemUiHelper.OnVisibilityChangeListener onVisibilityChangeListener) {
            super(activity, level, flags, onVisibilityChangeListener);
            mDecorView = activity.getWindow().getDecorView();
            mDecorView.setOnSystemUiVisibilityChangeListener(this);
        }


        @Override
        void show() {
            mDecorView.setSystemUiVisibility(createShowFlags());
        }

        @Override
        void hide() {
            mDecorView.setSystemUiVisibility(createHideFlags());
        }

        @Override
        public final void onSystemUiVisibilityChange(final int visibility) {
            if ((visibility & createTestFlags()) != 0) {
                onSystemUiHidden();
            }
            else {
                onSystemUiShown();
            }
        }

        protected void onSystemUiShown() {
            final ActionBar actionBar = mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setIsShowing(true);
        }

        protected void onSystemUiHidden() {
            final ActionBar actionBar = mActivity.getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setIsShowing(false);
        }

        protected int createShowFlags() {
            return View.STATUS_BAR_VISIBLE;
        }

        protected int createHideFlags() {
            return View.STATUS_BAR_HIDDEN;
        }

        protected int createTestFlags() {
            return View.STATUS_BAR_HIDDEN;
        }

    }

}
