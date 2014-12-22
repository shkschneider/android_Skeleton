package me.shkschneider.skeleton.network;

import android.net.Uri;

public class UrlHelper {

    public static Uri.Builder builder() {
        return new Uri.Builder();
    }

    public static Uri.Builder builder(final String url) {
        return Uri.parse(url).buildUpon();
    }

    public static Uri uri(final Uri.Builder builder) {
        return builder.build();
    }

    public static String url(final Uri uri) {
        return uri.toString();
    }

}
