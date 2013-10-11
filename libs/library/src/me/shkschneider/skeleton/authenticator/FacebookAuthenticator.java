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
package me.shkschneider.skeleton.authenticator;

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

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class FacebookAuthenticator {

    public static final String GRAPH_ME_URL = "https://graph.facebook.com/me/feed";
    public static final String PERMISSION_BASIC_INFO = "basic_info";
    public static final String PERMISSION_READ_STREAM = "read_stream";
    public static final String PERMISSION_READ_FRIENDLISTS = "read_friendlists";
    public static final String PERMISSION_MANAGE_FRIENDLISTS = "manage_friendlists";
    public static final String PERMISSION_MANAGE_NOTIFICATIONS = "manage_notifications";
    public static final String PERMISSION_PUBLISH_STREAM = "publish_stream";
    public static final String PERMISSION_PUBLISH_CHECKINS = "publish_checkins";
    public static final String PERMISSION_OFFLINE_ACCESS = "offline_access";
    public static final String PERMISSION_USER_PHOTOS = "user_photos";
    public static final String PERMISSION_USER_LIKES = "user_likes";
    public static final String PERMISSION_USER_GROUPS = "user_groups";
    public static final String PERMISSION_FRIENDS_PHOTOS = "friends_photos";

    protected String mAppId;
    protected Integer mRequestCode;
    protected AQuery mAQuery;
    protected FacebookHandle mHandle;

    public FacebookAuthenticator(final Context context, final String appId, final Integer requestCode) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return ;
        }
        if (TextUtils.isEmpty(appId)) {
            LogHelper.w("AppId was NULL");
            return ;
        }
        if (requestCode <= 0) {
            LogHelper.w("RequestCode was invalid");
            return ;
        }

        mAppId = appId;
        mRequestCode = requestCode;
        mAQuery = new AQuery(context);
    }

    public Boolean auth(final Activity activity, final FacebookAuthenticatorCallback facebookAuthenticatorCallback, final String permissions) {
        if (activity == null) {
            LogHelper.w("Activity was NULL");
            return false;
        }
        if (TextUtils.isEmpty(mAppId)) {
            LogHelper.w("AppId was NULL");
            return false;
        }
        if (mRequestCode <= 0) {
            LogHelper.w("RequestCode was invalid");
            return false;
        }

        mHandle = new FacebookHandle(activity, mAppId, permissions) {

            @Override
            public boolean expired(final AbstractAjaxCallback<?, ?> callback, final AjaxStatus status) {
                if (status.getCode() == HttpStatus.SC_UNAUTHORIZED) {
                    return true;
                }
                return super.expired(callback, status);
            }

        };
        mHandle.sso(mRequestCode);

        if (mAQuery == null) {
            LogHelper.w("AQuery was NULL");
            if (facebookAuthenticatorCallback != null) {
                facebookAuthenticatorCallback.facebookAuthenticatorCallback(null);
            }
            return false;
        }

        mAQuery.auth(mHandle).ajax(GRAPH_ME_URL, String.class, new AjaxCallback<String>() {

            @Override
            public void callback(final String url, final String object, final AjaxStatus status) {
                super.callback(url, object, status);

                if (TextUtils.isEmpty(status.getError()) && ! status.getMessage().equalsIgnoreCase("cancel")) {
                    final String token = mHandle.getToken();
                    if (! TextUtils.isEmpty(token)) {
                        LogHelper.d("Token: " + token);
                        facebookAuthenticatorCallback.facebookAuthenticatorCallback(token);
                    }
                    else {
                        LogHelper.w("Token is NULL");
                        if (facebookAuthenticatorCallback != null) {
                            facebookAuthenticatorCallback.facebookAuthenticatorCallback(null);
                        }
                    }
                }
                else {
                    LogHelper.w("Message: " + status.getMessage());
                    LogHelper.w("Error: " + status.getError());
                    if (facebookAuthenticatorCallback != null) {
                        facebookAuthenticatorCallback.facebookAuthenticatorCallback(null);
                    }
                }
            }

        });
        return true;
    }

    public Boolean unauth() {
        if (mHandle == null) {
            LogHelper.w("Handle was NULL");
            return false;
        }
        if (TextUtils.isEmpty(mHandle.getToken())) {
            LogHelper.w("Token was NULL");
            return false;
        }

        mHandle.unauth();
        return true;
    }

    public String getToken() {
        if (mHandle == null) {
            LogHelper.w("Handle was NULL");
            return null;
        }

        return mHandle.getToken();
    }

    public Boolean onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode != mRequestCode) {
            return false;
        }

        if (mHandle == null) {
            LogHelper.w("Handle was NULL");
            return false;
        }

        mHandle.onActivityResult(requestCode, resultCode, data);
        return true;
    }

    public Boolean onDestroy() {
        if (mAQuery == null) {
            LogHelper.w("AQuery was NULL");
            return false;
        }

        mAQuery.dismiss();
        return true;
    }

    public static interface FacebookAuthenticatorCallback {

        public void facebookAuthenticatorCallback(final String token);

    }

}
