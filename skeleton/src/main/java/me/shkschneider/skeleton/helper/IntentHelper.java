package me.shkschneider.skeleton.helper;

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

import java.io.File;
import java.util.List;

import me.shkschneider.skeleton.java.StringHelper;
import me.shkschneider.skeleton.network.NetworkHelper;
import me.shkschneider.skeleton.ui.ImageManipulator;
import me.shkschneider.skeleton.data.MimeTypeHelper;

public class IntentHelper {

    public static final int MAIN_FLAGS = (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    public static final int HOME_FLAGS = (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    private static final int REQUEST_CODE_CAMERA = 111;
    private static final int REQUEST_CODE_GALLERY = 222;

    public static Intent view(@NonNull final Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent web(@NonNull final String url) {
        if (! NetworkHelper.validUrl(url)) {
            LogHelper.warning("Url was invalid");
            return null;
        }

        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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

    public static Intent applicationSettings() {
        return new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + ApplicationHelper.packageName()));
    }

    public static Intent systemSettings() {
        return new Intent(Settings.ACTION_SETTINGS);
    }

    public static Intent text(@NonNull final Uri uri) {
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.TEXT_PLAIN);
    }

    public static Intent audio(@NonNull final Uri uri) {
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.AUDIO);
    }

    public static Intent video(@NonNull final Uri uri) {
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.VIDEO);
    }

    public static Intent picture(@NonNull final Uri uri) {
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, MimeTypeHelper.IMAGE);
    }

    public static Intent gallery() {
        return new Intent(Intent.ACTION_PICK)
                .setType(MimeTypeHelper.IMAGE);
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

        return intent;
    }

    public static Intent file() {
        return new Intent(Intent.ACTION_GET_CONTENT)
                .setType(MimeTypeHelper.FILE);
    }

    public static Intent dial(@NonNull final String phone) {
        return new Intent(Intent.ACTION_DIAL)
                .setData(Uri.parse("tel:" + phone));
    }

    public static Intent call(@NonNull final String phone) {
        return new Intent(Intent.ACTION_CALL)
                .setData(Uri.parse("tel:" + phone));
    }

    public static Intent contact() {
        return new Intent(Intent.ACTION_PICK, Uri.parse("content://com.android.contacts/contacts"))
                .setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
    }

    public static Intent googlePlay() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ApplicationHelper.packageName()));
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

            return ImageManipulator.decodeUri(uri);
        }
        return null;
    }

}
