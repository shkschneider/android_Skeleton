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
import android.text.TextUtils;

import com.androidquery.AQuery;
import com.androidquery.auth.BasicHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class SingleSignOnAuthenticator {

    protected String mUrl;
    protected AQuery mAQuery;
    protected BasicHandle mHandle;

    public SingleSignOnAuthenticator(final Context context, final String url, final String login, final String password) {
        if (context == null) {
            LogHelper.w("Context was NULL");
            return ;
        }
        if (TextUtils.isEmpty(url)) {
            LogHelper.w("URL was NULL");
            return ;
        }

        mUrl = url;
        mHandle = new BasicHandle(login, password);
        mAQuery = new AQuery(context);
    }

    public Boolean auth(final Activity activity, final SingleSignOnAuthenticatorCallback singleSignOnAuthenticatorCallback, final String permissions) {
        if (mAQuery == null) {
            LogHelper.w("AQuery was NULL");
            if (singleSignOnAuthenticatorCallback != null) {
                singleSignOnAuthenticatorCallback.singleSignOnAuthenticatorCallback(null);
            }
            return false;
        }
        if (TextUtils.isEmpty(mUrl)) {
            LogHelper.w("URL was NULL");
            return false;
        }

        mAQuery.auth(mHandle).ajax(mUrl, String.class, new AjaxCallback<String>() {

            @Override
            public void callback(final String url, final String object, final AjaxStatus status) {
                super.callback(url, object, status);

                if (! TextUtils.isEmpty(status.getError())) {
                    LogHelper.w("Message: " + status.getMessage());
                    LogHelper.w("Error: " + status.getError());
                }

                if (singleSignOnAuthenticatorCallback != null) {
                    singleSignOnAuthenticatorCallback.singleSignOnAuthenticatorCallback(null);
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

        mHandle.unauth();
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

    public static interface SingleSignOnAuthenticatorCallback {

        public void singleSignOnAuthenticatorCallback(final String result);

    }

}
