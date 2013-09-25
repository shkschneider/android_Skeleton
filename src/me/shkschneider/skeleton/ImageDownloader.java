package me.shkschneider.skeleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

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
        if (mContext != null) {
            if (! TextUtils.isEmpty(mUrl)) {
                if (Skeleton.Network.validUrl(mUrl)) {
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
                                    .header("User-Agent", Skeleton.Network.userAgent()));
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

    protected void result(final ImageDownloaderCallback callback, final ImageView imageView, final Bitmap bitmap) {
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
            Skeleton.Log.w("ImageDownloaderCallback was NULL");
        }
    }

    public static interface ImageDownloaderCallback {

        public void imageDownloaderCallback(final ImageView imageView, final Bitmap bitmap);

    }

}
