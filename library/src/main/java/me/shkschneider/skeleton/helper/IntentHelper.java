package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

import me.shkschneider.skeleton.java.SkHide;
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

    @SkHide
    public static final int REQUEST_CODE_CAMERA = 111;
    @SkHide
    public static final int REQUEST_CODE_GALLERY = 222;
    @SkHide
    public static final int REQUEST_CODE_RINGTONE = 333;
    @SkHide
    public static final int REQUEST_CODE_PERMISSIONS = 23;

    @Nullable
    public static Intent home() {
        final PackageManager packageManager = ApplicationHelper.packageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return null;
        }

        final Intent intent = packageManager.getLaunchIntentForPackage(ApplicationHelper.packageName());
        if (intent == null) {
            LogHelper.warning("Intent was NULL");
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
            LogHelper.warning("Url was invalid");
            return null;
        }

        return external(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static Intent share(@Nullable final String subject, @Nullable final String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND)
                .setType(MimeTypeHelper.TEXT_PLAIN);
        if (! TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (! TextUtils.isEmpty(text)) {
            intent.putExtra(Intent.EXTRA_TEXT, text);
        }
        return Intent.createChooser(intent, null);
    }

    public static Intent directions(final long fromLatitude, final long fromLongitude,
                                    final long toLatitude, final long toLongitude) {
        return external(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format(LocaleHelper.locale(),
                        "http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",
                        fromLatitude, fromLongitude,
                        toLatitude, toLongitude))));
    }

    public static Intent ringtone(final String existingUri) {
        final Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (existingUri != null ? Uri.parse(existingUri) : null));
        return intent;
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

    @SkHide
    public static boolean canHandle(@NonNull final Intent intent) {
        // return (intent.resolveActivity(ApplicationHelper.packageManager()) != null);
        final List<ResolveInfo> resolveInfos = ApplicationHelper.packageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    @SkHide
    @Nullable
    public static Object onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent intent) {
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
            return bundle.get("data");
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
        else if (requestCode == REQUEST_CODE_RINGTONE) {
            if (intent == null) {
                LogHelper.warning("Intent was NULL");
                return null;
            }
            final Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                return RingtoneManager.getRingtone(ApplicationHelper.context(), uri);
            }
            return null;
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
