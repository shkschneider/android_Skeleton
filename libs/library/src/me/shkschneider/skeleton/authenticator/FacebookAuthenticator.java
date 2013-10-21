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
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.widget.LoginButton;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import java.util.ArrayList;
import java.util.List;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class FacebookAuthenticator {

    protected Activity mActivity;
    protected String mAppId;
    protected SimpleFacebook mFacebook;
    protected Permissions[] mPermissions;

    public FacebookAuthenticator(final Activity activity, final String appId) {
        mActivity = activity;
        if (mActivity != null) {
            mFacebook = SimpleFacebook.getInstance(mActivity);

            if (! TextUtils.isEmpty(appId)) {
                mAppId = appId;
                mPermissions = new Permissions[] {
                        Permissions.BASIC_INFO
                };
                SimpleFacebook.setConfiguration(new SimpleFacebookConfiguration.Builder()
                        .setAppId(mAppId)
                        .setNamespace(AndroidHelper.packageName(mActivity))
                        .setPermissions(mPermissions)
                        .build());
            }

            login(new FacebookAuthenticatorCallback() {

                @Override
                public void facebookAuthenticatorCallback(final Boolean success, final String token) {
                    LogHelper.w("Token: " + token);
                }

            });
        }
    }

    public String[] permissions() {
        final List<String> list = new ArrayList<String>();
        for (final Permissions permission : mPermissions) {
            list.add(permission.getValue());
        }
        return list.toArray(new String[list.size()]);
    }

    public String[] readPermissions(final Permissions[] permissions) {
        final List<String> list = new ArrayList<String>();
        for (final Permissions permission : permissions) {
            if (permission.getType() == SessionAuthorizationType.READ) {
                list.add(permission.getValue());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public String[] publishPermissions(final Permissions[] permissions) {
        final List<String> list = new ArrayList<String>();
        for (final Permissions permission : permissions) {
            if (permission.getType() == SessionAuthorizationType.PUBLISH) {
                list.add(permission.getValue());
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public void loginButton(final LoginButton loginButton, final FacebookAuthenticatorCallback callback) {
        if (loginButton == null) {
            LogHelper.w("LoginButton was NULL");
            return ;
        }

        loginButton.setSession(session());
        loginButton.setApplicationId(mAppId);
        loginButton.setReadPermissions(readPermissions(mPermissions));
        loginButton.setPublishPermissions(publishPermissions(mPermissions));
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                final Session session = session();
                if (session == null) {
                    login(callback);
                }
                else if (session.isClosed()) {
                    login(callback);
                }
                else {
                    logout(callback);
                }
            }

        });
    }

    public void login(final FacebookAuthenticatorCallback callback) {
        if (mFacebook == null) {
            LogHelper.w("SimpleFacebook was NULL");
            return ;
        }

        try {
            mFacebook.login(new SimpleFacebook.OnLoginListener() {

                @Override
                public void onLogin() {
                    LogHelper.d("Login");
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(true, token());
                    }
                }

                @Override
                public void onNotAcceptingPermissions() {
                    LogHelper.d("NotAcceptingPermissions");
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(false, "NotAcceptingPermissions");
                    }
                }

                @Override
                public void onThinking() {
                    LogHelper.v("Thinking");
                }

                @Override
                public void onException(Throwable throwable) {
                    LogHelper.e("Throwable: " + throwable.getMessage());
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(false, throwable.getMessage());
                    }
                }

                @Override
                public void onFail(String reason) {
                    LogHelper.e("Fail: " + reason);
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(false, reason);
                    }
                }

            });
        }
        catch (FacebookException e) {
            LogHelper.e("FacebookException: " + e.getMessage());
            if (callback != null) {
                callback.facebookAuthenticatorCallback(false, e.getMessage());
            }
        }
    }

    // friends

    // invite

    // publish

    public void logout(final FacebookAuthenticatorCallback callback) {
        if (mFacebook == null) {
            LogHelper.w("SimpleFacebook was NULL");
            return ;
        }

        try {
            mFacebook.logout(new SimpleFacebook.OnLogoutListener() {

                @Override
                public void onLogout() {
                    LogHelper.d("Logout");
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(true, null);
                    }
                }

                @Override
                public void onThinking() {
                    LogHelper.v("Thinking");
                }

                @Override
                public void onException(Throwable throwable) {
                    LogHelper.e("Throwable: " + throwable.getMessage());
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(false, throwable.getMessage());
                    }
                }

                @Override
                public void onFail(String reason) {
                    LogHelper.e("Fail: " + reason);
                    if (callback != null) {
                        callback.facebookAuthenticatorCallback(false, reason);
                    }
                }

            });
        }
        catch (FacebookException e) {
            LogHelper.e("FacebookException: " + e.getMessage());
            if (callback != null) {
                callback.facebookAuthenticatorCallback(false, e.getMessage());
            }
        }
    }

    public String appId() {
        return mAppId;
    }

    public String token() {
        final Session session = session();
        if (session == null) {
            LogHelper.w("Session was NULL");
            return null;
        }

        return session.getAccessToken();
    }

    public Session session() {
        Session session;

        session = Session.getActiveSession();
        if (session != null) {
            return session;
        }

        if (mActivity == null) {
            return null;
        }

        session = Session.openActiveSessionFromCache(mActivity);
        if (session != null) {
            return session;
        }

        return null;
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (mFacebook == null) {
            LogHelper.w("SimpleFacebook was NULL");
            return ;
        }
        if (mActivity == null) {
            LogHelper.w("Activity was NULL");
            return ;
        }

        mFacebook.onActivityResult(mActivity, requestCode, resultCode, data);
    }

    public static interface FacebookAuthenticatorCallback {

        public void facebookAuthenticatorCallback(final Boolean success, final String token);

    }

}
