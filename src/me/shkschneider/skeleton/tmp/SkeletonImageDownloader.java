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
package me.shkschneider.skeleton.tmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import me.shkschneider.skeleton.Skeleton;

@SuppressWarnings("unused")
public class SkeletonImageDownloader {

    private Context mContext;
    private ImageView mImageView;
    private String mUrl;
    private Boolean[] mCache = { false, false };

    public SkeletonImageDownloader(final Context context, final ImageView imageView, final String url) {
        mContext = context;
        mImageView = imageView;
        mUrl = url;
    }

    public SkeletonImageDownloader cache(final Boolean file, final Boolean memory) {
        mCache[0] = file;
        mCache[1] = memory;
        return this;
    }

    public void run(final ImageDownloaderCallback callback) {
        if (mContext != null) {
            if (! TextUtils.isEmpty(mUrl)) {
                if (Skeleton.Network.isValidUrl(mUrl)) {
                    new AQuery(mContext)
                            .ajax(new AjaxCallback<Bitmap>() {

                                @Override
                                public void callback(final String url, final Bitmap bitmap, final AjaxStatus status) {
                                    if (bitmap != null) {
                                        if (mImageView != null) {
                                            mImageView.setImageBitmap(bitmap);
                                            result(callback, mImageView, bitmap);
                                        }
                                        else {
                                            Skeleton.Log.w("ImageView was NULL");
                                            result(callback, null, null);
                                        }
                                    }
                                    else {
                                        Skeleton.Log.w("Bitmap was NULL");
                                        result(callback, null, null);
                                    }
                                }

                            }
                            .fileCache(mCache[0])
                            .memCache(mCache[1])
                            .url(mUrl)
                            .type(Bitmap.class)
                            .header("User-Agent", Skeleton.Network.makeUserAgent(mContext)));
                }
                else {
                    Skeleton.Log.w("Url was invalid");
                    result(callback, null, null);
                }
            }
            else {
                Skeleton.Log.w("Url was NULL");
                result(callback, null, null);
            }
        }
        else {
            Skeleton.Log.w("Context was NULL");
            result(callback, null, null);
        }
    }

    public void run() {
        run(null);
    }

    private void result(final ImageDownloaderCallback callback, final ImageView imageView, final Bitmap bitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            Skeleton.Log.w("ImageView was NULL");
        }

        if (callback != null) {
            callback.imageDownloaderCallback(imageView, bitmap);
        }
        else {
            Skeleton.Log.w("Callback was NULL");
        }
    }

    public static interface ImageDownloaderCallback {

        public void imageDownloaderCallback(final ImageView imageView, final Bitmap bitmap);

    }

}
