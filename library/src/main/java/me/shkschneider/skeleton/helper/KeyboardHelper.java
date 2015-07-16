package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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

    public final static int ENTER = KeyEvent.KEYCODE_ENTER;

    public static boolean show(@NonNull final Activity activity) {
        LogHelper.verbose("SOFT_INPUT_STATE_ALWAYS_VISIBLE");
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return true;
    }

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

    public static boolean keyboardCallback(@NonNull final EditText editText, @Nullable final Callback keyboardCallback, final boolean all) {
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
                    if (keyboardCallback != null) {
                        keyboardCallback.keyboardCallback(actionId);
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

        void keyboardCallback(@IntRange(from=0) final int action);

    }

}
