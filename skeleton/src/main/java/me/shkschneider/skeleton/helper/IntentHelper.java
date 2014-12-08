package me.shkschneider.skeleton.helper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.List;

import me.shkschneider.skeleton.network.NetworkHelper;
import me.shkschneider.skeleton.ui.ImageManipulator;
import me.shkschneider.skeleton.SkeletonApplication;
import me.shkschneider.skeleton.data.MimeTypeHelper;

public class IntentHelper {

    public static final int HOME_FLAGS = (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    private static final int REQUEST_CODE_CAMERA = 111;
    private static final int REQUEST_CODE_GALLERY = 222;

    public static Intent uri(final Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent url(final String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public static Intent share(final String subject, final String body) {
        final Intent intent = new Intent(Intent.ACTION_SEND)
                .setType(MimeTypeHelper.TEXT_PLAIN)
                .putExtra(Intent.EXTRA_TEXT, body)
                .putExtra(Intent.EXTRA_SUBJECT, subject);
        return Intent.createChooser(intent, null);
    }

    public static Intent settings() {
        return new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + ApplicationHelper.packageName()));
    }

    public static Intent picture() {
        return new Intent(Intent.ACTION_PICK)
                .setType(MimeTypeHelper.IMAGE)
                .setAction(Intent.ACTION_GET_CONTENT);
    }

    public static Intent camera() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, true);
    }

    public static boolean canHandle(@NonNull final Intent intent) {
        final PackageManager packageManager = SkeletonApplication.CONTEXT.getPackageManager();
        if (packageManager == null) {
            LogHelper.warning("PackageManager was NULL");
            return false;
        }

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return (resolveInfos.size() > 0);
    }

    public static boolean web(@NonNull final Activity activity, @NonNull final String url) {
        if (! NetworkHelper.validUrl(url)) {
            LogHelper.warning("Url was invalid");
            return false;
        }

        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        return true;
    }

    public static boolean image(@NonNull final Activity activity, @NonNull final Uri uri) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, MimeTypeHelper.IMAGE);
        activity.startActivity(intent);
        return true;
    }

    public static boolean gallery(@NonNull final Activity activity) {
        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MimeTypeHelper.IMAGE);
        activity.startActivityForResult(intent, REQUEST_CODE_GALLERY);
        return true;
    }

    public static boolean camera(@NonNull final Activity activity) {
        if (! FeaturesHelper.feature(FeaturesHelper.FEATURE_CAMERA)) {
            LogHelper.warning("Camera was unavailable");
            return false;
        }

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (! canHandle(intent)) {
            LogHelper.warning("Cannot handle Intent");
            return false;
        }

        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
        return true;
    }

    public static boolean settings(@NonNull final Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
        return true;
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
