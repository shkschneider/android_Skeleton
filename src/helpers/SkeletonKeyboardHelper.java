/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import me.shkschneider.skeleton.SkeletonLog;

public abstract class SkeletonKeyboardHelper {

    // Behavior can vary

    public static void showKeyboard(final Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        /*
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            final View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            }
            else {
                SkeletonLog.d("View was NULL");
            }
        }
        else {
            SkeletonLog.d("InputMethodManager was NULL");
        }
        */
    }

    // Behavior can vary

    public static void hideKeyboard(final Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            final View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            else {
                SkeletonLog.d("View was NULL");
            }
        }
        else {
            SkeletonLog.d("InputMethodManager was NULL");
        }
        */
    }

    public static void callback(final EditText editText, final Callback callback) {
        if (editText != null) {
            if (callback != null) {
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(final TextView textView, final int i, final KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                                (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            callback.KeyboardCallback();
                        }
                        // Keep false
                        return false;
                    }

                });
            }
            else {
                SkeletonLog.d("Callback was NULL");
            }
        }
        else {
            SkeletonLog.d("EditText was NULL");
        }
    }

    public static interface Callback {

        public void KeyboardCallback();

    }

}
