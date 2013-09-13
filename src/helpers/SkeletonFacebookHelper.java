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
package me.shkschneider.skeleton.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpStatus;

import me.shkschneider.skeleton.SkeletonLog;

public class SkeletonFacebookHelper {

    public static final String GRAPH_ME_URL = "https://graph.facebook.com/me/feed";

    public static final String PERMISSION_BASIC = "basic_info";
    public static final String PERMISSION_FRIENDS = "read_friendlists";
    public static final String PERMISSION_PUBLISH = "publish_actions";

    private static SkeletonFacebookHelper INSTANCE = null;

    private String mAppId;
    private Integer mRequestCode;
    private AQuery mAQuery;
    private FacebookHandle mHandle;

    public static SkeletonFacebookHelper newInstance(final Context context, final String appId, final Integer requestCode) {
        if (INSTANCE == null) {
            if (context != null) {
                if (! TextUtils.isEmpty(appId)) {
                    INSTANCE = new SkeletonFacebookHelper(context, appId, requestCode);
                }
                else {
                    SkeletonLog.w("AppId was NULL");
                }
            }
            else {
                SkeletonLog.w("Context was NULL");
            }
        }
        return INSTANCE;
    }

    public static SkeletonFacebookHelper getInstance() {
        return INSTANCE;
    }

    private SkeletonFacebookHelper(final Context context, final String appId, final Integer requestCode) {
        mAppId = appId;
        mRequestCode = requestCode;
        mAQuery = new AQuery(context);
    }

    public void auth(final Activity activity, final Callback callback, final String permissions) {
        mHandle = new FacebookHandle(activity, mAppId, permissions) {

            @Override
            public boolean expired(AbstractAjaxCallback<?, ?> callback, AjaxStatus status) {
                if (status.getCode() == HttpStatus.SC_UNAUTHORIZED) {
                    return true;
                }
                return super.expired(callback, status);
            }

        };
        mHandle.sso(mRequestCode);

        mAQuery.auth(mHandle)
                .ajax(GRAPH_ME_URL, String.class, new AjaxCallback<String>() {

                    @Override
                    public void callback(final String url, final String object, final AjaxStatus status) {
                        super.callback(url, object, status);

                        if (TextUtils.isEmpty(status.getError()) && ! status.getMessage().equalsIgnoreCase("cancel")) {
                            final String token = mHandle.getToken();
                            if (! TextUtils.isEmpty(token)) {
                                SkeletonLog.d("Token: " + token);
                                callback.FacebookCallback(token);
                            }
                            else {
                                SkeletonLog.d("Token is NULL");
                                if (callback != null) {
                                    callback.FacebookCallback(null);
                                }
                            }
                        }
                        else {
                            SkeletonLog.d("Message: " + status.getMessage());
                            SkeletonLog.d("Error: " + status.getError());
                            if (callback != null) {
                                callback.FacebookCallback(null);
                            }
                        }
                    }

                });
    }

    public void unauth() {
        if (mHandle != null) {
            if (! TextUtils.isEmpty(mHandle.getToken())) {
                mHandle.unauth();
            }
            else {
                SkeletonLog.d("Token was NULL");
            }
        }
        else {
            SkeletonLog.d("Handle was NULL");
        }
    }

    public String getToken() {
        if (mHandle != null) {
            return mHandle.getToken();
        }
        else {
            SkeletonLog.d("Handle was NULL");
        }
        return null;
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == mRequestCode) {
            if (mHandle != null) {
                mHandle.onActivityResult(requestCode, resultCode, data);
            }
            else {
                SkeletonLog.d("Handle was NULL");
            }
        }
    }

    public void onDestroy() {
        if (mAQuery != null) {
            mAQuery.dismiss();
        }
        else {
            SkeletonLog.d("AQuery was NULL");
        }
    }

    public static interface Callback {

        public void FacebookCallback(final String token);

    }
}
