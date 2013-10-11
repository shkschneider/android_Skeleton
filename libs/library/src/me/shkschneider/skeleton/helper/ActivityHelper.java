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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

@SuppressWarnings("unused")
public class ActivityHelper {

    public static Boolean popup(final Context context, final String message, final DialogInterface.OnClickListener onClickListener) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return false;
        }

        if (TextUtils.isEmpty(message)) {
            LogHelper.w("Message was NULL");
            return false;
        }

        alertDialogBuilder(context)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, onClickListener)
                .setCancelable(false)
                .create()
                .show();
        return true;
    }

    public static Boolean popup(final Context context, final String message) {
        return popup(context, message, null);
    }

    public static AlertDialog.Builder alertDialogBuilder(final Context context, final int style) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        return new AlertDialog.Builder(new ContextThemeWrapper(context, style));
    }

    public static AlertDialog.Builder alertDialogBuilder(final Context context) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return null;
        }

        return new AlertDialog.Builder(context);
    }

}
