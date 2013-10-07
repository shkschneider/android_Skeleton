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

import me.shkschneider.skeleton.helper.LogHelper;

@SuppressWarnings("unused")
public class ImageDownloader {

    protected Context mContext;
    protected ImageView mImageView;
    protected String mUrl;
    protected Boolean[] mCache = { false, false };

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

    public void run(final ImageDownloaderCallback callback) {
        if (mContext == null) {
            LogHelper.w("Context was NULL");
            result(callback, null, null);
            return ;
        }

        if (TextUtils.isEmpty(mUrl)) {
            LogHelper.w("Url was NULL");
            result(callback, null, null);
            return ;
        }

        if (! NetworkHelper.validUrl(mUrl)) {
            LogHelper.w("Url was invalid");
            result(callback, null, null);
            return ;
        }

        new AQuery(mContext).ajax(new AjaxCallback<Bitmap>() {

                    @Override
                    public void callback(final String url, final Bitmap bitmap, final AjaxStatus status) {
                        if (bitmap != null) {
                            if (mImageView != null) {
                                mImageView.setImageBitmap(bitmap);
                                result(callback, mImageView, bitmap);
                            }
                            else {
                                LogHelper.w("ImageView was NULL");
                                result(callback, null, null);
                            }
                        }
                        else {
                            LogHelper.w("Bitmap was NULL");
                            result(callback, null, null);
                        }
                    }

                }.fileCache(mCache[0]).memCache(mCache[1]).url(mUrl).type(Bitmap.class).header("User-Agent", NetworkHelper.userAgent()));
    }

    public void run() {
        run(null);
    }

    protected void result(final ImageDownloaderCallback callback, final ImageView imageView, final Bitmap bitmap) {
        if (imageView == null) {
            LogHelper.w("ImageView was NULL");
            return ;
        }

        if (callback == null) {
            LogHelper.w("ImageDownloaderCallback was NULL");
            return ;
        }

        imageView.setImageBitmap(bitmap);
        callback.imageDownloaderCallback(imageView, bitmap);
    }

    public static interface ImageDownloaderCallback {

        public void imageDownloaderCallback(final ImageView imageView, final Bitmap bitmap);

    }

}
