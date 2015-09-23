package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class KeyboardHelper {

    protected KeyboardHelper() {
        // Empty
    }

    // Show

    public static boolean show(@NonNull final Activity activity) {
        LogHelper.verbose("SOFT_INPUT_STATE_ALWAYS_VISIBLE");
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return true;
    }

    // Hide

    public static boolean hide(@NonNull final Activity activity) {
        LogHelper.verbose("hideSoftInputFromWindow()");
        InputMethodManager inputMethodManager = SystemServices.inputMethodManager();
        if (inputMethodManager == null) {
            LogHelper.warning("InputMethodManager was NULL");
            return false;
        }
        else {
            return inputMethodManager.hideSoftInputFromWindow(ActivityHelper.contentView(activity).getWindowToken(), 0);
        }
    }

    @Deprecated
    public static boolean hide(@NonNull final Window window) {
        LogHelper.verbose("SOFT_INPUT_STATE_ALWAYS_HIDDEN");
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return true;
    }

    // (Input) Callback

    public final static int ENTER = KeyEvent.KEYCODE_ENTER;

    public static boolean keyboardCallback(@NonNull final EditText editText, @Nullable final Callback callback, final boolean all) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL) {
                    return false;
                }

                final Integer[] enterKeys = {
                        // ImeOptions
                        EditorInfo.IME_ACTION_DONE,
                        EditorInfo.IME_ACTION_GO,
                        EditorInfo.IME_ACTION_SEARCH,
                        EditorInfo.IME_ACTION_SEND,
                        // Trackball
                        KeyEvent.KEYCODE_DPAD_CENTER,
                        // Enter
                        KeyEvent.KEYCODE_ENTER
                };
                if (all || Arrays.asList(enterKeys).contains(actionId)) {
                    if (callback != null) {
                        callback.onKeyboardAction(actionId);
                    }
                }

                return false;
            }

        });
        return true;
    }

    public static boolean keyboardCallback(@NonNull final EditText editText, @Nullable final Callback keyboardCallback) {
        return keyboardCallback(editText, keyboardCallback, false);
    }

    public interface Callback {

        void onKeyboardAction(@IntRange(from=0) final int action);

    }

    // (Visibility) Listener

    // <https://github.com/yshrsmz/KeyboardVisibilityEvent>
    public static void keyboardListener(@NonNull final Activity activity, @NonNull final Listener listener) {
        final View activityRoot = ((ViewGroup) ActivityHelper.contentView(activity)).getChildAt(0);
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private final Rect mRect = new Rect();
            private final int visibleThreadshold = Math.round(ScreenHelper.pixelsFromDp(100));
            private boolean mWasOpened = false;

            @Override
            public void onGlobalLayout() {
                activityRoot.getWindowVisibleDisplayFrame(mRect);
                final int heightDiff = activityRoot.getRootView().getHeight() - mRect.height();
                final boolean isOpen = heightDiff > visibleThreadshold;
                if (isOpen == mWasOpened) {
                    return ;
                }
                mWasOpened = isOpen;
                listener.onKeyboardVisibilityChanged(isOpen);
            }

        });
    }

    public interface Listener {

        void onKeyboardVisibilityChanged(final boolean isOpen);

    }

}
