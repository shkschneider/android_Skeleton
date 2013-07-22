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
package me.shkschneider.skeleton.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.helpers.NetworkHelper;

public class ImageDownloader {

    private Context mContext;
    private ImageView mImageView;
    private String mUrl;
    private Boolean[] mCache = { false, false };

    private Boolean check() {
        if (mContext == null) {
            LogHelper.w("Context was NULL");
            return false;
        }
        if (TextUtils.isEmpty(mUrl)) {
            LogHelper.w("Url was NULL");
            return false;
        }
        if (! NetworkHelper.isValidUrl(mUrl)) {
            LogHelper.w("Url was invalid");
            return false;
        }
        return true;
    }

    public ImageDownloader(final Context context, final ImageView imageView, final String url) {
        mContext = context;
        mImageView = imageView;
        mUrl = url;
    }

    public ImageDownloader cache(final Boolean file, final Boolean memory) {
        mCache[0] = file;
        mCache[1] = memory;
        return this;
    }

    public void run(final Callback callback) {
        if (! check()) {
            LogHelper.d("check() failed");
            if (callback != null) {
                callback.ImageDownloaderCallback(mImageView, null);
            }
        }
        else {
            final AjaxCallback<Bitmap> ajaxCallback = new AjaxCallback<Bitmap>() {

                        @Override
                        public void callback(final String url, final Bitmap bitmap, final AjaxStatus status) {
                            if (mImageView != null) {
                                mImageView.setImageBitmap(bitmap);
                            }
                            if (callback != null) {
                                callback.ImageDownloaderCallback(mImageView, bitmap);
                            }
                            else {
                                LogHelper.d("Callback was NULL");
                            }
                        }

                    }
                    .fileCache(mCache[0])
                    .memCache(mCache[1])
                    .url(mUrl)
                    .type(Bitmap.class)
                    .header("User-Agent", NetworkHelper.userAgent(mContext));

            new AQuery(mContext).ajax(ajaxCallback);
        }
    }

    public void run() {
        run(null);
    }

    public static interface Callback {

        public void ImageDownloaderCallback(final ImageView imageView, final Bitmap bitmap);

    }

}
