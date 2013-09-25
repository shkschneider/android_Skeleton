package me.shkschneider.skeleton;

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
public class Activities {

    public static void indeterminate(final Object activity) {
        if (activity != null) {
            if (activity instanceof SherlockActivity) {
                ((SherlockActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
                ((SherlockActivity) activity).setSupportProgressBarIndeterminate(true);
            }
            else if (activity instanceof SherlockListActivity) {
                ((SherlockListActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
                ((SherlockListActivity) activity).setSupportProgressBarIndeterminate(true);
            }
            else if (activity instanceof SherlockFragmentActivity) {
                ((SherlockFragmentActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
                ((SherlockFragmentActivity) activity).setSupportProgressBarIndeterminate(true);
            }
            else if (activity instanceof SherlockPreferenceActivity) {
                ((SherlockPreferenceActivity) activity).requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
                ((SherlockPreferenceActivity) activity).setSupportProgressBarIndeterminate(true);
            }
            else {
                Log.w("Activity was not Sherlock-based");
            }
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void indeterminate(final Object activity, final Boolean on) {
        if (activity != null) {
            if (activity instanceof SherlockActivity) {
                ((SherlockActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            }
            else if (activity instanceof SherlockListActivity) {
                ((SherlockListActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            }
            else if (activity instanceof SherlockFragmentActivity) {
                ((SherlockFragmentActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            }
            else if (activity instanceof SherlockPreferenceActivity) {
                ((SherlockPreferenceActivity) activity).setSupportProgressBarIndeterminateVisibility(on);
            }
            else {
                Log.w("Activity was not Sherlock-based");
            }
        }
        else {
            Log.w("Activity was NULL");
        }
    }

    public static void error(final Context context, final String message, final DialogInterface.OnClickListener onClickListener) {
        if (context != null) {
            if (! TextUtils.isEmpty(message)) {
                alertDialogBuilder(context)
                        .setMessage(message)
                        .setNeutralButton(android.R.string.ok, onClickListener)
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else {
                Log.w("Message was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
    }

    public static void error(final Context context, final String message) {
        error(context, message, null);
    }

    public static void showcase(final Activity activity, final int id, final String title, final String message, final ShowcaseCallback callback) {
        if (activity != null) {
            if (id > 0) {
                if (! TextUtils.isEmpty(title)) {
                    if (! TextUtils.isEmpty(message)) {
                        final ShowcaseView.ConfigOptions configOptions = new ShowcaseView.ConfigOptions();
                        final ShowcaseView showcaseView = ShowcaseView.insertShowcaseView(android.R.id.home,
                                activity,
                                title,
                                message,
                                configOptions);
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
                    }
                    else {
                        Log.w("Message was NULL");
                    }
                }
                else {
                    Log.w("Title was NULL");
                }
            }
            else {
                Log.w("Id was invalid");
            }
        }
        else {
            Log.w("Activity was NULL");
        }

    }

    public static void showcase(final Activity activity, final int id, final String title, final String message) {
        showcase(activity, id, title, message, null);
    }

    public interface ShowcaseCallback {

        public void showCaseCallback();

    }

    public static AlertDialog.Builder alertDialogBuilder(final Context context, final int style) {
        if (context != null) {
            return new AlertDialog.Builder(new ContextThemeWrapper(context, style));
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static AlertDialog.Builder alertDialogBuilder(final Context context) {
        if (context != null) {
            return new AlertDialog.Builder(context);
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static AlertDialog alertDialog(final Context context, final int style) {
        if (context != null) {
            final AlertDialog.Builder alertDialog = alertDialogBuilder(context, style);
            if (alertDialog != null) {
                return alertDialog.create();
            }
            else {
                Log.w("AlertDialog.Builder was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

    public static AlertDialog alertDialog(final Context context) {
        if (context != null) {
            final AlertDialog.Builder alertDialog = alertDialogBuilder(context);
            if (alertDialog != null) {
                return alertDialog.create();
            }
            else {
                Log.w("AlertDialog.Builder was NULL");
            }
        }
        else {
            Log.w("Context was NULL");
        }
        return null;
    }

}
