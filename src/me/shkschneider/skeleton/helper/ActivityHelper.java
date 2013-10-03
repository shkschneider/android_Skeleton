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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Window;
import com.github.espiandev.showcaseview.ShowcaseView;

@SuppressWarnings("unused")
public class ActivityHelper {

    public static Boolean indeterminate(final Object activity) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        if (activity instanceof SherlockActivity) {
            ((SherlockActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            ((SherlockActivity) activity).setSupportProgressBarIndeterminate(true);
            return true;
        }
        else if (activity instanceof SherlockListActivity) {
            ((SherlockListActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            ((SherlockListActivity) activity).setSupportProgressBarIndeterminate(true);
            return true;
        }
        else if (activity instanceof SherlockFragmentActivity) {
            ((SherlockFragmentActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            ((SherlockFragmentActivity) activity).setSupportProgressBarIndeterminate(true);
            return true;
        }
        else if (activity instanceof SherlockPreferenceActivity) {
            ((SherlockPreferenceActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            ((SherlockPreferenceActivity) activity).setSupportProgressBarIndeterminate(true);
            return true;
        }
        else {
            LogHelper.w("Activity does not support FEATURE_INDETERMINATE_PROGRESS");
            return false;
        }
    }

    public static Boolean indeterminate(final Object activity, final Boolean on) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }

        if (activity instanceof SherlockActivity) {
            ((SherlockActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            return true;
        }
        else if (activity instanceof SherlockListActivity) {
            ((SherlockListActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            return true;
        }
        else if (activity instanceof SherlockFragmentActivity) {
            ((SherlockFragmentActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            return true;
        }
        else if (activity instanceof SherlockPreferenceActivity) {
            ((SherlockPreferenceActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            return true;
        }
        else {
            LogHelper.w("Activity does not support FEATURE_INDETERMINATE_PROGRESS");
            return false;
        }
    }

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

    public static Boolean showcase(final Activity activity, final int id, final String title, final String message, final ShowcaseCallback callback) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }
        if (id <= 0) {
            LogHelper.w("Id was invalid");
            return false;
        }
        if (TextUtils.isEmpty(title)) {
            LogHelper.w("Title was NULL");
            return false;
        }
        if (TextUtils.isEmpty(message)) {
            LogHelper.w("Message was NULL");
            return false;
        }

        final ShowcaseView.ConfigOptions configOptions = new ShowcaseView.ConfigOptions();
        final ShowcaseView showcaseView = ShowcaseView.insertShowcaseView(android.R.id.home, activity, title, message, configOptions);
        showcaseView.setBackgroundColor(Color.parseColor("#AA000000"));
        if (callback != null) {
            showcaseView.setOnShowcaseEventListener(new ShowcaseView.OnShowcaseEventListener() {

                @Override
                public void onShowcaseViewHide(final ShowcaseView showcaseView) {
                    callback.showCaseCallback();
                }

                @Override
                public void onShowcaseViewShow(final ShowcaseView showcaseView) {
                }

            });
        }
        showcaseView.show();
        return true;
    }

    public static Boolean showcase(final Activity activity, final int id, final String title, final String message) {
        return showcase(activity, id, title, message, null);
    }

    public interface ShowcaseCallback {

        public void showCaseCallback();

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
