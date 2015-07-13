package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.List;

import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.UrlHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.data.MimeTypeHelper;

// <http://developer.android.com/reference/android/content/Intent.html>
public class IntentHelper {

    protected IntentHelper() {
        // Empty
    }

    public static final int FLAGS_HOME = (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    public static final int FLAGS_CLEAR = (Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);

    // Useless: only used to keep track of new features
    public static final String BROADCAST_AIRPLANE_MODE_CHANGED = Intent.ACTION_AIRPLANE_MODE_CHANGED;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BROADCAST_APPLICATION_RESTRICTIONS_CHANGED = Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED;
    public static final String BROADCAST_BATTERY_CHANGED = Intent.ACTION_BATTERY_CHANGED;
    public static final String BROADCAST_BATTERY_LOW = Intent.ACTION_BATTERY_LOW;
    public static final String BROADCAST_BATTERY_OKAY = Intent.ACTION_BATTERY_OKAY;
    public static final String BROADCAST_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;
    public static final String BROADCAST_CAMERA_BUTTON = Intent.ACTION_CAMERA_BUTTON;
    public static final String BROADCAST_CLOSE_SYSTEM_DIALOGS = Intent.ACTION_CLOSE_SYSTEM_DIALOGS;
    public static final String BROADCAST_CONFIGURATION_CHANGED = Intent.ACTION_CONFIGURATION_CHANGED;
    public static final String BROADCAST_DATE_CHANGED = Intent.ACTION_DATE_CHANGED;
    @Deprecated
    public static final String BROADCAST_DEVICE_STORAGE_LOW = Intent.ACTION_DEVICE_STORAGE_LOW;
    @Deprecated
    public static final String BROADCAST_DEVICE_STORAGE_OK = Intent.ACTION_DEVICE_STORAGE_OK;
    public static final String BROADCAST_DOCK_EVENT = Intent.ACTION_DOCK_EVENT;
    @SuppressLint("InlinedApi") // API-17+
    public static final String BROADCAST_DREAMING_STARTED = Intent.ACTION_DREAMING_STARTED;
    @SuppressLint("InlinedApi") // API-17+
    public static final String BROADCAST_DREAMING_STOPPED = Intent.ACTION_DREAMING_STOPPED;
    public static final String BROADCAST_EXTERNAL_APPLICATIONS_AVAILABLE = Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE;
    public static final String BROADCAST_EXTERNAL_APPLICATIONS_UNAVAILABLE = Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE;
    @SuppressLint("InlinedApi") // API-18+
    public static final String BROADCAST_GET_RESTRICTION_ENTRIES = Intent.ACTION_GET_RESTRICTION_ENTRIES;
    public static final String BROADCAST_GTALK_SERVICE_CONNECTED = Intent.ACTION_GTALK_SERVICE_CONNECTED;
    public static final String BROADCAST_GTALK_SERVICE_DISCONNECTED = Intent.ACTION_GTALK_SERVICE_DISCONNECTED;
    public static final String BROADCAST_HEADSET_PLUG = Intent.ACTION_HEADSET_PLUG;
    public static final String BROADCAST_INPUT_METHOD_CHANGED = Intent.ACTION_INPUT_METHOD_CHANGED;
    public static final String BROADCAST_LOCALE_CHANGED = Intent.ACTION_LOCALE_CHANGED;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BROADCAST_MANAGED_PROFILE_ADDED = Intent.ACTION_MANAGED_PROFILE_ADDED;
    @SuppressLint("InlinedApi") // API-21+
    public static final String BROADCAST_MANAGED_PROFILE_REMOVED = Intent.ACTION_MANAGED_PROFILE_REMOVED;
    public static final String BROADCAST_MANAGE_PACKAGE_STORAGE = Intent.ACTION_MANAGE_PACKAGE_STORAGE;
    public static final String BROADCAST_MEDIA_BAD_REMOVAL = Intent.ACTION_MEDIA_BAD_REMOVAL;
    public static final String BROADCAST_MEDIA_BUTTON = Intent.ACTION_MEDIA_BUTTON;
    public static final String BROADCAST_MEDIA_CHECKING = Intent.ACTION_MEDIA_CHECKING;
    public static final String BROADCAST_MEDIA_EJECT = Intent.ACTION_MEDIA_EJECT;
    public static final String BROADCAST_MEDIA_MOUNTED = Intent.ACTION_MEDIA_MOUNTED;
    public static final String BROADCAST_MEDIA_NOFS = Intent.ACTION_MEDIA_NOFS;
    public static final String BROADCAST_MEDIA_REMOVED = Intent.ACTION_MEDIA_REMOVED;
    public static final String BROADCAST_MEDIA_SCANNER_FINISHED = Intent.ACTION_MEDIA_SCANNER_FINISHED;
    public static final String BROADCAST_MEDIA_SCANNER_SCAN_FILE = Intent.ACTION_MEDIA_SCANNER_SCAN_FILE;
    public static final String BROADCAST_MEDIA_SCANNER_STARTED = Intent.ACTION_MEDIA_SCANNER_STARTED;
    public static final String BROADCAST_MEDIA_SHARED = Intent.ACTION_MEDIA_SHARED;
    public static final String BROADCAST_MEDIA_UNMOUNTABLE = Intent.ACTION_MEDIA_UNMOUNTABLE;
    public static final String BROADCAST_MEDIA_UNMOUNTED = Intent.ACTION_MEDIA_UNMOUNTED;
    public static final String BROADCAST_MY_PACKAGE_REPLACED = Intent.ACTION_MY_PACKAGE_REPLACED;
    public static final String BROADCAST_NEW_OUTGOING_CALL = Intent.ACTION_NEW_OUTGOING_CALL;
    public static final String BROADCAST_PACKAGE_ADDED = Intent.ACTION_PACKAGE_ADDED;
    public static final String BROADCAST_PACKAGE_CHANGED = Intent.ACTION_PACKAGE_CHANGED;
    public static final String BROADCAST_PACKAGE_DATA_CLEARED = Intent.ACTION_PACKAGE_DATA_CLEARED;
    public static final String BROADCAST_PACKAGE_FIRST_LAUNCH = Intent.ACTION_PACKAGE_FIRST_LAUNCH;
    public static final String BROADCAST_PACKAGE_FULLY_REMOVED = Intent.ACTION_PACKAGE_FULLY_REMOVED;
    public static final String BROADCAST_PACKAGE_NEEDS_VERIFICATION = Intent.ACTION_PACKAGE_NEEDS_VERIFICATION;
    public static final String BROADCAST_PACKAGE_REMOVED = Intent.ACTION_PACKAGE_REMOVED;
    public static final String BROADCAST_PACKAGE_REPLACED = Intent.ACTION_PACKAGE_REPLACED;
    public static final String BROADCAST_PACKAGE_RESTARTED = Intent.ACTION_PACKAGE_RESTARTED;
    @SuppressLint("InlinedApi") // API-17+
    public static final String BROADCAST_PACKAGE_VERIFIED = Intent.ACTION_PACKAGE_VERIFIED;
    public static final String BROADCAST_POWER_CONNECTED = Intent.ACTION_POWER_CONNECTED;
    public static final String BROADCAST_POWER_DISCONNECTED = Intent.ACTION_POWER_DISCONNECTED;
    public static final String BROADCAST_PROVIDER_CHANGED = Intent.ACTION_PROVIDER_CHANGED;
    public static final String BROADCAST_REBOOT = Intent.ACTION_REBOOT;
    public static final String BROADCAST_SCREEN_OFF = Intent.ACTION_SCREEN_OFF;
    public static final String BROADCAST_SCREEN_ON = Intent.ACTION_SCREEN_ON;
    public static final String BROADCAST_SHUTDOWN = Intent.ACTION_SHUTDOWN;
    public static final String BROADCAST_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;
    public static final String BROADCAST_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
    public static final String BROADCAST_TIME_TICK = Intent.ACTION_TIME_TICK;
    public static final String BROADCAST_UID_REMOVED = Intent.ACTION_UID_REMOVED;
    public static final String BROADCAST_USER_PRESENT = Intent.ACTION_USER_PRESENT;

    // Useless: only used to keep track of new features
    public static final String ACTION_ALL_APPS = Intent.ACTION_ALL_APPS;
    public static final String ACTION_ANSWER = Intent.ACTION_ANSWER;
    public static final String ACTION_APP_ERROR = Intent.ACTION_APP_ERROR;
    @SuppressLint("InlinedApi") // API-16+
    public static final String ACTION_ASSIST = Intent.ACTION_ASSIST;
    public static final String ACTION_BUG_REPORT = Intent.ACTION_BUG_REPORT;
    public static final String ACTION_CALL = Intent.ACTION_CALL;
    public static final String ACTION_CALL_BUTTON = Intent.ACTION_CALL_BUTTON;
    public static final String ACTION_CHOOSER = Intent.ACTION_CHOOSER;
    @SuppressLint("InlinedApi") // API-19+
    public static final String ACTION_CREATE_DOCUMENT = Intent.ACTION_CREATE_DOCUMENT;
    public static final String ACTION_CREATE_SHORTCUT = Intent.ACTION_CREATE_SHORTCUT;
    public static final String ACTION_DELETE = Intent.ACTION_DELETE;
    public static final String ACTION_DIAL = Intent.ACTION_DIAL;
    public static final String ACTION_EDIT = Intent.ACTION_EDIT;
    public static final String ACTION_FACTORY_TEST = Intent.ACTION_FACTORY_TEST;
    public static final String ACTION_GET_CONTENT = Intent.ACTION_GET_CONTENT;
    public static final String ACTION_INSERT = Intent.ACTION_INSERT;
    public static final String ACTION_INSERT_OR_EDIT = Intent.ACTION_INSERT_OR_EDIT;
    public static final String ACTION_INSTALL_PACKAGE = Intent.ACTION_INSTALL_PACKAGE;
    public static final String ACTION_MAIN = Intent.ACTION_MAIN;
    public static final String ACTION_MANAGE_NETWORK_USAGE = Intent.ACTION_MANAGE_NETWORK_USAGE;
    @SuppressLint("InlinedApi") // API-19+
    public static final String ACTION_OPEN_DOCUMENT = Intent.ACTION_OPEN_DOCUMENT;
    @SuppressLint("InlinedApi") // API-21+
    public static final String ACTION_OPEN_DOCUMENT_TREE = Intent.ACTION_OPEN_DOCUMENT_TREE;
    public static final String ACTION_PASTE = Intent.ACTION_PASTE;
    public static final String ACTION_PICK = Intent.ACTION_PICK;
    public static final String ACTION_PICK_ACTIVITY = Intent.ACTION_PICK_ACTIVITY;
    public static final String ACTION_POWER_USAGE_SUMMARY = Intent.ACTION_POWER_USAGE_SUMMARY;
    public static final String ACTION_RUN = Intent.ACTION_RUN;
    public static final String ACTION_SEARCH = Intent.ACTION_SEARCH;
    public static final String ACTION_SEARCH_LONG_PRESS = Intent.ACTION_SEARCH_LONG_PRESS;
    public static final String ACTION_SEND = Intent.ACTION_SEND;
    public static final String ACTION_SENDTO = Intent.ACTION_SENDTO;
    public static final String ACTION_SEND_MULTIPLE = Intent.ACTION_SEND_MULTIPLE;
    public static final String ACTION_SET_WALLPAPER = Intent.ACTION_SET_WALLPAPER;
    public static final String ACTION_SYNC = Intent.ACTION_SYNC;
    public static final String ACTION_SYSTEM_TUTORIAL = Intent.ACTION_SYSTEM_TUTORIAL;
    public static final String ACTION_UNINSTALL_PACKAGE = Intent.ACTION_UNINSTALL_PACKAGE;
    public static final String ACTION_VIEW = Intent.ACTION_VIEW;
    public static final String ACTION_VOICE_COMMAND = Intent.ACTION_VOICE_COMMAND;
    public static final String ACTION_WEB_SEARCH = Intent.ACTION_WEB_SEARCH;

    // Useless: only used to keep track of new features
    public static final String CATEGORY_ALTERNATIVE = Intent.CATEGORY_ALTERNATIVE;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_BROWSER = Intent.CATEGORY_APP_BROWSER;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_CALCULATOR = Intent.CATEGORY_APP_CALCULATOR;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_CALENDAR = Intent.CATEGORY_APP_CALENDAR;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_CONTACTS = Intent.CATEGORY_APP_CONTACTS;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_EMAIL = Intent.CATEGORY_APP_EMAIL;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_GALLERY = Intent.CATEGORY_APP_GALLERY;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_MAPS = Intent.CATEGORY_APP_MAPS;
    public static final String CATEGORY_APP_MARKET = Intent.CATEGORY_APP_MARKET;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_MESSAGING = Intent.CATEGORY_APP_MESSAGING;
    @SuppressLint("InlinedApi") // API-15+
    public static final String CATEGORY_APP_MUSIC = Intent.CATEGORY_APP_MUSIC;
    public static final String CATEGORY_BROWSABLE = Intent.CATEGORY_BROWSABLE;
    public static final String CATEGORY_CAR_DOCK = Intent.CATEGORY_CAR_DOCK;
    public static final String CATEGORY_CAR_MODE = Intent.CATEGORY_CAR_MODE;
    public static final String CATEGORY_DEFAULT = Intent.CATEGORY_DEFAULT;
    public static final String CATEGORY_DESK_DOCK = Intent.CATEGORY_DESK_DOCK;
    public static final String CATEGORY_DEVELOPMENT_PREFERENCE = Intent.CATEGORY_DEVELOPMENT_PREFERENCE;
    public static final String CATEGORY_EMBED = Intent.CATEGORY_EMBED;
    public static final String CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST = Intent.CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST;
    public static final String CATEGORY_HE_DESK_DOCK = Intent.CATEGORY_HE_DESK_DOCK;
    public static final String CATEGORY_HOME = Intent.CATEGORY_HOME;
    public static final String CATEGORY_INFO = Intent.CATEGORY_INFO;
    public static final String CATEGORY_LAUNCHER = Intent.CATEGORY_LAUNCHER;
    @SuppressLint("InlinedApi") // API-21+
    public static final String CATEGORY_LEANBACK_LAUNCHER = Intent.CATEGORY_LEANBACK_LAUNCHER;
    public static final String CATEGORY_LE_DESK_DOCK = Intent.CATEGORY_LE_DESK_DOCK;
    public static final String CATEGORY_MONKEY = Intent.CATEGORY_MONKEY;
    public static final String CATEGORY_OPENABLE = Intent.CATEGORY_OPENABLE;
    public static final String CATEGORY_PREFERENCE = Intent.CATEGORY_PREFERENCE;
    @Deprecated
    public static final String CATEGORY_SAMPLE_CODE = Intent.CATEGORY_SAMPLE_CODE;
    public static final String CATEGORY_SELECTED_ALTERNATIVE = Intent.CATEGORY_SELECTED_ALTERNATIVE;
    public static final String CATEGORY_TAB = Intent.CATEGORY_TAB;
    @Deprecated
    public static final String CATEGORY_TEST = Intent.CATEGORY_TEST;
    @Deprecated
    public static final String CATEGORY_UNIT_TEST = Intent.CATEGORY_UNIT_TEST;

    public static final int REQUEST_CODE_CAMERA = 111;
    public static final int REQUEST_CODE_GALLERY = 222;

    @Nullable
    public static Intent home() {
        final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
        if (packageManager == null) {
            LogHelper.w("PackageManager was NULL");
            return null;
        }

        final Intent intent = packageManager.getLaunchIntentForPackage(ApplicationHelper.packageName());
        if (intent == null) {
            LogHelper.w("Intent was NULL");
            return null;
        }

        return intent.setFlags(FLAGS_HOME);
    }

    public static Intent view(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Nullable
    public static Intent web(@NonNull final String url) {
        if (! UrlHelper.valid(url)) {
            LogHelper.w("Url was invalid");
            return null;
        }

        return external(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static Intent share(final String subject, final String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND)
                .setType(MimeTypeHelper.TEXT_PLAIN);
        if (! StringHelper.nullOrEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (! StringHelper.nullOrEmpty(text)) {
            intent.putExtra(Intent.EXTRA_TEXT, text);
        }
        return Intent.createChooser(intent, null);
    }

    public static Intent directions(@NonNull final LatLng from, @NonNull final LatLng to) {
        return external(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                        from.latitude, from.longitude,
                        to.latitude, to.longitude))));
    }

    public static Intent applicationSettings() {
        return external(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + ApplicationHelper.packageName())));
    }

    public static Intent systemSettings() {
        return external(new Intent(Settings.ACTION_SETTINGS));
    }

    public static Intent text(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.TEXT_PLAIN));
    }

    public static Intent audio(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.AUDIO));
    }

    public static Intent video(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.VIDEO));
    }

    public static Intent picture(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.IMAGE));
    }

    public static Intent gallery() {
        return external(new Intent(Intent.ACTION_PICK)
                .setType(MimeTypeHelper.IMAGE));
    }

    @Nullable
    public static Intent camera(@NonNull final File file) {
        if (! FeaturesHelper.feature(FeaturesHelper.FEATURE_CAMERA)) {
            LogHelper.w("Camera was unavailable");
            return null;
        }

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                .putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, true);
        if (! canHandle(intent)) {
            LogHelper.w("Cannot handle Intent");
            return null;
        }

        return external(intent);
    }

    public static Intent file() {
        return external(new Intent(Intent.ACTION_GET_CONTENT)
                .setType(MimeTypeHelper.FILE));
    }

    public static Intent dial(@NonNull final String phone) {
        return external(new Intent(Intent.ACTION_DIAL)
                .setData(Uri.parse("tel:" + phone)));
    }

    public static Intent call(@NonNull final String phone) {
        return external(new Intent(Intent.ACTION_CALL)
                .setData(Uri.parse("tel:" + phone)));
    }

    public static Intent contact() {
        return external(new Intent(Intent.ACTION_PICK, Uri.parse("content://com.android.contacts/contacts"))
                .setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE));
    }

    public static class GooglePlay {

        public static Intent application(@NonNull final String packageName) {
            return external(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        }

        public static Intent publisher(@NonNull final String pub) {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?pub:" + pub));
            return external(intent);
        }

        public static Intent search(@NonNull final String q) {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=" + q));
            return external(intent);
        }

    }

    public static boolean canHandle(@NonNull final Intent intent) {
        final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
        if (packageManager == null) {
            LogHelper.w("PackageManager was NULL");
            return false;
        }

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    @Nullable
    public static Bitmap onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            LogHelper.d("ResultCode was not OK");
            return null;
        }

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (intent == null) {
                LogHelper.w("Intent was NULL");
                return null;
            }
            final Bundle bundle = intent.getExtras();
            if (bundle == null) {
                LogHelper.w("Bundle was NULL");
                return null;
            }

            return (Bitmap) bundle.get("data");
        }
        else if (requestCode == REQUEST_CODE_GALLERY) {
            if (intent == null) {
                LogHelper.w("Intent was NULL");
                return null;
            }
            final Uri uri = intent.getData();
            if (uri == null) {
                LogHelper.w("Uri was NULL");
                return null;
            }

            return BitmapHelper.decodeUri(uri);
        }
        return null;
    }

    // <http://developer.android.com/training/implementing-navigation/descendant.html#external-activities>
    private static Intent external(@NonNull final Intent intent) {
        if (AndroidHelper.api() >= AndroidHelper.API_21) {
            external21(intent);
        }
        else {
            external3(intent);
        }
        return intent;
    }

    @TargetApi(AndroidHelper.API_21)
    private static Intent external21(@NonNull final Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return intent;
    }

    @SuppressWarnings("deprecation")
    private static Intent external3(@NonNull final Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        return intent;
    }

}
