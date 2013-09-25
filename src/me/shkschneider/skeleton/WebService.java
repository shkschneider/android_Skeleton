package me.shkschneider.skeleton;

import android.content.Context;
import android.text.TextUtils;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpStatus;

@SuppressWarnings("unused")
public class WebService {

    protected Context mContext;
    protected AQuery mAQuery;
    protected Integer mId;
    protected String mUrl;

    protected Boolean check() {
        if (mContext == null) {
            Skeleton.Log.w("Context was NULL");
            return false;
        }
        if (mAQuery == null) {
            Skeleton.Log.w("AQuery was NULL");
            return false;
        }
        if (mId < 0) {
            Skeleton.Log.w("Id was invalid");
            return false;
        }
        if (TextUtils.isEmpty(mUrl)) {
            Skeleton.Log.w("Url was NULL");
            return false;
        }
        if (! Skeleton.Network.validUrl(mUrl)) {
            Skeleton.Log.w("Url was invalid");
            return false;
        }
        return true;
    }

    public WebService(final Context context, final Integer id, final String url) {
        mContext = context;
        if (mContext != null) {
            mAQuery = new AQuery(mContext);
        }
        mId = id;
        mUrl = url;
    }

    public void run(final WebServiceCallback callback) {
        if (! check()) {
            Skeleton.Log.d("check() failed");
            if (callback != null) {
                callback.webServiceCallback(mId, new Response(null, null));
            }
        }
        else {
            mAQuery.ajax(new AjaxCallback<String>() {

                @Override
                public void callback(final String url, final String content, final AjaxStatus ajaxStatus) {
                    if (callback != null) {
                        callback.webServiceCallback(mId, new Response(ajaxStatus, content));
                    }
                    else {
                        Skeleton.Log.w("WebServiceCallback was NULL");
                    }
                }

            }
                    .url(mUrl)
                    .type(String.class)
                    .header("User-Agent", Skeleton.Network.userAgent()));
        }
    }

    public void cancel() {
        if (mAQuery != null) {
            mAQuery.ajaxCancel();
        }
    }

    public void run() {
        run(null);
    }

    public static class Response {

        public Integer code;
        public Boolean success;
        public String content;
        public Long duration;

        public Response(final AjaxStatus ajaxStatus, final String content) {
            if (ajaxStatus != null) {
                this.code = ajaxStatus.getCode();
                this.success = (this.code == HttpStatus.SC_OK);
                this.duration = ajaxStatus.getDuration();
                this.content = (this.success ? content : Skeleton.String.capitalize(ajaxStatus.getMessage()));
            }
            else {
                Skeleton.Log.w("AjaxStatus was NULL");
                this.code = -1;
                this.success = false;
                this.duration = 0L;
                this.content = content;
            }
        }

    }

    public static interface WebServiceCallback {

        public void webServiceCallback(final Integer id, final Response response);

    }

}
