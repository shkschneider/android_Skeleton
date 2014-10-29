package me.shkschneider.skeleton;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import me.shkschneider.skeleton.helper.LogHelper;

/**
 * @TODO generify
 */
public class WebService {

    public static void getJsonObject(@NotNull final String url, final Callback callback) {
        LogHelper.info("type:" + "JsonObject" + " url:" + url);
        Ion.with(SkeletonApplication.CONTEXT)
                .load(url)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<JsonObject> response) {
                        if (e != null) {
                            LogHelper.wtf(e);
                            if (callback == null) {
                                LogHelper.warning("Callback was NULL");
                                return;
                            }
                            callback.webServiceCallback(e, null);
                            return;
                        }
                        if (callback == null) {
                            LogHelper.warning("Callback was NULL");
                            return;
                        }

                        final int responseCode = response.getHeaders().getResponseCode();
                        final String responseMessage = response.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            callback.webServiceCallback(new WebServiceException(responseCode, responseMessage), null);
                        }
                        else {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public static void getString(@NotNull final String url, final Callback callback) {
        LogHelper.info("type:" + "InputStream" + " url:" + url);
        Ion.with(SkeletonApplication.CONTEXT)
                .load(url)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<String> response) {
                        if (e != null) {
                            LogHelper.wtf(e);
                            if (callback == null) {
                                LogHelper.warning("Callback was NULL");
                                return;
                            }
                            callback.webServiceCallback(e, null);
                            return;
                        }
                        if (callback == null) {
                            LogHelper.warning("Callback was NULL");
                            return;
                        }

                        final int responseCode = response.getHeaders().getResponseCode();
                        final String responseMessage = response.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            callback.webServiceCallback(new WebServiceException(responseCode, responseMessage), null);
                        }
                        else {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public static void getInputStream(@NotNull final String url, final Callback callback) {
        LogHelper.info("type:" + "InputStream" + " url:" + url);
        Ion.with(SkeletonApplication.CONTEXT)
                .load(url)
                .asInputStream()
                .withResponse()
                .setCallback(new FutureCallback<Response<InputStream>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<InputStream> response) {
                        if (e != null) {
                            LogHelper.wtf(e);
                            if (callback == null) {
                                LogHelper.warning("Callback was NULL");
                                return;
                            }
                            callback.webServiceCallback(e, null);
                            return;
                        }
                        if (callback == null) {
                            LogHelper.warning("Callback was NULL");
                            return;
                        }

                        final int responseCode = response.getHeaders().getResponseCode();
                        final String responseMessage = response.getHeaders().getResponseMessage();
                        if (responseCode >= 400) {
                            callback.webServiceCallback(new WebServiceException(responseCode, responseMessage), null);
                        }
                        else {
                            callback.webServiceCallback(null, response.getResult());
                        }
                    }
                });
    }

    public static class WebServiceException extends Exception {

        private int mCode;

        public WebServiceException(final int code, final String message) {
            super(message);
            mCode = code;
        }

        public int getErrorCode() {
            return mCode;
        }

    }

    public interface Callback {

        public void webServiceCallback(final Exception e, final Object result);

    }

}
