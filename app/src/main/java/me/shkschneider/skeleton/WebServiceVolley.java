package me.shkschneider.skeleton;

import android.graphics.Bitmap;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.shkschneider.skeleton.helper.ApplicationHelper;
import me.shkschneider.skeleton.helper.ClassHelper;
import me.shkschneider.skeleton.helper.GsonParser;
import me.shkschneider.skeleton.helper.LogHelper;

@Deprecated
public class WebServiceVolley {

    private static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(5);
    private static final int RETRIES = 2;
    // Automatic cache based on Response's header: Cache-Control, Expires, maxAge (never in DEBUG)
    // Automatic GZip

    private static final String TAG = ClassHelper.name(WebServiceVolley.class);

    private DefaultRetryPolicy mRetryPolicy;
    private RequestQueue mRequestQueue;

    public WebServiceVolley() {
        mRetryPolicy = new DefaultRetryPolicy(TIMEOUT, RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue = Volley.newRequestQueue(SkeletonApplication.CONTEXT);
    }

    private void prepare(final Request request) {
        LogHelper.info("url:" + request.getUrl());
        request.setTag(TAG);
        request.setRetryPolicy(mRetryPolicy);
        try {
            @SuppressWarnings("unchecked") // @see com.android.volley.Request#getHeaders()
            final Map<String, String> headers = request.getHeaders();
            // headers.put()
        }
        catch (final Exception e) {
            LogHelper.wtf(e);
        }
        if (ApplicationHelper.debug()) {
            request.setShouldCache(false);
        }
    }

    // TODO InputStream

    public void getString(@NotNull final String url, final Callback callback) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String string) {
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(null, string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogHelper.wtf(volleyError);
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(new WebServiceException(volleyError), null);
            }
        });
        prepare(stringRequest);
        mRequestQueue.add(stringRequest);
    }

    public void getJsonObject(@NotNull final String url, final Callback callback) {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(null, GsonParser.parse(jsonObject));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogHelper.wtf(volleyError);
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(new WebServiceException(volleyError), null);
            }
        });
        prepare(jsonObjectRequest);
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getImage(@NotNull final String url, final Callback callback) {
        final ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(final Bitmap bitmap) {
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(null, bitmap);
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogHelper.wtf(volleyError);
                if (callback == null) {
                    LogHelper.warning("Callback was NULL");
                    return;
                }
                callback.webServiceCallback(new WebServiceException(volleyError), null);
            }
        });
        prepare(imageRequest);
        mRequestQueue.add(imageRequest);
    }

    public void clearCache() {
        mRequestQueue.getCache().clear();
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(TAG);
    }

    public static class WebServiceException extends Exception {

        private int mCode;

        public WebServiceException(final VolleyError volleyError) {
            super(volleyError.getMessage());
            mCode = volleyError.networkResponse.statusCode;
        }

        public int getErrorCode() {
            return mCode;
        }

    }

    public interface Callback {

        public void webServiceCallback(final WebServiceException e, final Object result);

    }

}
