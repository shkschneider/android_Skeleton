package me.shkschneider.skeleton.helper;

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

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.List;

import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.UrlHelper;
import me.shkschneider.skeleton.ui.BitmapHelper;
import me.shkschneider.skeleton.data.MimeTypeHelper;

public class IntentHelper {

    public static final int CLEAR_FLAGS = (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    public static final String BROADCAST_TIME_TICK = Intent.ACTION_TIME_TICK;
    public static final String BROADCAST_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
    public static final String BROADCAST_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;
    public static final String BROADCAST_BOOT_COMPLETED = Intent.ACTION_BOOT_COMPLETED;
    public static final String BROADCAST_PACKAGE_ADDED = Intent.ACTION_PACKAGE_ADDED;
    public static final String BROADCAST_PACKAGE_CHANGED = Intent.ACTION_PACKAGE_CHANGED;
    public static final String BROADCAST_PACKAGE_REMOVED = Intent.ACTION_PACKAGE_REMOVED;
    public static final String BROADCAST_PACKAGE_RESTARTED = Intent.ACTION_PACKAGE_RESTARTED;
    public static final String BROADCAST_PACKAGE_DATA_CLEARED = Intent.ACTION_PACKAGE_DATA_CLEARED;
    public static final String BROADCAST_UID_REMOVED = Intent.ACTION_UID_REMOVED;
    public static final String BROADCAST_BATTERY_CHANGED = Intent.ACTION_BATTERY_CHANGED;
    public static final String BROADCAST_POWER_CONNECTED = Intent.ACTION_POWER_CONNECTED;
    public static final String BROADCAST_POWER_DISCONNECTED = Intent.ACTION_POWER_DISCONNECTED;
    public static final String BROADCAST_SHUTDOWN = Intent.ACTION_SHUTDOWN;

    public static final int REQUEST_CODE_CAMERA = 111;
    public static final int REQUEST_CODE_GALLERY = 222;

    public static Intent home() {
        final PackageManager packageManager = ApplicationHelper.context().getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        final Intent intent = packageManager.getLaunchIntentForPackage(ApplicationHelper.packageName());
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
            return null;
        }

        return intent.setFlags(CLEAR_FLAGS);
    }

    public static Intent view(@NonNull final Uri uri) {
        return external(new Intent(Intent.ACTION_VIEW, uri));
    }

    public static Intent web(@NonNull final String url) {
        if (! UrlHelper.valid(url)) {
            LogHelper.warning("Url was invalid");
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

    public static Intent camera(@NonNull final File file) {
        if (! FeaturesHelper.feature(FeaturesHelper.FEATURE_CAMERA)) {
            LogHelper.warning("Camera was unavailable");
            return null;
        }

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                .putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, true);
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
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
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    public static Bitmap onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode != Activity.RESULT_OK) {
            LogHelper.debug("ResultCode was not OK");
            return null;
        }

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (intent == null) {
                LogHelper.warning("Intent was NULL");
                return null;
            }
            final Bundle bundle = intent.getExtras();
            if (bundle == null) {
                LogHelper.warning("Bundle was NULL");
                return null;
            }

            return (Bitmap) bundle.get("data");
        }
        else if (requestCode == REQUEST_CODE_GALLERY) {
            if (intent == null) {
                LogHelper.warning("Intent was NULL");
                return null;
            }
            final Uri uri = intent.getData();
            if (uri == null) {
                LogHelper.warning("Uri was NULL");
                return null;
            }

            return BitmapHelper.decodeUri(uri);
        }
        return null;
    }

    // http://developer.android.com/training/implementing-navigation/descendant.html#external-activities
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
