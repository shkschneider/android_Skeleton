package me.shkschneider.skeleton.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import me.shkschneider.skeleton.SkeletonActivity;

public class IntentHelper {

    public static final int HOME_FLAGS = (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

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

    public static Bitmap onActivityResult(final int resultCode, final Intent data) {
        if (resultCode == SkeletonActivity.RESULT_OK) {
            final Bundle extras = data.getExtras();
            return (Bitmap) extras.get("data");
        }
        return null;
    }

}
