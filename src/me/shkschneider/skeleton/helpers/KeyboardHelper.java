package me.shkschneider.skeleton.helpers;

import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

@SuppressWarnings("unused")
public final class KeyboardHelper {

    public static boolean show(final Window window, final EditText editText) {
        if (editText == null) {
            LogHelper.warning("EditText was NULL");
            return false;
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    show(window);
                }
            }

        });
        return true;
    }

    public static boolean show(final Window window) {
        if (window == null) {
            LogHelper.warning("Window was NULL");
            return false;
        }

        LogHelper.debug("Show keyboard");
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return true;
    }

    public static boolean hide(final Window window) {
        if (window == null) {
            LogHelper.warning("Window was NULL");
            return false;
        }

        LogHelper.debug("Hide keyboard");
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return true;
    }

    public static boolean hide(final IBinder windowToken) {
        final InputMethodManager inputMethodManager = (InputMethodManager) SystemHelper.systemService(SystemHelper.SYSTEM_SERVICE_INPUT_METHOD);
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
    }

    public static boolean keyboardCallback(final EditText editText, final Callback keyboardCallback, final boolean all) {
        if (editText == null) {
            LogHelper.warning("EditText was NULL");
            return false;
        }
        if (keyboardCallback == null) {
            LogHelper.info("Receiver was NULL");
            return false;
        }

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(final TextView textView, final int i, final KeyEvent keyEvent) {
                // Must return false

                final int action = keyEvent.getAction();
                if (action == EditorInfo.IME_NULL) {
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
                if (all || Arrays.asList(enterKeys).contains(action)) {
                    keyboardCallback.keyboardCallback(action);
                }

                return false;
            }

        });
        return true;
    }

    public static boolean keyboardCallback(final EditText editText, final Callback keyboardCallback) {
        return keyboardCallback(editText, keyboardCallback, false);
    }

    public static interface Callback {

        public void keyboardCallback(final int action);

    }

}
