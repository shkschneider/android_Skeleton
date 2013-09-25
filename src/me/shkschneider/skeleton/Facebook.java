package me.shkschneider.skeleton;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.apache.http.HttpStatus;

@SuppressWarnings("unused")
public class Facebook {

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

    protected static Facebook INSTANCE = null;

    protected String mAppId;
    protected Integer mRequestCode;
    protected AQuery mAQuery;
    protected FacebookHandle mHandle;

    public static Facebook newInstance(final Context context, final String appId, final Integer requestCode) {
        if (INSTANCE == null) {
            if (context != null) {
                if (! TextUtils.isEmpty(appId)) {
                    INSTANCE = new Facebook(context, appId, requestCode);
                }
                else {
                    Log.w("AppId was NULL");
                }
            }
            else {
                Log.w("Context was NULL");
            }
        }
        return INSTANCE;
    }

    public static Facebook getInstance() {
        return INSTANCE;
    }

    protected Facebook(final Context context, final String appId, final Integer requestCode) {
        mAppId = appId;
        mRequestCode = requestCode;
        mAQuery = new AQuery(context);
    }

    public void auth(final Activity activity, final FacebookCallback callback, final String permissions) {
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

        mAQuery.auth(mHandle)
                .ajax(GRAPH_ME_URL, String.class, new AjaxCallback<String>() {

                    @Override
                    public void callback(final String url, final String object, final AjaxStatus status) {
                        super.callback(url, object, status);

                        if (TextUtils.isEmpty(status.getError()) && ! status.getMessage().equalsIgnoreCase("cancel")) {
                            final String token = mHandle.getToken();
                            if (! TextUtils.isEmpty(token)) {
                                Log.d("Token: " + token);
                                callback.facebookCallback(token);
                            }
                            else {
                                Log.w("Token is NULL");
                                if (callback != null) {
                                    callback.facebookCallback(null);
                                }
                            }
                        }
                        else {
                            Log.w("Message: " + status.getMessage());
                            Log.w("Error: " + status.getError());
                            if (callback != null) {
                                callback.facebookCallback(null);
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
                Log.w("Token was NULL");
            }
        }
        else {
            Log.w("Handle was NULL");
        }
    }

    public String getToken() {
        if (mHandle != null) {
            return mHandle.getToken();
        }
        else {
            Log.w("Handle was NULL");
        }
        return null;
    }

    public void onActivityResult(final int requestCode, final int resultCode, final android.content.Intent data) {
        if (requestCode == mRequestCode) {
            if (mHandle != null) {
                mHandle.onActivityResult(requestCode, resultCode, data);
            }
            else {
                Log.w("Handle was NULL");
            }
        }
    }

    public void onDestroy() {
        if (mAQuery != null) {
            mAQuery.dismiss();
        }
        else {
            Log.w("AQuery was NULL");
        }
    }

    public static interface FacebookCallback {

        public void facebookCallback(final String token);

    }

}
