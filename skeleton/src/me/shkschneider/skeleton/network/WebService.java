package me.shkschneider.skeleton.network;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;
import me.shkschneider.skeleton.helpers.LogHelper;

public abstract class WebService<T> {

    public int id;
    public String url;
    public int tries;

    protected Future<Response<T>> request;

    private Callback callback;

    public WebService(final int id, final String url, final Callback callback) {
        this.id = id;
        this.url = url;
        this.tries = 0;
        this.request = null;
        this.callback = callback;
    }

    public WebService(final int id, final String url) {
        this(id, url, null);
    }

    public void run(final Callback overloadedCallback) {
        this.request.setCallback(new FutureCallback<Response<T>>() {

            @Override
            public void onCompleted(final Exception e, final Response<T> result) {
                if (e != null) {
                    LogHelper.error(e.getMessage());
                    if (overloadedCallback != null) {
                        overloadedCallback.webserviceFailure(e);
                    }
                    return;
                }

                if (overloadedCallback != null) {
                    overloadedCallback.webserviceSuccess(result.getHeaders().getResponseCode(), result.getResult());
                }
            }

        });
    }

    public void run() {
        run(this.callback);
    }

    public void failure(final Exception e) {
        if (this.callback != null) {
            this.callback.webserviceFailure(e);
        }
    }

    public void success(final int responseCode, final Object result) {
        if (this.callback != null) {
            this.callback.webserviceSuccess(responseCode, result);
        }
    }

    public boolean cancel() {
        return this.request.cancel();
    }

    public static interface Callback {

        public void webserviceFailure(final Exception e);

        public void webserviceSuccess(final int responseCode, final Object result);

    }

}
