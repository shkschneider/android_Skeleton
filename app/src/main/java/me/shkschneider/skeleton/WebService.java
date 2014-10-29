package me.shkschneider.skeleton;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import me.shkschneider.skeleton.helper.LogHelper;

/**
 * WebService (generified) that handles most class types (default being InputStream from Ion library).
 * Beware that T and the Class<T> parameter of the constructor MUST be the same classType.
 * <em>This is due to Java limitations in reflections with Ion's mechanisms.</em>
 * <br />
 * Safe for types like:
 * - InputStream
 * - String
 * - Json
 * - XML
 * - ...
 *
 * WebService<T>:
 * - WebService(Class<T>)
 * - void get(String, Callback<T>)
 *
 * WebServiceException:
 * - String getMessage()
 * - int getErrorCode()
 *
 * Callback<T>:
 * - void webServiceCallback(Exception, T)
 */
public class WebService<T> {

    private Class<T> mClassType;

    // implicitly specify a classType (same as T)
    public WebService(final Class<T> classType) {
        mClassType = classType;
    }

    public void get(final String url, final Callback<T> callback) {
        Ion.with(SkeletonApplication.CONTEXT)
                .load(url)
                // cannot get classType from generic T type variable
                .as(mClassType)
                .withResponse()
                .setCallback(new FutureCallback<Response<T>>() {
                    @Override
                    public void onCompleted(final Exception e, final Response<T> response) {
                        if (e != null) {
                            LogHelper.wtf(e);
                            if (callback == null) {
                                LogHelper.warning("Callback was NULL");
                                return ;
                            }
                            callback.webServiceCallback(e, null);
                            return ;
                        }
                        if (callback == null) {
                            LogHelper.warning("Callback was NULL");
                            return ;
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

        @Override
        public String getLocalizedMessage() {
            return super.getMessage();
        }

        public int getErrorCode() {
            return mCode;
        }

    }

    public interface Callback<T> {

        public void webServiceCallback(final Exception e, final T response);

    }

}
