package me.shkschneider.skeleton.network;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.java.ClassHelper;
import me.shkschneider.skeleton.helper.LogHelper;

public class WebService {

    public static final int CODE_FATAL = 666;

    private static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(5);
    // No retries
    // Automatic cache GET requests (never in DEBUG)
    private static final String CACHE_CONTROL = "min-fresh=60";
    // Automatic GZip

    private LoadBuilder<Builders.Any.B> mIon;

    public WebService() {
        mIon = Ion.with(ApplicationHelper.context());
    }

    private Builders.Any.B build(final String url) {
        LogHelper.info("url:" + url);
        final Builders.Any.B builder = mIon.load(url);
        builder.setTimeout(TIMEOUT);
        // builder.setHeader()
        // builder.setBodyParameter()
        if (ApplicationHelper.debug()) {
            builder.noCache();
        }
        else {
            builder.setHeader("Cache-Control", CACHE_CONTROL);
        }
        return builder;
    }

    private boolean error(final Exception e, final Response response, final Callback callback) {
        if (e != null) {
            LogHelper.wtf(e);
            if (callback == null) {
                LogHelper.warning("Callback was NULL");
                return true;
            }
            callback.webServiceCallback(new WebServiceException(CODE_FATAL, ClassHelper.simpleName(e.getClass())), null);
            return true;
        }
        if (callback == null) {
            LogHelper.warning("Callback was NULL");
            return true;
        }
        if (response != null) {
            final int responseCode = response.getHeaders().code();
            final String responseMessage = response.getHeaders().message();
            // All codes below 400 do not imply success...
            // <http://en.wikipedia.org/wiki/List_of_HTTP_status_codes>
            if (responseCode >= 400) {
                callback.webServiceCallback(new WebServiceException(responseCode, responseMessage), null);
                return true;
            }
        }
        return false;
    }

    public void getInputStream(@NonNull final String url, final Callback callback) {
        build(url).asInputStream()
                .withResponse()
                .setCallback(new FutureCallback<Response<InputStream>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<InputStream> response) {
                        if (! error(e, response, callback)) {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public void getString(@NonNull final String url, final Callback callback) {
        build(url).asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<String> response) {
                        if (! error(e, response, callback)) {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public void getJsonObject(@NonNull final String url, final Callback callback) {
        build(url).asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonObject> response) {
                        if (! error(e, response, callback)) {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public void getJsonArray(@NonNull final String url, final Callback callback) {
        build(url).asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonArray> response) {
                        if (! error(e, response, callback)) {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public void getBitmap(@NonNull final String url, final Callback callback) {
        build(url).asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(final Exception e, final Bitmap response) {
                        if (! error(e, null, callback)) {
                            callback.webServiceCallback(null, response);
                        }
                    }
                });
    }

    public static void clearCache() {
        Ion.getDefault(ApplicationHelper.context()).getCache().clear();
    }

    public static void cancelAll() {
        Ion.getDefault(ApplicationHelper.context()).cancelAll();
    }

    public static class WebServiceException extends Exception {

        private int mCode;

        public WebServiceException(final int responseCode, final String responseMessage) {
            super(responseMessage);
            mCode = responseCode;
        }

        public int getCode() {
            return mCode;
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }

    }

    public interface Callback {

        public void webServiceCallback(final WebServiceException e, final Object result);

    }

}