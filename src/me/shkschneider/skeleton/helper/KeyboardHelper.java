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
package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("unused")
public class KeyboardHelper {

    // Behavior can vary

    public static Boolean show(final Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) SystemHelper.systemService(activity, Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        return true;
    }

    // Behavior can vary

    public static Boolean hide(final Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) SystemHelper.systemService(activity, Context.INPUT_METHOD_SERVICE);
        final View view = activity.getCurrentFocus();
        if (view == null) {
            LogHelper.d("View was NULL");
            return false;
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        return true;
    }

    public static Boolean keyboardCallback(final EditText editText, final Callback callback) {
        if (editText == null) {
            LogHelper.w("EditText was NULL");
            return false;
        }

        if (callback == null) {
            LogHelper.w("Callback was NULL");
            return false;
        }

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
        return true;
    }

    public static interface Callback {

        public void keyboardCallback();

    }

}
