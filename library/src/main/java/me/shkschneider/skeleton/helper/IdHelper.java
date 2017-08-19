package me.shkschneider.skeleton.helper;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import java.util.UUID;

import me.shkschneider.skeleton.java.StringHelper;

// <https://developers.google.com/instance-id/>
@SuppressLint("HardwareIds")
public class IdHelper {

    protected IdHelper() {
        // Empty
    }

    // <http://stackoverflow.com/q/22743087>
    @Nullable
    @RequiresPermission("com.google.android.providers.gsf.permission.READ_GSERVICES")
    public static String googleServiceFrameworkId() {
        final Cursor cursor = ContextHelper.applicationContext().getContentResolver().query(Uri.parse("content://com.google.android.gsf.gservices"),
                null, null, new String[] { "android_id" }, null);
        if (cursor == null) {
            return null;
        }
        String gsfId = null;
        try {
            if (! cursor.moveToFirst() || cursor.getColumnCount() < 2) {
                throw new Exception();
            }
            gsfId = Long.toHexString(Long.parseLong(cursor.getString(1)));
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        finally {
            cursor.close();
        }
        return gsfId;
    }

    @Deprecated // Using getString to get device identifiers is not recommended.
    @Nullable
    public static String androidId() {
        final String androidId = Settings.Secure.getString(ContextHelper.applicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            LogHelper.warning("AndroidId was NULL");
            return null;
        }
        return StringHelper.lower(androidId);
    }

    public static String uuid(@NonNull final String id) {
        return UUID.nameUUIDFromBytes(id.getBytes()).toString();
    }

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
