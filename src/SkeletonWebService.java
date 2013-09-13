/**
 * Copyright 2013 ShkSchneider
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shkschneider.skeleton;

import android.content.Context;
import android.text.TextUtils;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpStatus;

import me.shkschneider.skeleton.helpers.SkeletonNetworkHelper;
import me.shkschneider.skeleton.helpers.SkeletonStringHelper;

public class SkeletonWebService {

    private Context mContext;
    private Integer mId;
    private String mUrl;

    private Boolean check() {
        if (mContext == null) {
            SkeletonLog.w("Context was NULL");
            return false;
        }
        if (mId < 0) {
            SkeletonLog.w("Id was invalid");
            return false;
        }
        if (TextUtils.isEmpty(mUrl)) {
            SkeletonLog.w("Url was NULL");
            return false;
        }
        if (! SkeletonNetworkHelper.isValidUrl(mUrl)) {
            SkeletonLog.w("Url was invalid");
            return false;
        }
        return true;
    }

    public SkeletonWebService(final Context context, final Integer id, final String url) {
        mContext = context;
        mId = id;
        mUrl = url;
    }

    public void run(final Callback callback) {
        if (! check()) {
            SkeletonLog.d("check() failed");
            if (callback != null) {
                callback.WebServiceCallback(mId, new Response(null, null));
            }
        }
        else {
            final AjaxCallback<String> ajaxCallback = new AjaxCallback<String>() {

                        @Override
                        public void callback(final String url, final String content, final AjaxStatus ajaxStatus) {
                            if (callback != null) {
                                callback.WebServiceCallback(mId, new Response(ajaxStatus, content));
                            }
                            else {
                                SkeletonLog.d("Callback was NULL");
                            }
                        }

                    }
                    .url(mUrl)
                    .type(String.class)
                    .header("User-Agent", SkeletonNetworkHelper.makeUserAgent(mContext));

            new AQuery(mContext).ajax(ajaxCallback);
        }
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
                this.content = (this.success ? content : SkeletonStringHelper.capitalize(ajaxStatus.getMessage()));
            }
            else {
                SkeletonLog.d("AjaxStatus was NULL");
                this.code = -1;
                this.success = false;
                this.duration = 0L;
                this.content = content;
            }
        }

    }

    public static interface Callback {

        public void WebServiceCallback(final Integer id, final Response response);

    }

}
