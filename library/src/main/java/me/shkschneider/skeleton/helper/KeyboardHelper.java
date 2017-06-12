package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import me.shkschneider.skeleton.SkeletonReceiver;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.ui.ViewHelper;

public class KeyboardHelper {

    protected KeyboardHelper() {
        // Empty
    }

    // Show

    public static boolean show(@NonNull final Window window) {
        LogHelper.verbose("SOFT_INPUT_STATE_ALWAYS_VISIBLE");
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return true;
    }

    // Hide

    public static boolean hide(@NonNull final Window window) {
        LogHelper.verbose("SOFT_INPUT_STATE_ALWAYS_HIDDEN");
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return true;
    }

    // ResultReceiver

    public static boolean keyboardCallback(@NonNull final EditText editText, @Nullable final SkeletonReceiver skeletonReceiver, final boolean all) {
        if (skeletonReceiver == null) {
            editText.setOnEditorActionListener(null);
            return false;
        }
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent keyEvent) {
                if (all) {
                    skeletonReceiver.post(ClassHelper.simpleName(KeyboardHelper.class), actionId);
                    return false;
                }
                switch (actionId) {
                    case EditorInfo.IME_NULL:
                        return false;
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_GO:
                    case EditorInfo.IME_ACTION_SEARCH:
                    case EditorInfo.IME_ACTION_SEND:
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        skeletonReceiver.post(ClassHelper.simpleName(KeyboardHelper.class), actionId);
                        break;
                }
                return false;
            }

        });
        return true;
    }

    public static boolean keyboardCallback(@NonNull final EditText editText, @Nullable final SkeletonReceiver skeletonReceiver) {
        return keyboardCallback(editText, skeletonReceiver, false);
    }

    // (Visibility) Listener

    // <https://github.com/yshrsmz/KeyboardVisibilityEvent>
    public static void keyboardListener(@NonNull final Activity activity, @NonNull final Listener listener) {
        final View root = ViewHelper.children(ViewHelper.content(activity)).get(0);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private final Rect mRect = new Rect();
            private final int visibleThreadshold = Math.round(ScreenHelper.pixelsFromDp(100));
            private boolean mWasOpened = false;

            @Override
            public void onGlobalLayout() {
                root.getWindowVisibleDisplayFrame(mRect);
                final int heightDiff = root.getRootView().getHeight() - mRect.height();
                final boolean isOpen = heightDiff > visibleThreadshold;
                if (isOpen == mWasOpened) {
                    return;
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
