package me.shkschneider.skeleton.network;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import me.shkschneider.skeleton.helper.LogHelper;

public class UrlHelper {

    public static Uri.Builder builder() {
        return new Uri.Builder();
    }

    public static Uri.Builder builder(@NonNull final String url) {
        return Uri.parse(url).buildUpon();
    }

    public static boolean valid(@NonNull final String url) {
        return URLUtil.isValidUrl(url);
    }

    public static String encode(@NonNull final String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        }
        catch (final UnsupportedEncodingException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static String decode(@NonNull final String string) {
        try {
            return URLDecoder.decode(string, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            LogHelper.wtf(e);
            return null;
        }
    }

    public static Uri uri(@NonNull final Uri.Builder builder) {
        return builder.build();
    }

    public static String url(@NonNull final Uri uri) {
        return uri.toString();
    }

}
