package me.shkschneider.skeleton;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("unused")
public class Keyboards {

    // Behavior can vary

    public static void show(final Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) Systems.systemService(activity, Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
        else {
            Log.d("InputMethodManager was NULL");
        }
    }

    // Behavior can vary

    public static void hide(final Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) Systems.systemService(activity, Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            final View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            else {
                Log.d("View was NULL");
            }
        }
        else {
            Log.d("InputMethodManager was NULL");
        }
    }

    public static void keyboardCallback(final EditText editText, final KeyboardCallback callback) {
        if (editText != null) {
            if (callback != null) {
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(final TextView textView, final int i, final KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            callback.keyboardCallback();
                        }
                        // Keep false
                        return false;
                    }

                });
            }
            else {
                Log.w("KeyboardCallback was NULL");
            }
        }
        else {
            Log.w("EditText was NULL");
        }
    }

    public static interface KeyboardCallback {

        public void keyboardCallback();

    }

}
